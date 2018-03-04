package com.xwd.study.rpc.example.generic.test_2;

import com.xwd.study.rpc.client.consumer.ConsumerClient;
import com.xwd.study.rpc.client.consumer.ConsumerConfig;
import com.xwd.study.rpc.client.consumer.Consumer.SubscribeManager;
import com.xwd.study.rpc.client.consumer.proxy.ProxyFactory;
import com.xwd.study.rpc.remoting.netty.NettyClientConfig;

public class ConsumerTest {
	
	public static void main(String[] args) throws Exception {
		
		NettyClientConfig registryNettyClientConfig = new NettyClientConfig();
		registryNettyClientConfig.setDefaultAddress("127.0.0.1:18010");

		NettyClientConfig provideClientConfig = new NettyClientConfig();

		ConsumerClient client = new ConsumerClient(registryNettyClientConfig, provideClientConfig, new ConsumerConfig());

		client.start();
		
		SubscribeManager subscribeManager = client.subscribeService("LAOPOPO.TEST.SAYHELLO");

		if (!subscribeManager.waitForAvailable(3000l)) {
			throw new Exception("no service provider");
		}
		
		HelloService helloService = ProxyFactory.factory(HelloService.class).consumer(client).timeoutMillis(3000l).newProxyInstance();
		
		String str = helloService.sayHello("Lyncc");
		
		System.out.println(str);
	}

}
