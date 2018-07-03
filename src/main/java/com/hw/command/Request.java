package com.hw.command;

import com.alibaba.fastjson.JSONObject;

public class Request {

	private Long id;
	private String mode;
	
	public Request(Long id, String mode) {
		super();
		this.id = id;
		this.mode = mode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
