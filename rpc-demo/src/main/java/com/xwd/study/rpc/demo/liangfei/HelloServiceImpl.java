package com.xwd.study.rpc.demo.liangfei;
/**
 * @author xwd	
 * @time 2018年1月16日 下午4:16:36 
 */
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello " + name;
	}

}
