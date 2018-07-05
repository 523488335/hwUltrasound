package com.hw.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.command.CallBack;
import com.hw.command.Session;
import com.hw.command.Manager;
import com.hw.command.Request;
import com.hw.command.Response;
import com.hw.dao.MessageMapper;
import com.hw.exception.HwException;
import com.hw.model.Message;

@Service
public class SocketService {

	private static Session conection;
	
	@Autowired
	private MessageMapper messageMapper;
	
	public void start(int port) throws IOException{
		conection = new Session(Manager.getInstance());
		conection.accept(port);
	}
	
	public void sendMessage(String mode) throws HwException{
		Long id = new Date().getTime();
		conection.sendMessage(new Request(id, mode), new CallBack() {
			
			@Override
			public void success(Response response) {
				Message message = new Message();
				message.setRespId(response.getId());
				message.setMsg(response.getMsg());
				message.setState(response.getState());
				messageMapper.save(message);
			}
		});
	}
}
