package com.xwd.study.rpc.example.generic.test_4;

import com.xwd.study.rpc.client.annotation.RPConsumer;

public interface HelloService {

	@RPConsumer(serviceName="LAOPOPO.TEST.SAYHELLO")
	String sayHello(String str);
	
}
