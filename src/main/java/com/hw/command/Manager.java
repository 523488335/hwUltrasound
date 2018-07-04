package com.hw.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息管理类
 * @author 陈尚均
 *
 */
public class Manager {
	
	/**
	 * 上位机发出请求的注册表，Long为request的id，CallBack是收到消息的回调。
	 */
	private Map<Long, CallBack> callBacks = new HashMap<>();
	/**
	 * Manager的单例
	 */
	private static Manager instance;
	
	private Manager(){
		
	}
	public static Manager getInstance(){
		if (instance == null) {
			instance = new Manager();
		}
		return instance;
	}
	/**
	 * 注册请求
	 * @param request 请求
	 * @param callBack 响应请求的回调
	 */
	public void register(Request request,CallBack callBack){
		if (callBack == null) {
			return;
		}
		callBacks.put(request.getId(), callBack);
	}
	/**
	 * 删除注册表中的注册信息
	 * @param id
	 */
	public void remove(Long id){
		if (callBacks == null) {
			return;
		}
		callBacks.remove(id);
	}
	/**
	 * 消息分发机制，根据response的id分发到对应的回调。
	 * @param response
	 */
	public void dispatcher(Response response){
		CallBack callBack = callBacks.get(response.getId());
		if (callBack != null) {
			callBack.call(response);
		}else{
			System.err.println("===========请求未分发==============");
		}
	}
}
