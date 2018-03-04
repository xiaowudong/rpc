package com.xwd.study.rpc.client.provider;

import java.util.List;

import com.xwd.study.rpc.client.provider.interceptor.ProviderProxyHandler;
import com.xwd.study.rpc.client.provider.model.ServiceWrapper;


/**
 * 
 * @author BazingaLyn
 * @description
 * @time
 * @modifytime
 */
public interface ServiceWrapperWorker {
	
	ServiceWrapperWorker provider(Object serviceProvider);
	
	ServiceWrapperWorker provider(ProviderProxyHandler proxyHandler,Object serviceProvider);
	
	List<ServiceWrapper> create();

}
