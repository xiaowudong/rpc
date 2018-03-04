package com.xwd.study.rpc.demo.me;

public class RpcConsumer {
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午4:19:03 
	 */
	public static void main(String[] args) throws Exception {  
        HelloService service = RpcFramework.refer(HelloService.class,"1.0.0");  
        for (int i = 0; i < 100; i ++) {  
            String hello = service.hello("World" + i);  
            System.out.println(hello);  
            Thread.sleep(1000);  
        }  
    }  
}
