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

@RestController
public class PatientMapperController {
	
    @Autowired
    private PatientService patientService;
    @Autowired
    private ImageService imageService;

    /**
     *      url：getCurrPage
     *      参数：无
     *      返回：查询当前页面数据
     */
    @RequestMapping("getCurrPage")
    public Object getPatientByPage(){
    	return patientService.getCurrPage();
    }
    
    /**
     *      url：getFristPage
     *      参数：无
     *      返回：查询首页面数据
     */
    @RequestMapping("getFristPage")
    public Object getFristPage(){
        return patientService.getFristPage();
    }
    
    /**
     *      url：getLastPage
     *      参数：无
     *      返回：查询最后页面数据
     */
    @RequestMapping("getLastPage")
    public Object getLastPage(){
    	return patientService.getLastPage();
    }
    
    /**
     *      url：getPreviousPage
     *      参数：无
     *      返回：查询上一页面数据
     */
    @RequestMapping("getPreviousPage")
    public Object getPreviousPage(){
    	return patientService.getPreviousPage();
    }
    
    /**
     *      url：getNextPage
     *      参数：无
     *      返回：查询上一页面数据
     * @throws HwException 
     */
    @RequestMapping("getNextPage")
    public Object getNextPage() throws HwException{
		return patientService.getNextPage();
    }
    
    /**
     *      url：getAllPatient
     *      参数：无
     *      返回：查询表中所有数据
     */
    @RequestMapping("getAllPatient")
    public Object getAllPatientTable(){
        System.out.println("------------查询患者表所有信息------------");
        return patientService.getAllPatient();
    }

    /**
     *      url：getPatientById
     *      参数：int PatientId
     *      返回：根据PatientId查询
     */
    @RequestMapping("getPatientById")
    public Object getPatientById(Patient patient){
        System.out.println("------------查询患者表id=" + patient.getPatientId() + "所有信息------------");
        return patientService.getPatientById(patient.getPatientId());
    }

    /**
     *      url：getHistoryPatientById
     *      参数：int PatientId
     *      返回：返回包括历史病例的所有数据，按时间降序排序
     */
    @RequestMapping("getHistoryPatientById")
    public Object getHistoryPatient(Patient patient){
        System.out.println("------------查询id："+patient.getPatientId()+"的历史病例------------------------");
        return patientService.getHistoryPatient(patient.getPatientId());
    }

    /**
     *      url：getPatientByCondition
     *      参数：Patient patient
     *      返回：返回条件检索查询出来的患者列表
     */
    @RequestMapping("getPatientByCondition")
    public Object getPatientByCondition(Patient patient){
    	return patientService.getPatientByCondition(patient.getName(), patient.getSex(), patient.getPatientId());
    }

    /**
     *      url：getViewModel
     *      参数：int patientId, String date
     *      返回：根据检查时间，返回一个患者的所有信息（填充信息模态框）
     */
    @RequestMapping("getViewModel")
    public Object getViewModel(PatientData patientData){
        return patientService.getViewModel(patientData.getPatientDataId());
    }
    /**
     *      url：getViewModel
     *      参数：int patientId, String date
     *      返回：根据检查时间，返回一个患者的所有信息（填充信息模态框）
     */
    @RequestMapping("getPatientImage")
    public Object getPatientImage(PatientData patientData){
        System.out.println("------------查询id："+patientData.getPatientDataId()+"的信息------------------------");
        return imageService.getOriginalAndProcessedImage(patientData.getPatientDataId());
    }
    
    /**
     *      url：/-Saveimg
     *      参数：String canvas,int PatientDataId,boolean isLeft
     *      返回：保存缩略图
     */
	@RequestMapping("saveImg")
    public void saveOriginalImg(Image image) throws IOException {
		imageService.saveOriginalImg(image);
	}
	
	/**
     *      url：/-saveProcessedImg
     *      参数：String canvas,int PatientDataId,boolean isLeft
     *      返回：保存缩略图
     */
	@RequestMapping("saveProcessedImg")
    public void saveProcessedImg(Image image) throws IOException {
		imageService.saveProcessedImg(image);
	}
}

