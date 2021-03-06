package com.xwd.study.rpc.remoting.netty;

import java.util.concurrent.ExecutorService;

import com.xwd.study.rpc.common.exception.remoting.RemotingException;
import com.xwd.study.rpc.common.exception.remoting.RemotingSendRequestException;
import com.xwd.study.rpc.common.exception.remoting.RemotingTimeoutException;

import com.xwd.study.rpc.remoting.model.NettyChannelInactiveProcessor;
import com.xwd.study.rpc.remoting.model.NettyRequestProcessor;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;

/**
 * Netty客户端的一些特定的方法
 * @author xwd	
 * @time 2018年1月18日 下午3:39:14
 */
public interface RemotingClient extends BaseRemotingService {

	/**
	 * 向某个地址发送request的请求，并且远程端返回 #RemotingTransporter的结果，调用超时时间是timeoutMillis
	 * @param addr 远程地址 例如 127.0.0.1:8080
	 * @param request 请求入参 详细参考 #RemotingTransporter
	 * @param timeoutMillis  超时时间
	 * @return
	 * @throws RemotingTimeoutException
	 * @throws RemotingSendRequestException
	 * @throws InterruptedException
	 * @throws RemotingException
	 */
	public RemotingTransporter invokeSync(final String addr ,final RemotingTransporter request,final long timeoutMillis) throws RemotingTimeoutException, RemotingSendRequestException, InterruptedException, RemotingException;

	/**
	 * 注入处理器，例如某个Netty的Client实例，这个实例是Consumer端的，它需要处理订阅返回的信息
	 * 假如订阅的requestCode 是100，那么给定requestCode特定的处理器processorA,且指定该处理器的线程执行器是executorA
	 * 这样做的好处就是业务逻辑很清晰，什么样的业务请求对应特定的处理器
	 * 一般场景下，不是高并发的场景下，executor是可以复用的，这样减少线程上下文的切换
	 * @param requestCode
	 * @param processor
	 * @param executor
	 */
	void registerProcessor(final byte requestCode, final NettyRequestProcessor processor, final ExecutorService executor);
	
	
	/**
	 * 注册channel inactive的处理器
	 * @param processor
	 * @param executor
	 */
	void registerChannelInactiveProcessor(NettyChannelInactiveProcessor processor, ExecutorService executor);

	/**
	 * 某个地址的长连接的channel是否可写
	 * @param addr
	 * @return
	 */
	boolean isChannelWriteable(final String addr);
	
	
	/**
	 * 当与server的channel inactive的时候，是否主动重连netty的server端
	 * @param isReconnect
	 */
	void setreconnect(boolean isReconnect);

}
