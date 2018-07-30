package com.hw.cto;

import com.hw.exception.HwException;
import com.hw.model.Patient;
import com.hw.service.PatientSvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientCto {
	
    @Autowired
    private PatientSvc patientSvc;

    /**
     * @deprecated 翻页：翻到当前页面
     * @param url /getCurrPage
     * @return 当前页面患者列表
     */
    @RequestMapping("getCurrPage")
    public Object getPatientByPage(){
    	return patientSvc.getCurrPage();
    }
    
    /**
     * @deprecated 翻页：翻到第一页
     * @param url /getFristPage
     * @return 首页面患者列表
     */
    @RequestMapping("getFristPage")
    public Object getFristPage(){
        return patientSvc.getFristPage();
    }
    
    /**
     * @deprecated 翻页：翻到最后一页
     * @param url /getLastPage
     * @return 最后一页患者列表
     */
    @RequestMapping("getLastPage")
    public Object getLastPage(){
    	return patientSvc.getLastPage();
    }
    
    /**
     * @deprecated 翻页：翻到前一页
     * @param url /getPreviousPage
     * @return 前一页患者列表
     */
    @RequestMapping("getPreviousPage")
    public Object getPreviousPage(){
    	return patientSvc.getPreviousPage();
    }
    
    /**
     * @deprecated 翻页：翻到下一页
     * @param url /getNextPage
     * @return 下一页患者列表
     */
    @RequestMapping("getNextPage")
    public Object getNextPage() throws HwException{
		return patientSvc.getNextPage();
    }
    
    /**
     * @deprecated 获得全部患者列表
     * @param url /getAllPatient
     * @return 全部患者列表
     */
    @RequestMapping("getAllPatient")
    public Object getAllPatientTable(){
        System.out.println("------------查询患者表所有信息------------");
        return patientSvc.getAllPatient();
    }

    /**
     * @deprecated 查询患者：条件ID
     * @param url /getPatientById
     * @return 患者信息
     */
    @RequestMapping("getPatientById")
    public Object getPatientById(Patient patient){
        System.out.println("------------查询患者表id=" + patient.getPatientId() + "所有信息------------");
        return patientSvc.getPatientById(patient.getPatientId());
    }

    /**
     * @deprecated 查询患者：条件ID，Name,Sex
     * @param url /getPatientByCondition
     * @return 患者信息
     */
    @RequestMapping("getPatientByCondition")
    public Object getPatientByCondition(Patient patient){
    	return patientSvc.getPatientByCondition(patient.getName(), patient.getSex(), patient.getPatientId());
    }
}

