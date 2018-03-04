package com.xwd.study.rpc.common.serialization.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.xwd.study.rpc.common.serialization.Serializer;

/**
 * 使用Kryo序列化 需要实现java.io.Serializable接口
 * @author xwd	
 * @time 2018年1月17日 下午7:22:04
 */
public class KryoSerializer implements Serializer {

	@Override
	public <T> byte[] writeObject(T obj) {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.register(obj.getClass(), new JavaSerializer());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeClassAndObject(output, obj);
		output.flush();
		output.close();

		byte[] b = baos.toByteArray();
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(byte[] bytes, Class<T> clazz) {
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.register(clazz, new JavaSerializer());

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		return (T) kryo.readClassAndObject(input);
	}

}
