package com.xwd.study.rpc.example.generic.test_5;

import com.xwd.study.rpc.client.annotation.RPCService;

import com.xwd.study.rpc.example.demo.service.HelloSerivce;

public class HelloServiceFlowControllerImpl implements HelloSerivce {

	@Override
	@RPCService(responsibilityName="xiaoy",serviceName="LAOPOPO.TEST.SAYHELLO",maxCallCountInMinute = 40)
	public String sayHello(String str) {
		return "hello "+ str;
	}

}
