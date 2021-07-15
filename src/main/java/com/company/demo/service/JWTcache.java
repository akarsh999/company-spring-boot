package com.company.demo.service;


import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.company.demo.DAO.JWTRepository;
import com.company.demo.entity.Designation;
import com.company.demo.entity.Employee;

@Service
@CacheConfig(cacheNames = {"jwttoken","cache1","cache2","cache3","cache4","cache5"})
public class JWTcache {
	@Autowired
	DataManager dataManager;

	
	@Cacheable(cacheNames = "jwttoken")
	public String getJWTbyid(String userName1) {
		return this.dataManager.getJWTbyid(userName1);
	}
	
	@Cacheable(cacheNames = "cache1")
	public Designation getDesignationByUserName(String userName2) {
		int currentUserid = getCurrentIdOfUser(userName2);
		return this.dataManager.getEmpByid(currentUserid).getDesignation();
	}

	public String getCurrentUsername(String token){
		String payload = token.split("\\.")[1];  
		Base64.Decoder decoder = Base64.getDecoder();
		Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(new String(decoder.decode(payload)));
        m.find();
        m.find();
        return m.group(1);
	}
	
	@Cacheable(cacheNames = "cache2")
	public String getDeptbyUserName(String userName3) {
		// TODO Auto-generated method stub
		int currentUserid = getCurrentIdOfUser(userName3);
		return this.dataManager.getEmpByid(currentUserid).getDept_name();
	}
	
	@Cacheable(cacheNames = "cache3")
	public int getCurrentIdOfUser(String UserName4) {
		return this.dataManager.getCurrentUserid(UserName4);
	}
	
	@Cacheable(cacheNames = "cache4")
	public Designation getDesignationByid(int id) {
		return this.dataManager.getEmpByid(id).getDesignation();
	}
	@Cacheable(cacheNames = "cache5")
	public String getDeptByid(int id) {
		return this.dataManager.getEmpByid(id).getDept_name();
	}
}
