package com.xwd.study.rpc.example.benchmark;

import com.xwd.study.rpc.base.registry.DefaultRegistryServer;
import com.xwd.study.rpc.base.registry.RegistryServerConfig;
import com.xwd.study.rpc.common.rpc.ServiceReviewState;
import com.xwd.study.rpc.remoting.netty.NettyServerConfig;

/**
 * 
 * @author BazingaLyn
 * @description 性能测试的注册中心端
 * @time
 * @modifytime
 */
public class BenchmarkRegistryTest {
	
	
	public static void main(String[] args) {
        
		NettyServerConfig config = new NettyServerConfig();
		RegistryServerConfig registryServerConfig = new RegistryServerConfig();
		registryServerConfig.setDefaultReviewState(ServiceReviewState.PASS_REVIEW);
		//注册中心的端口号
		config.setListenPort(18010);
		new DefaultRegistryServer(config,registryServerConfig).start();
		
	}
	
	

}
