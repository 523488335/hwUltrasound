package com.hw.dao;

import com.hw.model.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMapper extends JpaRepository<Image, Long>,JpaSpecificationExecutor<Image>{
	
	@Query
	List<Image> findByPatientDataId(Integer patientDataId);
	@Query
	List<Image> findByType(short type);
}