package com.hw.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	static{
		try {
			conection = new Session(Manager.getInstance());
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
