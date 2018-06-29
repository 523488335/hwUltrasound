package com.hw.exception;


public class HwException extends Exception{
	
	private static final long serialVersionUID = 2562764480374200175L;
	
	private ErrorCode errorCode;

	public HwException(ErrorCode errorCode,String msg){
		super(msg);
		this.errorCode = errorCode;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return errorCode + ":" + super.getMessage();
	}
}
