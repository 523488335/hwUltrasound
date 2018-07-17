package com.hw.controller;

import com.hw.exception.HwException;
import com.hw.model.Image;
import com.hw.model.Patient;
import com.hw.model.PatientData;
import com.hw.service.ImageService;
import com.hw.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.List;

@RestController
public class PatientController {
	
    @Autowired
    private PatientService patientService;
    @Autowired
    private ImageService imageService;

    /**
     * @deprecated 翻页：翻到当前页面
     * @param url /getCurrPage
     * @return 当前页面患者列表
     */
    @RequestMapping("getCurrPage")
    public Object getPatientByPage(){
    	return patientService.getCurrPage();
    }
    
    @RequestMapping("updatePatientData")
    public Object updatePatientData(Integer patientDataId, String status){
    	patientService.updatePatientData(patientDataId, status);
    	return "true";
    }
    
    /**
     * @deprecated 翻页：翻到第一页
     * @param url /getFristPage
     * @return 首页面患者列表
     */
    @RequestMapping("getFristPage")
    public Object getFristPage(){
        return patientService.getFristPage();
    }
    
    /**
     * @deprecated 翻页：翻到最后一页
     * @param url /getLastPage
     * @return 最后一页患者列表
     */
    @RequestMapping("getLastPage")
    public Object getLastPage(){
    	return patientService.getLastPage();
    }
    
    /**
     * @deprecated 翻页：翻到前一页
     * @param url /getPreviousPage
     * @return 前一页患者列表
     */
    @RequestMapping("getPreviousPage")
    public Object getPreviousPage(){
    	return patientService.getPreviousPage();
    }
    
    /**
     * @deprecated 翻页：翻到下一页
     * @param url /getNextPage
     * @return 下一页患者列表
     */
    @RequestMapping("getNextPage")
    public Object getNextPage() throws HwException{
		return patientService.getNextPage();
    }
    
    /**
     * @deprecated 获得全部患者列表
     * @param url /getAllPatient
     * @return 全部患者列表
     */
    @RequestMapping("getAllPatient")
    public Object getAllPatientTable(){
        System.out.println("------------查询患者表所有信息------------");
        return patientService.getAllPatient();
    }

    /**
     * @deprecated 查询患者：条件ID
     * @param url /getPatientById
     * @return 患者信息
     */
    @RequestMapping("getPatientById")
    public Object getPatientById(Patient patient){
        System.out.println("------------查询患者表id=" + patient.getPatientId() + "所有信息------------");
        return patientService.getPatientById(patient.getPatientId());
    }

    /**
     * @deprecated 查询患者病例：条件ID
     * @param url /getHistoryPatientById
     * @return 患者诊断信息
     */
    @RequestMapping("getHistoryPatientById")
    public Object getHistoryPatient(Patient patient){
        System.out.println("------------查询id："+patient.getPatientId()+"的历史病例------------------------");
        return patientService.getHistoryPatient(patient.getPatientId());
    }

    /**
     * @deprecated 查询患者：条件ID，Name,Sex
     * @param url /getPatientByCondition
     * @return 患者信息
     */
    @RequestMapping("getPatientByCondition")
    public Object getPatientByCondition(Patient patient){
    	return patientService.getPatientByCondition(patient.getName(), patient.getSex(), patient.getPatientId());
    }

    /**
     * @deprecated 查询患者病例：条件ID
     * @param url /getViewModel
     * @return 患者病例信息
     */
    @RequestMapping("getViewModel")
    public Object getViewModel(PatientData patientData){
    	List<PatientData> list = patientService.getViewModel(patientData.getPatientDataId());
    	System.out.println(list.get(0));
        return list;
    }
    /**
     * @deprecated 查询患者诊断图片：条件ID
     * @param url /getPatientImage
     * @return 患者诊断图片
     */
    @RequestMapping("getPatientImage")
    public Object getPatientImage(PatientData patientData){
        System.out.println("------------查询id："+patientData.getPatientDataId()+"的信息------------------------");
        return imageService.getOriginalAndProcessedImage(patientData.getPatientDataId());
    }
    
    /**
     * @deprecated 删除患者诊断图片：条件ID
     * @param url /deleteImageById
     * @return 是否成功
     */
    @RequestMapping("deleteImageById")
    public Object deleteImageById(Image image) throws HwException{
        System.out.println("------------删除id："+image.getImageId()+"的图片------------------------");
        imageService.deleteImageById(image.getImageId());
        return "true";
    }
    
    /**
     * @deprecated 删除患者诊断图片：条件ID
     * @param url /saveImg
     * @return 是否成功
     */
	@RequestMapping("saveImg")
    public Object saveOriginalImg(Image image) throws IOException {
		imageService.saveOriginalImg(image);
		return "true";
	}
	
	/**
     * @deprecated 保存医生修改后的患者诊断图片
     * @param url /saveProcessedImg
     * @return 是否成功
     */
	@RequestMapping("saveProcessedImg")
    public Object saveProcessedImg(Image image) throws IOException {
		imageService.saveProcessedImg(image);
		return "true";
	}
}

