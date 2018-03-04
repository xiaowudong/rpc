package com.xwd.study.rpc.demo.me;

import java.io.Serializable;
import java.util.HashSet;

@SuppressWarnings("serial")
public class RegistryRe implements Serializable{
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午7:08:07 
	 */
	private String status;
	private String msg;
	private HashSet<Service> services;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public HashSet<Service> getServices() {
		return services;
	}
	public void setServices(HashSet<Service> services) {
		this.services = services;
	}
	
	public String toString(){
		return "status:"+status+" msg:"+msg+" services:"+services;
	}
}
