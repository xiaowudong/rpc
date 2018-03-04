package com.xwd.study.rpc.demo.me;
/**
 * @author xwd	
 * @time 2018年1月16日 下午4:16:36 
 */
public class HelloServiceImpl implements HelloService {
	public String version;
	
	public HelloServiceImpl(String version) {
		super();
		this.version = version;
	}
	@Override
	public String hello(String name) {
		return version+"  Hello " + name;
	}

}
