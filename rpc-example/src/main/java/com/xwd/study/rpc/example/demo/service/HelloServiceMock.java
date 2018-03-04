package com.xwd.study.rpc.example.demo.service;
/**
 * 
 * @author xwd	
 * @time 2018年2月6日 下午12:09:41
 */
public class HelloServiceMock implements HelloSerivce {

	@Override
	public String sayHello(String str) {
		
		//直接给出默认的返回值
		return "hello";
	}

}
