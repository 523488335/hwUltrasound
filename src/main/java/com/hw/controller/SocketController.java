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
	 * 
     *      url：/socket/send
     *      参数：无
     *      返回：向下位机发送数据
	 * @throws HwException 
     */
    @RequestMapping("/send")
    public Object sendMessage(String mode) throws HwException{
    	socketService.sendMessage(mode);
    	return "true";
    }
    /**
     *      url：/socket/start
     *      参数：无
     *      返回：开启socket服务，等待下位机连接
     * @throws IOException 
     * @throws NumberFormatException 
	 * @throws HwException 
     */
    @RequestMapping("/start")
    public Object start(HttpServletRequest request) throws NumberFormatException, IOException {
    	String port = request.getParameter("port");
		socketService.start(Integer.parseInt(port));
    	return "true";
    }
    /**
     *      url：/socket/stop
     *      参数：无
     *      返回：开启socket服务，等待下位机连接
     * @throws IOException 
     * @throws NumberFormatException 
	 * @throws HwException 
     */
    @RequestMapping("/stop")
    public Object stop() throws NumberFormatException, IOException {
		socketService.stop();
    	return "true";
    }
}
