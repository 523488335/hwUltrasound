package com.hw.command;

import com.alibaba.fastjson.JSONObject;

public class Response {

	private Long id;
	private Integer state;
	private String msg;
	
	public static class State{
		public static final int ERROR = 0;
		public static final int SUCESS = 1;
		public static final int END = 2;
		public static final int SUCESS_END = 3;
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
