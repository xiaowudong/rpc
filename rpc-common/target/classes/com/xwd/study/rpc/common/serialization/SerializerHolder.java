package com.xwd.study.rpc.common.serialization;

import com.xwd.study.rpc.common.spi.BaseServiceLoader;

/**
 * 序列化的入口,基于SPI方式
 * @author xwd	
 * @time 2018年1月17日 下午7:23:01
 */
public final class SerializerHolder {

    // SPI
    private static final Serializer serializer = BaseServiceLoader.load(Serializer.class);

    public static Serializer serializerImpl() {
        return serializer;
    }
}
