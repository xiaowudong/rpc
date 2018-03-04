package com.xwd.study.rpc.remoting.model;

import io.netty.channel.ChannelHandlerContext;

public interface NettyRequestProcessor {
	
	RemotingTransporter processRequest(ChannelHandlerContext ctx, RemotingTransporter request)
            throws Exception;

}
