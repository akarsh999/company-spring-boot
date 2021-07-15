package com.company.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.demo.DAO.LoginCredRepo;
import com.company.demo.Exceptions.AccountDisabledException;
import com.company.demo.entity.LoginCred;

@Service
public class LoginDetailsService implements UserDetailsService{
	@Autowired
	private LoginCredRepo loginCredRepo;
	@Autowired
	private DataManager dataManager;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginCred user=loginCredRepo.findByUserName(username);
		if(this.dataManager.has_resigned(user.getUid())) {
			throw new AccountDisabledException();
		}
		return new User(user.getUserName(),user.getPassWord(),new ArrayList<>());
		
	}

}
