package com.xwd.study.rpc.client.consumer;

import static com.xwd.study.rpc.common.serialization.SerializerHolder.serializerImpl;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xwd.study.rpc.common.loadbalance.LoadBalanceStrategy;
import com.xwd.study.rpc.common.protocal.LaopopoProtocol;
import com.xwd.study.rpc.common.rpc.RegisterMeta;
import com.xwd.study.rpc.common.transport.body.AckCustomBody;
import com.xwd.study.rpc.common.transport.body.SubcribeResultCustomBody;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwd.study.rpc.client.consumer.NotifyListener.NotifyEvent;

/**
 * 
 * @author BazingaLyn
 * @description 消费者端的一些逻辑处理
 * @time 2016年8月22日
 * @modifytime
 */
public class ConsumerManager {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerManager.class);
	
	private DefaultConsumer defaultConsumer; //consumer模块的代码手持defaultConsumer好办事
	private final ReentrantReadWriteLock registriesLock = new ReentrantReadWriteLock();
	private final Map<String, List<RegisterMeta>> registries = new ConcurrentHashMap<String, List<RegisterMeta>>();

	public ConsumerManager(DefaultConsumer defaultConsumer) {
		this.defaultConsumer = defaultConsumer;
	}

	/**
	 * 处理服务的订阅结果
	 * @param request
	 * @param channel
	 * @return
	 */
	public RemotingTransporter handlerSubcribeResult(RemotingTransporter request, Channel channel) {

		if (logger.isDebugEnabled()) {
			logger.debug("handler subcribe result [{}] and channel [{}]", request, channel);
		}

		AckCustomBody ackCustomBody = new AckCustomBody(request.getOpaque(), false);
		RemotingTransporter responseTransporter = RemotingTransporter.createResponseTransporter(LaopopoProtocol.ACK, ackCustomBody, request.getOpaque());

		SubcribeResultCustomBody subcribeResultCustomBody = serializerImpl().readObject(request.bytes(), SubcribeResultCustomBody.class);

		String serviceName = null;
		if (subcribeResultCustomBody != null && subcribeResultCustomBody.getRegisterMeta() != null && !subcribeResultCustomBody.getRegisterMeta().isEmpty()) {

			for (RegisterMeta registerMeta : subcribeResultCustomBody.getRegisterMeta()) {

				if (null == serviceName) {
					serviceName = registerMeta.getServiceName();
				}
				notify(serviceName, registerMeta, NotifyEvent.CHILD_ADDED);
			}
		}

		ackCustomBody.setSuccess(true);
		return responseTransporter;
	}

	/**
	 * 处理服务取消的时候逻辑处理
	 * 
	 * @param request
	 * @param channel
	 * @return
	 */
	public RemotingTransporter handlerSubscribeResultCancel(RemotingTransporter request, Channel channel) {
		AckCustomBody ackCustomBody = new AckCustomBody(request.getOpaque(), false);
		RemotingTransporter responseTransporter = RemotingTransporter.createResponseTransporter(LaopopoProtocol.ACK, ackCustomBody, request.getOpaque());

		SubcribeResultCustomBody subcribeResultCustomBody = serializerImpl().readObject(request.bytes(), SubcribeResultCustomBody.class);

		if (subcribeResultCustomBody != null && subcribeResultCustomBody.getRegisterMeta() != null && !subcribeResultCustomBody.getRegisterMeta().isEmpty()) {

			for (RegisterMeta registerMeta : subcribeResultCustomBody.getRegisterMeta()) {
				notify(registerMeta.getServiceName(), registerMeta, NotifyEvent.CHILD_REMOVED);
			}
		}
		ackCustomBody.setSuccess(true);
		return responseTransporter;
	}

	/**
	 * 处理注册中心发送过来的负载均衡策略的变化
	 * @param request
	 * @param channel
	 * @return
	 */
	public RemotingTransporter handlerServiceLoadBalance(RemotingTransporter request, Channel channel) {
		
		if(logger.isDebugEnabled()){
			logger.debug("handler change loadBalance strategy [{}] and channel [{}]",request,channel);
		}

		AckCustomBody ackCustomBody = new AckCustomBody(request.getOpaque(), false);
		RemotingTransporter responseTransporter = RemotingTransporter.createResponseTransporter(LaopopoProtocol.ACK, ackCustomBody, request.getOpaque());

		SubcribeResultCustomBody subcribeResultCustomBody = serializerImpl().readObject(request.bytes(), SubcribeResultCustomBody.class);

		String serviceName = subcribeResultCustomBody.getServiceName();

		LoadBalanceStrategy balanceStrategy = subcribeResultCustomBody.getLoadBalanceStrategy();

		defaultConsumer.setServiceLoadBalanceStrategy(serviceName, balanceStrategy);

		ackCustomBody.setSuccess(true);
		return responseTransporter;
	}

	/************************* ↑为核心方法，下面为内部方法 ************************/

	private void notify(String serviceName, RegisterMeta registerMeta, NotifyEvent event) {

		boolean notifyNeeded = false;

		final Lock writeLock = registriesLock.writeLock();
		writeLock.lock();
		try {
			List<RegisterMeta> registerMetas = registries.get(serviceName);
			if (registerMetas == null) {
				if (event == NotifyEvent.CHILD_REMOVED) {
					return;
				}
				registerMetas = new ArrayList<RegisterMeta>();
				registerMetas.add(registerMeta);
				notifyNeeded = true;
			} else {
				if (event == NotifyEvent.CHILD_REMOVED) {
					registerMetas.remove(registerMeta);
				} else if (event == NotifyEvent.CHILD_ADDED) {
					registerMetas.add(registerMeta);
				}
				notifyNeeded = true;
			}
			registries.put(serviceName, registerMetas);
		} finally {
			writeLock.unlock();
		}

		if (notifyNeeded) {

			NotifyListener listener = this.defaultConsumer.getDefaultConsumerRegistry().getServiceMatchedNotifyListener().get(serviceName);
			if (null != listener) {
				listener.notify(registerMeta, event);
			}

		}
	}

}