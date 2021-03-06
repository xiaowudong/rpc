package com.xwd.study.rpc.base.registry;

import io.netty.channel.Channel;

import com.xwd.study.rpc.common.exception.remoting.RemotingSendRequestException;
import com.xwd.study.rpc.common.exception.remoting.RemotingTimeoutException;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;

/**
 * 
 * @author BazingaLyn
 * @description 注册中心处理provider的服务接口
 * @time 2016年8月15日
 * @modifytime
 */
public interface RegistryProviderServer {
	
	
	/**
	 * 处理provider发送过来的注册信息
	 * @param remotingTransporter 里面的CommonCustomBody 是#PublishServiceCustomBody
	 * @param channel
	 * @return
	 * @throws InterruptedException 
	 * @throws RemotingTimeoutException 
	 * @throws RemotingSendRequestException 
	 */
	RemotingTransporter handlerRegister(RemotingTransporter remotingTransporter,Channel channel) throws RemotingSendRequestException, RemotingTimeoutException, InterruptedException;
}
