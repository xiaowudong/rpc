package com.xwd.study.rpc.example.generic.test_5;

import com.xwd.study.rpc.client.provider.DefaultProvider;
import com.xwd.study.rpc.common.exception.remoting.RemotingException;

/**
 * 
 * @author BazingaLyn
 * @description 测试单位时间的限流
 * @time 2016年9月14日
 * @modifytime
 */
public class ProviderTest {

	public static void main(String[] args) throws InterruptedException, RemotingException {

		DefaultProvider defaultProvider = new DefaultProvider();

		defaultProvider.serviceListenPort(8899) // 暴露服务的地址
				.publishService(new HelloServiceFlowControllerImpl()) // 暴露的服务
				.start(); // 启动服务

	}

}
