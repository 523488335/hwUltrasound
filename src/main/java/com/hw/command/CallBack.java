package com.hw.command;

import com.hw.command.Response.State;

public abstract class CallBack {

	
	public void call(Response response){
		// TODO Auto-generated method stub
		System.out.println(response);
		if (response.getState() == State.SUCESS || response.getState() == State.SUCESS_END) {
			success(response);
		}
		if (response.getState() == State.ERROR || response.getState() == State.ERROR_END) {
			error(response);
		}
		if (response.getState() == State.SUCESS_END || response.getState() == State.END
			 || response.getState() == State.ERROR_END) {
			end(response);
		}
	}
	
	public abstract void success(Response response);
	public void error(Response response){
		System.err.println("下位机返回错误:" + response.getMsg());
	}
	public void end(Response response){
		Manager.getInstance().remove(response.getId());
	}
	
}
