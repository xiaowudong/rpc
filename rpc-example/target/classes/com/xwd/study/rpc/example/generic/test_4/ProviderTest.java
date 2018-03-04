package com.xwd.study.rpc.example.generic.test_4;

import com.xwd.study.rpc.client.provider.DefaultProvider;
import com.xwd.study.rpc.common.exception.remoting.RemotingException;

import com.xwd.study.rpc.example.demo.service.ByeServiceImpl;
import com.xwd.study.rpc.example.demo.service.HelloSerivceImpl;

public class ProviderTest {

	public static void main(String[] args) throws InterruptedException, RemotingException {

		DefaultProvider defaultProvider = new DefaultProvider();

		defaultProvider.serviceListenPort(8899) // 暴露服务的地址
				.publishService(new HelloSerivceImpl(), new ByeServiceImpl()) // 暴露的服务
				.start(); // 启动服务

	}

}
