package com.hw.cto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.model.Diagnostic;
import com.hw.model.Patient;
import com.hw.service.DiagnosticSvc;

@RestController
public class PatientDataCto {
	
	@Autowired
	private DiagnosticSvc diagnosticSvc;

	@RequestMapping("updatePatientData")
    public Object updatePatientData(Integer patientDataId, String status){
		diagnosticSvc.updatePatientData(patientDataId, status);
    	return "true";
    }
    
    @RequestMapping("saveReport")
    public Object submitReport(Diagnostic patientData){
    	diagnosticSvc.saveReport(patientData);
    	return "true";
    }
    
    /**
     * @deprecated 查询患者病例：条件ID
     * @return 患者病例信息
     */
    @RequestMapping("getViewModel")
    public Object getViewModel(Diagnostic patientData){
    	List<Diagnostic> list = diagnosticSvc.getViewModel(patientData.getPatientDataId());
    	System.out.println(list.get(0));
        return list;
    }
    
    /**
     * @deprecated 查询患者病例：条件ID
     * @return 患者诊断信息
     */
    @RequestMapping("getHistoryPatientById")
    public Object getHistoryPatient(Patient patient){
        return diagnosticSvc.getHistoryPatient(patient.getPatientId());
    }
}
