package com.xwd.study.rpc.example.serializer;

import static com.xwd.study.rpc.common.serialization.SerializerHolder.serializerImpl;
import com.xwd.study.rpc.example.netty.TestCommonCustomBody;
import com.xwd.study.rpc.example.netty.TestCommonCustomBody.ComplexTestObj;

/**
 * 
 * @author xwd	
 * @time 2018年1月17日 下午7:28:48
 * 1)使用protoStuff序列化测试
 * 修改com.xwd.study.rpc.common.serialization.Serializer中的内容为：
 * com.xwd.study.rpc.common.serialization.proto.ProtoStuffSerializer
 * 
 * 2)使用fastjson序列化测试
 * 修改com.xwd.study.rpc.common.serialization.Serializer中的内容为：
 * com.xwd.study.rpc.common.serialization.fastjson.FastjsonSerializer
 * 
 * 3)使用kryo序列化测试
 * 修改com.xwd.study.rpc.common.serialization.Serializer中的内容为：
 * com.xwd.study.rpc.common.serialization.kryo.KryoSerializer
 */
public class SerializerTest {
	public static void main(String[] args) {
		
		long beginTime = System.currentTimeMillis();
		
		for(int i = 0;i < 100000;i++){
			ComplexTestObj complexTestObj = new ComplexTestObj("attr1", 2);
			TestCommonCustomBody commonCustomHeader = new TestCommonCustomBody(1, "test",complexTestObj);
			byte[] bytes = serializerImpl().writeObject(commonCustomHeader);
			
			TestCommonCustomBody body = serializerImpl().readObject(bytes, TestCommonCustomBody.class);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - beginTime));
		
	}

}
