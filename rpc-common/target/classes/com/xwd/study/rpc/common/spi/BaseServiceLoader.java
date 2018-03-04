package com.xwd.study.rpc.common.spi;

import java.util.ServiceLoader;

/**
 * 得到接口的第一个实现
 * @author xwd	
 * @time 2018年1月1日 下午12:57:43
 */
public final class BaseServiceLoader {

    public static <S> S load(Class<S> serviceClass) {
        return ServiceLoader.load(serviceClass).iterator().next();
    }
}
