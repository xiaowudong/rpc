package com.xwd.study.rpc.client.provider.model;

import io.netty.channel.ChannelHandlerContext;

import com.xwd.study.rpc.remoting.model.NettyChannelInactiveProcessor;

import com.xwd.study.rpc.client.provider.DefaultProvider;

/**
 * 
 * @author BazingaLyn
 * @description provider的netty inactive触发的事件
 * @time
 * @modifytime
 */
public class DefaultProviderInactiveProcessor implements NettyChannelInactiveProcessor {

	private DefaultProvider defaultProvider;

	public DefaultProviderInactiveProcessor(DefaultProvider defaultProvider) {
		this.defaultProvider = defaultProvider;
	}

	@Override
	public void processChannelInactive(ChannelHandlerContext ctx) {
		defaultProvider.setProviderStateIsHealthy(false);
	}

}
