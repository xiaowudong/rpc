package com.xwd.study.rpc.example.netty;

import com.xwd.study.rpc.common.exception.remoting.RemotingException;
import com.xwd.study.rpc.remoting.model.RemotingTransporter;
import com.xwd.study.rpc.remoting.netty.NettyClientConfig;
import com.xwd.study.rpc.remoting.netty.NettyRemotingClient;

import com.xwd.study.rpc.example.netty.TestCommonCustomBody.ComplexTestObj;

public class NettyClientTest {
	
	public static final byte TEST = -1;
	
	public static void main(String[] args) throws InterruptedException, RemotingException {
		NettyClientConfig nettyClientConfig = new NettyClientConfig();
		NettyRemotingClient client = new NettyRemotingClient(nettyClientConfig);
		client.start();
		
		ComplexTestObj complexTestObj = new ComplexTestObj("attr1", 2);
		TestCommonCustomBody commonCustomHeader = new TestCommonCustomBody(1, "test",complexTestObj);
		
		RemotingTransporter remotingTransporter = RemotingTransporter.createRequestTransporter(TEST, commonCustomHeader);
		RemotingTransporter request = client.invokeSync("127.0.0.1:18001", remotingTransporter, 3000);
		System.out.println(request);
	}
	
}
