package com.xwd.study.rpc.example.demo.service;

import com.xwd.study.rpc.client.annotation.RPCService;

/**
 * @description Demo
 * @author xwd	
 * @time 2018年2月6日 下午12:33:29
 */
public class HelloSerivceImpl implements HelloSerivce {

	@Override
	@RPCService(responsibilityName="xiaoy",
				serviceName="LAOPOPO.TEST.SAYHELLO",
				isVIPService = false,
				isSupportDegradeService = true,
				degradeServicePath="com.xwd.study.rpc.example.demo.service.HelloServiceMock",
				degradeServiceDesc="默认返回hello")
	public String sayHello(String str) {
		
		//真实逻辑可能涉及到查库
		return "hello "+ str;
		
	}

}
