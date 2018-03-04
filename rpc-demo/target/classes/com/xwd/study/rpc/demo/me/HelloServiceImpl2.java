package com.xwd.study.rpc.demo.me;
/**
 * @author xwd	
 * @time 2018年1月16日 下午4:16:36 
 */
public class HelloServiceImpl2 implements HelloService2 {
	public String version;
	
	public HelloServiceImpl2(String version) {
		super();
		this.version = version;
	}
	@Override
	public String hello2(String name) {
		return version+"  Hello2 " + name;
	}

}
