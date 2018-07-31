package com.hw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.dao.DiagnosticDao;
import com.hw.model.Diagnostic;

@Service
public class DiagnosticSvc {

	@Autowired
	private DiagnosticDao diagnosticDao;
	
	public List<Diagnostic> getHistoryPatient(Integer id) {
        return diagnosticDao.findByPatientIdOrderByDate(id);
    }
	
	public List<Diagnostic> getViewModel(Integer id) {
        return diagnosticDao.findByPatientDataId(id);
    }
	
	public void updatePatientData(Integer patientDataId, String status){
		Diagnostic p = diagnosticDao.findByPatientDataId(patientDataId).get(0);
		p.setStatus(status);
		diagnosticDao.save(p);
	}
	
	public void saveReport(Diagnostic patientData){
		System.out.println(patientData);
		Diagnostic p = diagnosticDao.findByPatientDataId(patientData.getPatientDataId()).get(0);
		p.setBreastFinding(patientData.getBreastFinding());
		p.setOxterFinding(patientData.getOxterFinding());
		p.setHint(patientData.getHint());
		diagnosticDao.save(p);
	}
}
