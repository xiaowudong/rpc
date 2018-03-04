package com.xwd.study.rpc.client.provider;

import static com.xwd.study.rpc.common.protocal.LaopopoProtocol.AUTO_DEGRADE_SERVICE;
import static com.xwd.study.rpc.common.protocal.LaopopoProtocol.DEGRADE_SERVICE;
import io.netty.channel.ChannelHandlerContext;

import com.xwd.study.rpc.remoting.ConnectionUtils;
import com.xwd.study.rpc.remoting.model.NettyRequestProcessor;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author BazingaLyn
 * @description provider端注册的处理器
 * @time
 * @modifytime
 */
public class DefaultProviderRegistryProcessor implements NettyRequestProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultProviderRegistryProcessor.class);
	
	private DefaultProvider defaultProvider;

	public DefaultProviderRegistryProcessor(DefaultProvider defaultProvider) {
		this.defaultProvider = defaultProvider;
	}

	@Override
	public RemotingTransporter processRequest(ChannelHandlerContext ctx, RemotingTransporter request) throws Exception {
		
		if (logger.isDebugEnabled()) {
			logger.debug("receive request, {} {} {}",//
                request.getCode(), //
                ConnectionUtils.parseChannelRemoteAddr(ctx.channel()), //
                request);
        }
		
		switch (request.getCode()) {
		   case DEGRADE_SERVICE:
			    return this.defaultProvider.handlerDegradeServiceRequest(request,ctx.channel(),DEGRADE_SERVICE);
		   case AUTO_DEGRADE_SERVICE:
			    return this.defaultProvider.handlerDegradeServiceRequest(request,ctx.channel(),AUTO_DEGRADE_SERVICE);
		}
		return null;
	}

}
