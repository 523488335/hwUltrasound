package com.hw.dao;

import com.hw.model.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageMapper extends JpaRepository<Message, Long>{
	
}