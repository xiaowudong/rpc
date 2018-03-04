package com.xwd.study.rpc.remoting.model;

import io.netty.channel.ChannelHandlerContext;

public interface NettyRpcRequestProcessor {
	
	void processRPCRequest(ChannelHandlerContext ctx, RemotingTransporter request);

}
