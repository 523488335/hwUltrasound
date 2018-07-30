package com.hw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hw.model.Diagnostic;

@Repository
public interface DiagnosticDao extends JpaRepository<Diagnostic, Long>{
	
	@Query
	List<Diagnostic> findByPatientIdOrderByDate(Integer patientId);
	
	@Query
	List<Diagnostic> findByPatientDataId(Integer patientDataId);
}