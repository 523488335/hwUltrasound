package com.hw.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;

/**
 * 连接实例，保存连接信息
 * @author 陈尚均
 *
 */
public class Session {
	
	private static final String ENCODE = "utf-8";
	private ServerSocket server = null;
	private Socket socket = null;
	private InputStream in = null;
	private OutputStream out = null;
	private PrintWriter writer = null;
	private Manager manager = null;
	
	/**
	 * 消息缓冲池，接收到下位机消息都缓冲在缓冲池中待处理。
	 */
	private StringBuilder buffer = new StringBuilder();
	
	public Session(Manager manager){
		this.manager = manager;
	}
	/**
	 * 等待下位机连接，获取下位机连接实例
	 * @param port
	 * @throws IOException
	 */
	public void accept(int port) throws IOException{
		server = new ServerSocket(port);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						socket = server.accept();
						in = socket.getInputStream();
						out = socket.getOutputStream();
						writer = new PrintWriter(new OutputStreamWriter(out, ENCODE));
						new recvThread().start();
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("==========连接线程未知错误===========");
					}
				}
			}
		}).start();
	}
	/**
	 * 关闭soket连接
	 * @return
	 * @throws IOException
	 */
	public boolean close() throws IOException {
		writer.close();
		out.close();
		in.close();
		socket.close();
		server.close();
		return true;
	}
	/**
	 * 接收消息线程，负责接收下位机消息，处理过后交给消息调度器Manager
	 * @author 陈尚均
	 *
	 */
	private class recvThread extends Thread {
		
		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODE));
				while(true){
					if (reader.ready()) {
						buffer.append(reader.readLine());
						System.out.println(buffer);
						while(true){
							int index1,index2;
							index1 = buffer.indexOf("{");
							index2 = buffer.lastIndexOf("}");
							if (index1 != -1 && index2 > index1) {
								String json = buffer.substring(index1, index2 + 1);
								System.out.println(json);
								buffer.delete(0, index2 + 1);
								try {
									manager.dispatcher(Response.paseResponse(json));
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
	/**
	 * 向下位机发出请求
	 * @param request 请求信息
	 * @param callBack 回调
	 * @throws HwException
	 */
	public void sendMessage(Request request,CallBack callBack) throws HwException {
		if (out == null || writer == null) {
			throw new HwException(ErrorCode.流程出错, "设备未连接");
		}
		writer.println(request.toString());
		writer.flush();
		manager.register(request, callBack);
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
