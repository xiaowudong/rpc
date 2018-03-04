package com.xwd.study.rpc.demo.me;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Service implements Serializable{
	/**
	 * @author xwd	
	 * @time 2018年1月16日 下午7:03:00 
	 */
	private String ServiceName;
	private String serviceVersion;
	private String serviceIp;
	private int  servicePort;
	public String getServiceName() {
		return ServiceName;
	}
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public String getServiceVersion() {
		return serviceVersion;
	}
	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	public String getServiceIp() {
		return serviceIp;
	}
	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	public int getServicePort() {
		return servicePort;
	}
	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}
	public String toString(){
		return "ServiceName:"+ServiceName+" serviceVersion:"+serviceVersion+" serviceIp:"+serviceIp+" servicePort:"+servicePort;
	}
	
}
