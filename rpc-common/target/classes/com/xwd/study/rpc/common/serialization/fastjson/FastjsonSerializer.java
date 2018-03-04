package com.xwd.study.rpc.common.serialization.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xwd.study.rpc.common.serialization.Serializer;

/**
 * 使用fastjson序列化  需要有无参构造函数
 * @author xwd	
 * @time 2018年1月17日 下午7:22:21
 */
public class FastjsonSerializer implements Serializer {

	@Override
	public <T> byte[] writeObject(T obj) {
		return JSON.toJSONBytes(obj, SerializerFeature.SortField);
	}

	@Override
	public <T> T readObject(byte[] bytes, Class<T> clazz) {
		return JSON.parseObject(bytes, clazz, Feature.SortFeidFastMatch);
	}

}
