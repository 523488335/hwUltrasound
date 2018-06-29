package com.hw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hw.model.PatientData;

@Repository
public interface PatientDataMapper extends JpaRepository<PatientData, Long>{
	
	@Query
	List<PatientData> findByPatientIdOrderByDate(Integer patientId);
	
	@Query
	List<PatientData> findByPatientDataIdOrderByDate(Integer patientDataId);
}