package com.company.demo.Utility;

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
public class WebJwtAuth {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	JWTcache jwtCache;
	@Autowired
    private LoginDetailsService loginService;
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthorization.class);

	
	public boolean basicJWTAuth(String Auth,String UserName) {
		
		String authorizationHeader = Auth;
		String clientUserName = UserName;
		String token=null;
		String userName=null;
		String jwtKey=null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer") && clientUserName!=null) {
			try {
				token = authorizationHeader.substring(6);
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
	public boolean VPauthorization(String Auth,String UserName) {
		log.info("checking for VPauthorization");
		if(basicJWTAuth(Auth,UserName) && VPDesignation(UserName))return true;
		return false;
	}
	
	public boolean HODauthorization(String Auth,String UserName, String dept) {
		log.info("checking for HODauthorization");
		if(basicJWTAuth(Auth,UserName) && 
				HODDesignation(UserName,dept))return true;
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
	public boolean SelfDataEditAuth(String Auth,String UserName, int id){
		
		log.info("checking for SelfDataEditAuth");
		int currentUserid=this.jwtCache.getCurrentIdOfUser(UserName);
		if(basicJWTAuth(Auth,UserName) && 
				currentUserid==id)return true;
		return false;
	}
	
	public boolean EmpSaveAuth(String Auth,String UserName, Employee emp) {
		
		log.info("checking permission for adding new employee details");
		if(!basicJWTAuth(Auth,UserName))return false;
		if(VPDesignation(UserName) || HODDesignation(UserName, emp.getDept_name()))return true;
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
	public boolean EmpEditAuth(String Auth,String UserName, Employee emp, int id){
		log.info("checking for edit permission of employee data");
		if(emp.getEmp_id()!=id)return false;
		if(isDeptChange(emp,id) || isDesignationChange(emp, id)) {
			if(VPauthorization(Auth, UserName))return true;
			else return false;
		}
		if(EmpSaveAuth(Auth, UserName, emp) || SelfDataEditAuth(Auth, UserName, emp.getEmp_id()))return true;
		return false;
	}
}
