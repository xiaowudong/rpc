package com.xwd.study.rpc.example.demo.service;

import com.xwd.study.rpc.client.annotation.RPCService;

public class HelloServiceBenchmark implements HelloSerivce {

	@Override
	@RPCService(responsibilityName="xiaoy",
	serviceName="LAOPOPO.TEST.SAYHELLO",
	connCount = 4,
	isFlowController = false,
	degradeServiceDesc="默认返回hello")
	public String sayHello(String str) {
		return str;
	}
	

}
