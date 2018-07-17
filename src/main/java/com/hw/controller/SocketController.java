package com.hw.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.exception.HwException;
import com.hw.service.SocketService;


@RestController
@RequestMapping("/socket")
public class SocketController {

	@Autowired
	private SocketService socketService;
	
	/**
     * @deprecated 向连接者发送指令
     * @param url /socket/send
     * @param path 文件路径
	 * @throws HwException 
     */
    @RequestMapping("/send")
    public Object sendMessage(String mode) throws HwException{
    	socketService.sendMessage(mode);
    	return "true";
    }
    /**
     * @deprecated 启动socket服务器
     * @param url /socket/send
     * @param port socket服务器端口
	 * @throws HwException 
     */
    @RequestMapping("/start")
    public Object start(HttpServletRequest request) throws NumberFormatException, IOException {
    	String port = request.getParameter("port");
		socketService.start(Integer.parseInt(port));
    	return "true";
    }
    /**
     * @deprecated 停止socket服务器
     * @param url /socket/stop
	 * @throws NumberFormatException, IOException 
     */
    @RequestMapping("/stop")
    public Object stop() throws NumberFormatException, IOException {
		socketService.stop();
    	return "true";
    }
}
