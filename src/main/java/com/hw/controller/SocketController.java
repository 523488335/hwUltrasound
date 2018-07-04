package com.hw.controller;

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
     *      url：/socket/send
     *      参数：无
     *      返回：查询当前页面数据
	 * @throws HwException 
     */
    @RequestMapping("/send")
    public Object sendMessage(String mode) throws HwException{
    	socketService.sendMessage(mode);
    	return "true";
    }
}
