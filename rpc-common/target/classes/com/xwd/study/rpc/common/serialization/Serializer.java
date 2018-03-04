package com.xwd.study.rpc.common.serialization;

/**
 *  序列化接口
 * @author xwd	
 * @time 2018年1月17日 下午7:22:37
 */
public interface Serializer {

	/**
	 * 将对象序列化成byte[]
	 * @param obj
	 * @return
	 */
    <T> byte[] writeObject(T obj);

    /**
     * 将byte数组反序列成对象
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T readObject(byte[] bytes, Class<T> clazz);
}
