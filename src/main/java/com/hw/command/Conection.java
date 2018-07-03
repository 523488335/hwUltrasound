package com.hw.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;

public class Conection {

	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	private PrintWriter writer = null;
	private Manager manager = null;
	
	private StringBuilder buffer = new StringBuilder();
	
	public Conection(Manager manager){
		this.manager = manager;
	}
	
	@SuppressWarnings("resource")
	public void accept(int port) throws IOException{
		ServerSocket server = new ServerSocket(port);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						socket = server.accept();
						in = socket.getInputStream();
						out = socket.getOutputStream();
						new recvThread().start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private class recvThread extends Thread{
		
		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
				while(true){
					if (reader.ready()) {
						buffer.append(reader.readLine());
						while(true){
							int index1,index2;
							index1 = buffer.indexOf("{");
							index2 = buffer.indexOf("}");
							if (index1 != -1 && index2 > index1) {
								String json = buffer.substring(index1, index2 + 1);
								System.out.println(json);
								buffer.delete(0, index2 + 1);
								System.out.println(buffer.length());
								try {
									manager.dispatcher(JSONObject.parseObject(json, Response.class));
								} catch (Exception e) {
									e.printStackTrace();
									System.err.println("========消息格式错误=========");
								}
							}else{
								break;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendMessage(Request message,CallBack callBack) throws HwException {
		if (out == null) {
			throw new HwException(ErrorCode.流程出错, "设备未连接");
		}
		if (writer == null) {
			try {
				writer = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				throw new HwException(ErrorCode.编程错误, "编码不能识别");
			}
		}
		writer.println(message.toString());
		writer.flush();
		manager.register(message, callBack);
	}
	public void sendMessage(Request message) throws HwException{
		manager.register(message, null);
	}
	public void remove(Request request){
		remove(request.getId());
	}
	public void remove(Long id){
		manager.remove(id);
	}
}
