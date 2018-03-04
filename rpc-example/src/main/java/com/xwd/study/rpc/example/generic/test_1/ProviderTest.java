package com.xwd.study.rpc.example.generic.test_1;

import com.xwd.study.rpc.client.provider.DefaultProvider;
import com.xwd.study.rpc.common.exception.remoting.RemotingException;
import com.xwd.study.rpc.remoting.netty.NettyClientConfig;
import com.xwd.study.rpc.remoting.netty.NettyServerConfig;

import com.xwd.study.rpc.example.demo.service.ByeServiceImpl;
import com.xwd.study.rpc.example.demo.service.HelloSerivceImpl;

public class ProviderTest {

	public static void main(String[] args) throws InterruptedException, RemotingException {

		DefaultProvider defaultProvider = new DefaultProvider(new NettyClientConfig(), new NettyServerConfig());

		defaultProvider.registryAddress("127.0.0.1:18010") // 注册中心的地址
				.serviceListenPort(8899) // 暴露服务的端口
				.publishService(new HelloSerivceImpl(), new ByeServiceImpl()) // 暴露的服务
				.start(); // 启动服务

	}

}
