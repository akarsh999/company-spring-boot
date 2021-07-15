package com.company.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.company.demo.entity.JWTSecret;

@Component
public interface JWTRepository extends JpaRepository<JWTSecret, Integer>{
	@Query("select u FROM JWTSecret u WHERE u.id= :n")
	public JWTSecret getByid(@Param("n") String id);
	
}
