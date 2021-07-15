package com.company.demo.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.company.demo.DAO.EmpRepository;
import com.company.demo.DAO.LoginCredRepo;
import com.company.demo.Exceptions.UnauthorizedUserException;
import com.company.demo.entity.Designation;
import com.company.demo.entity.EmpSalary;
import com.company.demo.entity.Employee;
import com.company.demo.entity.JWTSecret;
import com.company.demo.entity.LoginCred;

@Component
public class populateDatabase {
	@Autowired
	DataManager datamanager;
	@Autowired
	EmpRepository emprep;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	LoginCredRepo loginCredRepo;
	public enum title{
		kumar,
		shah,
		jain,
		khan,
		gupta,
		kapoor,
		ali,
		singla,
		kaur,
		arya,
		pandey
	}
	
	@SuppressWarnings("deprecation")
	public void populateDB() {
		System.out.println(datamanager.getEmail(1001));
		for(int i=1;i<=50;i++) {
//			Employee emp=new Employee();
//			emp.setEmp_id(i+1000);
//			int j= new Random().nextInt(5)+1;
//			emp.setDept_name("Dept "+j);
//			emp.setFirst_name("emp"+i);
//			j= new Random().nextInt(10);
//			emp.setLast_name(title.values()[j].toString());
//			j= new Random().nextInt(10);
//			if(j==4)j++;
//			emp.setDesignation(Designation.values()[j]);
//			emp.setHasResigned(false);
//			emp.setEmail(emp.getFirst_name()+"@company.com");
//			try {
//				datamanager.saveEmp(emp);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			EmpSalary empsl= new EmpSalary();
//			empsl.setId(i+1000);
//			empsl.setSalary(new Random().nextInt(5000));
////			empsl.setLastProcessed(new Date());
//			try {
//				datamanager.addSalary(empsl);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(); 
//			String encodedPw="user@"+(1000+i);
//			String p =passwordEncoder.encode(encodedPw);
//			LoginCred loginCred=new LoginCred(1000+i,"emp"+(1000+i),p);
//			loginCredRepo.save(loginCred);
			
//			JWTSecret jwtsecret = new JWTSecret("emp"+(1000+i),"secret"+(1000+i));
//			this.datamanager.saveJWT(jwtsecret);
		}
		
		//make VP
//		Employee emp = new Employee();
//		emp.setDesignation(Designation.VP);
//		emp.setEmail("vp@company.com");
//		emp.setEmp_id(1000);
//		emp.setFirst_name("emp1000");
//		emp.setHasResigned(false);
//		emp.setLast_name(title.pandey.toString());
//		emprep.save(emp);
//		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(); 
//		String encodedPw="user@"+(1000);
//		String p =passwordEncoder.encode(encodedPw);
//		LoginCred loginCred=new LoginCred(1000,"emp"+(1000),p);
//		loginCredRepo.save(loginCred);
//		JWTSecret jwtsecret = new JWTSecret("emp"+(1000),"secret"+(1000));
//		this.datamanager.saveJWT(jwtsecret);
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DATE, 1);
//		String d=formatter.format(cal.getTime());
//		List<EmpSalary>salarylist=this.datamanager.salaryToPay(5,d); 
//		for(EmpSalary empsl: salarylist)System.out.println(empsl.getId());
	}
	
}
