package com.hw.command;

import com.alibaba.fastjson.JSONObject;

/**
 * 下位机响应类，id对应request的id表示响应哪条请求，state表示响应状态，msg表示响应消息。
 * @author 陈尚均
 */
public class Response {

	private Long id;
	private Integer state;
	private String msg;
	
	/**
	 * 响应状态定义
	 * @author 陈尚均
	 */
	public static class State{
		public static final int ERROR = 0;
		public static final int SUCESS = 1;
		public static final int END = 2;
		public static final int SUCESS_END = 3;
		public static final int ERROR_END = 4;
	}
	
	public static Response paseResponse(String json){
		return JSONObject.parseObject(json, Response.class);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", state=" + state + ", msg=" + msg + "]";
	}
}
