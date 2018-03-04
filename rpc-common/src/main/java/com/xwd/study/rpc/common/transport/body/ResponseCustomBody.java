package com.xwd.study.rpc.common.transport.body;

import static com.xwd.study.rpc.common.utils.Status.OK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xwd.study.rpc.common.exception.remoting.RemotingCommmonCustomException;

/**
 * 
 * @author BazingaLyn
 * @description
 * @time 2016年8月20日
 * @modifytime 2016年9月20日
 */
public class ResponseCustomBody implements CommonCustomBody {
	
	private static final Logger logger = LoggerFactory.getLogger(ResponseCustomBody.class);
	
	private byte status = OK.value();

    private ResultWrapper resultWrapper;	
    
	public ResponseCustomBody(byte status, ResultWrapper resultWrapper) {
		this.status = status;
		this.resultWrapper = resultWrapper;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public ResultWrapper getResultWrapper() {
		return resultWrapper;
	}

	public void setResultWrapper(ResultWrapper resultWrapper) {
		this.resultWrapper = resultWrapper;
	}

	@Override
	public void checkFields() throws RemotingCommmonCustomException {
	}
	
	public static class ResultWrapper {
		
		private Object result;
	    private String error;
	    
		public Object getResult() {
			return result;
		}
		
		public void setResult(Object result) {
			this.result = result;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}

	public Object getResult() {
		
		if(status == OK.value()){
			return getResultWrapper().getResult();
		}else{
			logger.warn("get result occor exception [{}]",getResultWrapper().getError());
			return null;
		}
	}

}
