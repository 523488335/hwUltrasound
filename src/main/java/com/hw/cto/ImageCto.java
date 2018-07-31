package com.hw.cto;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.dao.ImageDao;
import com.hw.exception.HwException;
import com.hw.model.Diagnostic;
import com.hw.model.Image;
import com.hw.service.ImageSvc;

@RestController
public class ImageCto {
	
	@Autowired
    private ImageSvc imageSvc;
	
	/**
     * @deprecated 查询患者诊断图片：条件ID
     * @return 患者诊断图片
     */
    @RequestMapping("getPatientImage")
    public Object getPatientImage(Diagnostic patientData){
        System.out.println("------------查询id：" + patientData.getPatientDataId() + "的信息------------------------");
        Object object = imageSvc.getOriginalAndProcessedImage(patientData.getPatientDataId());
        System.out.println(object);
        return object;
    }
    
    /**
     * @deprecated 删除患者诊断图片：条件ID
     * @return 是否成功
     */
    @RequestMapping("deleteImageById")
    public Object deleteImageById(Image image) throws HwException{
        System.out.println("------------删除id：" + image.getImageId() + "的图片------------------------");
        imageSvc.deleteImage(image.getImageId());
        return "true";
    }
    
    /**
     * @deprecated 删除患者诊断图片：条件ID
     * @return 是否成功
     */
	@RequestMapping("saveImg")
    public Object saveOriginalImg(Image image) throws IOException {
		imageSvc.saveOriginalImg(image);
		return "true";
	}
	
	/**
     * @deprecated 将图片添加到报表
     * @return 是否成功
     */
    @RequestMapping("addReport")
    public Object addReport(long id) throws HwException{
    	System.out.println(id);
        imageSvc.addReport(id);
        return "true";
    }
    
    /**
     * @deprecated 将图片添加到报表
     * @return 是否成功
     */
    @RequestMapping("rmReport")
    public Object rmReport(long id) throws HwException{
        imageSvc.rmReport(id);
        return "true";
    }
	
	/**
     * @deprecated 保存医生修改后的患者诊断图片
     * @return 是否成功
     */
	@RequestMapping("saveProcessedImg")
    public Object saveProcessedImg(Image image) throws IOException {
		imageSvc.saveProcessedImg(image);
		return "true";
	}
}
