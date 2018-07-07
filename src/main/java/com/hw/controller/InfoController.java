package com.hw.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.exception.HwException;
import com.hw.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

	@Autowired
	private InfoService infoService;
	
	/**
	 * 
     *      url：/socket/send
     *      参数：无
     *      返回：向下位机发送数据
	 * @throws HwException 
	 * @throws FileNotFoundException 
     */
    @RequestMapping("/log")
    public Object log(String path) throws HwException, FileNotFoundException{
    	return infoService.parseLog(path);
    }
    
    /**
     *      参数：无
     *      返回：向下位机发送数据
	 * @throws HwException 
	 * @throws FileNotFoundException 
     */
    @RequestMapping("/pointSet")
    public Object pointSet(String path) throws HwException, FileNotFoundException{
    	return infoService.pasePointSet(path);
    }
}
