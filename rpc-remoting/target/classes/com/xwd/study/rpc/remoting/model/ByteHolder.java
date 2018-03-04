package com.xwd.study.rpc.remoting.model;

/**
 * 
 * @author xwd	
 * @time 2018年1月17日 下午3:28:02
 */
public class ByteHolder {
	
	private transient byte[] bytes;

    public byte[] bytes() {
        return bytes;
    }

    public void bytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int size() {
        return bytes == null ? 0 : bytes.length;
    }

}
