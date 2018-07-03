package com.hw.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.command.CallBack;
import com.hw.command.Conection;
import com.hw.command.Manager;
import com.hw.command.Request;
import com.hw.command.Response;
import com.hw.command.Response.State;
import com.hw.dao.MessageMapper;
import com.hw.exception.HwException;
import com.hw.model.Message;

@Service
public class SocketService {

	private static Conection conection;
	
	@Autowired
	private MessageMapper messageMapper;
	
	static{
		try {
			conection = new Conection(new Manager());
			conection.accept(80);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String mode) throws HwException{
		Long id = new Date().getTime();
		conection.sendMessage(new Request(id, mode), new CallBack() {
			
			@Override
			public void call(Response response) {
				// TODO Auto-generated method stub
				System.out.println(response);
				
				if (response.getState() == State.SUCESS || response.getState() == State.SUCESS_END) {
					Message message = new Message();
					message.setRespId(response.getId());
					message.setMsg(response.getMsg());
					message.setState(response.getState());
					messageMapper.save(message);
				}
				if (response.getState() == State.SUCESS_END || response.getState() == State.END) {
					conection.remove(response.getId());
				}
				if (response.getState() == State.ERROR) {
					System.err.println("下位机返回错误:" + response.getMsg());
				}
			}
		});
	}
}
