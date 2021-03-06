package com.xwd.study.rpc.example.generic.test_7;

import com.xwd.study.rpc.client.provider.DefaultProvider;
import com.xwd.study.rpc.common.exception.remoting.RemotingException;

import com.xwd.study.rpc.example.demo.service.HelloService_1;

public class ProviderTest_1 {
	
	public static void main(String[] args) throws InterruptedException, RemotingException {

		DefaultProvider defaultProvider = new DefaultProvider();

		defaultProvider.serviceListenPort(9001)    					  // 暴露服务的地址
				.publishService(new HelloService_1()) 				  // 暴露的服务
				.start(); 											  // 启动服务

	}

}
