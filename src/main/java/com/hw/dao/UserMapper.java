package com.hw.dao;

import com.hw.model.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User, Long>{
	
	@Query
	List<User> findById(int id);
	
	@Query
	List<User> findByUsername(String username);
	
	@Query
	List<User> findByUsernameAndPassword(String username,String password);
}