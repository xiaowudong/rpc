package com.xwd.study.rpc.remoting.netty;

import com.xwd.study.rpc.remoting.RPCHook;

/**
 * Netty网络通讯端Client端和Server端都需要实现的方法集合
 * @author xwd	
 * @time 2018年1月18日 下午3:39:49
 */
public interface BaseRemotingService {
	
	/**
	 * Netty的一些参数的初始化
	 */
	void init();
	
	/**
	 * 启动Netty方法
	 */
	void start();
	
	/**
	 * 关闭Netty C/S 实例
	 */
	void shutdown();
	
	/**
	 * 注入钩子，Netty在处理的过程中可以嵌入一些方法，增加代码的灵活性
	 * @param rpcHook
	 */
	void registerRPCHook(RPCHook rpcHook);

}
