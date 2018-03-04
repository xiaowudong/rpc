package com.xwd.study.rpc.demo.liangfei;

public class RpcConsumer {
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午4:19:03 
	 */
	public static void main(String[] args) throws Exception {  
        HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);  
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {  
            String hello = service.hello("World" + i);  
            System.out.println(hello);  
            Thread.sleep(1000);  
        }  
    }  
}
