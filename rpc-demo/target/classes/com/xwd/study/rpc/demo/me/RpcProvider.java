package com.xwd.study.rpc.demo.me;

public class RpcProvider {
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午4:18:40 
	 */
	public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl("6668");  
        RpcFramework.export(HelloService.class.getName(),service,"1.0.0",6668);
    } 
	
}
