package com.company.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.company.demo.entity.LoginCred;
@Component
public interface LoginCredRepo extends JpaRepository<LoginCred, Integer>{

	public LoginCred findByUserName(String username);


}
