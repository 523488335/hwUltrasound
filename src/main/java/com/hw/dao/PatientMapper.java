package com.hw.dao;

import com.hw.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientMapper extends JpaRepository<Patient, Long>,JpaSpecificationExecutor<Patient>{
	
	@Query
	List<Patient> findByPatientId(Integer patientId);
	
}