package com.xwd.study.rpc.example.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author xwd	
 * @time 2018年1月18日 下午1:55:39 
 */
public class SpiMain {

	public static void main(String[] args) {
		 ServiceLoader<SpiHelloWorld>  serviceLoader =ServiceLoader.load(SpiHelloWorld.class);
		 Iterator<SpiHelloWorld> iterator=serviceLoader.iterator();
		 while(iterator.hasNext()){
			 iterator.next().helloWorld();
		 }

	}

}
