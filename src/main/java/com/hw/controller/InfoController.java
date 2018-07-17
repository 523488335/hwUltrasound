package com.hw.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.dao.PatientDataMapper;
import com.hw.exception.HwException;
import com.hw.model.PatientData;
import com.hw.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

	@Autowired
	private InfoService infoService;
	
	@Autowired
	private PatientDataMapper patientDataMapper;
	
	/**
     * @deprecated 获得病人原始数据目录列表
     * @param url /info/data
     * @param dataPath 原始数据目录
     * @return 原始数据文件目录列表
	 * @throws FileNotFoundException 
     */
    @RequestMapping("/data")
    public Object data(String dataPath) throws HwException, FileNotFoundException{
    	System.out.println(dataPath);
    	return infoService.parsePath(dataPath);
    }
    
    /**
     * @deprecated 获得呼吸数据点
     * @param url /info/point2dSet
     * @param patientDataId 病例号
     * @return 胸型3d点集合
	 * @throws HwException,FileNotFoundException 
     */
    @RequestMapping("/pointSet")
    public Object pointSet(int patientDataId) throws HwException, FileNotFoundException{
    	PatientData patientData = patientDataMapper.findByPatientDataId(patientDataId).get(0);
    	return infoService.pasePointSet(patientData.getDataPath());
    }
    /**
     * @deprecated 获得呼吸数据点集合
     * @param url /info/point2dSet
     * @param patientDataId 病例号
     * @return 呼吸数据点集合
	 * @throws HwException,FileNotFoundException 
     */
    @RequestMapping("/point2dSet")
    public Object point2dSet(int patientDataId) throws HwException, FileNotFoundException{
    	Map<String, Object> map = new  HashMap<String, Object>();
    	PatientData patientData = patientDataMapper.findByPatientDataId(patientDataId).get(0);
    	map.put("left", infoService.pasePoint2dSet(patientData.getDataPath(),InfoService.BREATHE_FILE_LEFT));
    	map.put("center", infoService.pasePoint2dSet(patientData.getDataPath(),InfoService.BREATHE_FILE_CENTER));
    	map.put("right", infoService.pasePoint2dSet(patientData.getDataPath(),InfoService.BREATHE_FILE_RIGHT));
    	return map;
    }
    
    /**
     * @deprecated 文件下载
     * @param url /info/download
     * @param filename 文件路径
     * @return 文件流
     */
    @RequestMapping("/download")
    public String downLoad(HttpServletRequest request,HttpServletResponse response){
    	if(infoService.fileDownload(request.getParameter("filename"), response)){
    		return null;
    	}
    	return "false";
    }
    
}
