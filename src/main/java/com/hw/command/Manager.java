package com.hw.command;

import java.util.HashMap;
import java.util.Map;

public class Manager {
	
	private Map<Long, CallBack> callBacks = new HashMap<>();
	
	public void register(Request request,CallBack callBack){
		if (callBack == null) {
			return;
		}
		callBacks.put(request.getId(), callBack);
	}
	
	public void remove(Long id){
		if (callBacks == null) {
			return;
		}
		callBacks.remove(id);
	}
	
	public void dispatcher(Response response){
		CallBack callBack = callBacks.get(response.getId());
		if (callBack != null) {
			callBack.call(response);
		}else{
			System.err.println("===========请求未分发==============");
		}
	}
}
