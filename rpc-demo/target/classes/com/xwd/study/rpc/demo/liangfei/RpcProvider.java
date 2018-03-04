package com.xwd.study.rpc.demo.liangfei;

public class RpcProvider {
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午4:18:40 
	 */
	public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl();  
        RpcFramework.export(service, 1234);
    } 
	
}
