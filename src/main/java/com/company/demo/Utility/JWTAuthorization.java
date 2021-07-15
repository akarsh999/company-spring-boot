package com.company.demo.Utility;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.company.demo.Exceptions.InvalidCredentialException;
import com.company.demo.entity.Designation;
import com.company.demo.entity.Employee;
import com.company.demo.service.JWTcache;
import com.company.demo.service.LoginDetailsService;

@Component
public class JWTAuthorization {
	
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	JWTcache jwtCache;
	@Autowired
    private LoginDetailsService loginService;
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthorization.class);

	
	public boolean basicJWTAuth(HttpServletRequest request) {
		
		String authorizationHeader = request.getHeader("Authorization");
		String clientUserName = request.getHeader("UserName");
		String token=null;
		String userName=null;
		String jwtKey=null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && clientUserName!=null) {
			try {
				token = authorizationHeader.substring(7);
				log.debug("getting client username from payload of JWT");
				jwtKey=this.jwtCache.getJWTbyid(clientUserName);
				log.info("getting username after validation");
//				log.info(jwtKey);
	            userName = this.jwtUtil.extractUsername(token, jwtKey);
			}
			catch (Exception e) {
				log.error("error in basicJWTAuth method", e);
				throw new InvalidCredentialException();
			}  
        }
		if(userName!=null) {
			UserDetails userDetails = loginService.loadUserByUsername(userName);
            boolean b=this.jwtUtil.validateToken(token, userDetails, this.jwtCache.getJWTbyid(clientUserName));
            return b;
		}
		return false;
	}
	public boolean VPauthorization(HttpServletRequest request) {
		log.info("checking for VPauthorization");
		if(basicJWTAuth(request) && VPDesignation(request.getHeader("UserName")))return true;
		return false;
	}
	
	public boolean HODauthorization(HttpServletRequest request, String dept) {
		log.info("checking for HODauthorization");
		if(basicJWTAuth(request) && 
				HODDesignation(request.getHeader("UserName"),dept))return true;
		return false;
	}
	public boolean VPDesignation(String UserName) {
		log.info("extracting designation for vp from jwtCache class");
		Designation designation = this.jwtCache.getDesignationByUserName(UserName);
		if(designation==Designation.VP)return true;
		return false;
	}
	public boolean HODDesignation(String UserName, String dept) {
		log.info("extracting designation from jwtCache class");
		Designation designation = this.jwtCache.getDesignationByUserName(UserName);
		if(designation==Designation.HOD && this.jwtCache.getDeptbyUserName(UserName).equals(dept))return true;
		return false;
	}
	public boolean SelfDataEditAuth(HttpServletRequest request, int id){
		
		log.info("checking for SelfDataEditAuth");
		int currentUserid=this.jwtCache.getCurrentIdOfUser(request.getHeader("UserName"));
		if(basicJWTAuth(request) && 
				currentUserid==id)return true;
		return false;
	}
	
	public boolean EmpSaveAuth(HttpServletRequest request, Employee emp) {
		
		log.info("checking permission for adding new employee details");
		if(!basicJWTAuth(request))return false;
		if(VPDesignation(request.getHeader("UserName")) || HODDesignation(request.getHeader("UserName"), emp.getDept_name()))return true;
		return false;
	}
	public boolean isHODChange(Employee emp, int id) {
		log.info("checking if hod is changed");
		if(emp.getDesignation()==Designation.HOD && this.jwtCache.getDesignationByid(id)!=Designation.HOD)return true;
		return false;
	}
	public boolean isVPChange(Employee emp, int id) {
		log.info("checking if vp is changed");
		if(emp.getDesignation()==Designation.VP && this.jwtCache.getDesignationByid(id)!=Designation.VP)return true;
		return false;
	}
	public boolean isDesignationChange(Employee emp, int id) {
		log.info("checking if designation is changed");
		if(emp.getDesignation()!=this.jwtCache.getDesignationByid(id))return true;
		return false;
	}
	public boolean isDeptChange(Employee emp, int id) {
		log.info("checking if designation is changed");
		if(!emp.getDept_name().equals(this.jwtCache.getDeptByid(id)))return true;
		return false;
	}
	public boolean EmpEditAuth(HttpServletRequest request, Employee emp, int id){
		log.info("checking for edit permission of employee data");
		if(emp.getEmp_id()!=id)return false;
		if(isDeptChange(emp,id) || isDesignationChange(emp, id)) {
			if(VPauthorization(request))return true;
			else return false;
		}
		if(EmpSaveAuth(request, emp) || SelfDataEditAuth(request, emp.getEmp_id()))return true;
		return false;
	}
}
