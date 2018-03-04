package com.xwd.study.rpc.example.netty;

import static com.xwd.study.rpc.common.serialization.SerializerHolder.serializerImpl;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executors;

import com.xwd.study.rpc.common.protocal.LaopopoProtocol;
import com.xwd.study.rpc.remoting.model.NettyRequestProcessor;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;
import com.xwd.study.rpc.remoting.netty.NettyRemotingServer;
import com.xwd.study.rpc.remoting.netty.NettyServerConfig;

public class NettyServerTest {
	
	public static final byte TEST = -1;
	
	public static void main(String[] args) {
		
		NettyServerConfig config = new NettyServerConfig();
		config.setListenPort(18001);
		NettyRemotingServer server = new NettyRemotingServer(config);
		server.registerProecessor(TEST, new NettyRequestProcessor() {
			
			@Override
			public RemotingTransporter processRequest(ChannelHandlerContext ctx, RemotingTransporter transporter) throws Exception {
				transporter.setCustomHeader(serializerImpl().readObject(transporter.bytes(), TestCommonCustomBody.class));
				System.out.println(transporter);
				transporter.setTransporterType(LaopopoProtocol.RESPONSE_REMOTING);
				return transporter;
			}
		}, Executors.newCachedThreadPool());
		server.start();
	}

}
