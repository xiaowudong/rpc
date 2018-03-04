package com.xwd.study.rpc.example.demo.service;

import com.xwd.study.rpc.client.annotation.RPCService;

import com.xwd.study.rpc.example.demo.service.HelloSerivce;

public class HelloService_1 implements HelloSerivce {

	@Override
	@RPCService(responsibilityName = "xiaoy", serviceName = "LAOPOPO.TEST.SAYHELLO", weight = 5)
	public String sayHello(String str) {
		return "hello_1";
	}

}
