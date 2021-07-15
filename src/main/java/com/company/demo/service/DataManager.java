package com.company.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.company.demo.DAO.DeptRepository;
import com.company.demo.DAO.EmpRepository;
import com.company.demo.DAO.JWTRepository;
import com.company.demo.DAO.LoginCredRepo;
import com.company.demo.DAO.SalaryAuditRepository;
import com.company.demo.DAO.SalaryRepository;
import com.company.demo.Utility.JwtUtil;
import com.company.demo.entity.Departments;
import com.company.demo.entity.Designation;
import com.company.demo.entity.EmpSalary;
import com.company.demo.entity.Employee;
import com.company.demo.entity.JWTSecret;
import com.company.demo.entity.LoginCred;
import com.company.demo.entity.SalaryAudit;

@Component
public class DataManager {

	private static final Logger log = LoggerFactory.getLogger(DataManager.class);

	@Autowired
	DeptRepository deptrep;
	@Autowired
	EmpRepository emprep;
	@Autowired
	SalaryRepository salrep;
	@Autowired
	SalaryAuditRepository auditrep;
	@Autowired
	LoginCredRepo loginCredRepo;
	@Autowired
	EmailService emailservice;
	@Autowired
	JWTRepository jwtRepo;

	// -----------------------------------Department--------------------------------------------------
	public List<Departments> findAlldept() {
		log.info("getting all dept from db");
		return this.deptrep.findAll();
	}

	public Departments getDeptByid(int id) {
		log.info("getting dept by id from db");
		return this.deptrep.findById(id).get();
	}

	public void savedept(Departments dept) {
		this.deptrep.save(dept);
	}

	public void updateDept(Departments dept, int id) {
		Departments prevdept = getDeptByid(id);
		this.deptrep.delete(prevdept);
		this.deptrep.save(dept);
	}
	public List<Departments> findDeptbyName(String name) {
		return this.deptrep.findDeptbyname(name);
	}
	// -----------------------------------Employee----------------------------------------------------

	public List<Employee> findAllemp() {
		log.info("getting list of all employees from db");
		return this.emprep.findAll();
	}

	public Employee getEmpByid(int id) {
		return this.emprep.findById(id).get(); // using this because provide eager loading
	}
	
	public void saveEmp(Employee emp) {
		
		List<Departments> fdept = this.deptrep.findDeptbyname(emp.getDept_name());
		log.info("getting current emp who is asking for access");

		
//		System.out.println(fdept.isEmpty());
		log.info("checking condition for giving access to save");


		if (fdept.isEmpty() == false) {
			List<Employee> list = fdept.get(0).getEmp_list();
			list.add(emp);
			this.deptrep.save(fdept.get(0));
		} else {
			Departments temp = new Departments();
			if (emp.getDesignation() == Designation.HOD)
				temp.setHod(emp.getFirst_name() + " " + emp.getLast_name());
			temp.setDept_name(emp.getDept_name());
			List<Employee> list = new ArrayList<>();
			list.add(emp);
			temp.setEmp_list(list);
			this.deptrep.save(temp);
		}
		LoginCred lc= new LoginCred(emp.getEmp_id(),"emp"+emp.getEmp_id(),"user@"+emp.getEmp_id());
		updateLoginCred(lc);
		JWTSecret sc= new JWTSecret("emp"+emp.getEmp_id(),"secret"+emp.getEmp_id());
		saveJWT(sc);
	}



	public void updateEmp(Employee emp, int id) {
		Employee prevEmployee = this.getEmpByid(id);
		List<Departments> fdept = this.deptrep.findDeptbyname(emp.getDept_name());
		if (prevEmployee.getDept_name() != emp.getDept_name()) {

			if (fdept.isEmpty() == true) {
				Departments temp = new Departments();
				if (emp.getDesignation() == Designation.HOD)
					temp.setHod(emp.getFirst_name() + " " + emp.getLast_name());
				temp.setDept_name(emp.getDept_name());
				List<Employee> list = new ArrayList<>();
				list.add(emp);
				temp.setEmp_list(list);
				this.emprep.delete(prevEmployee);
				this.deptrep.save(temp);
			} else {
				this.emprep.delete(prevEmployee);
				fdept.get(0).getEmp_list().add(emp);
				if (emp.getDesignation() == Designation.HOD)
					fdept.get(0).setHod(emp.getFirst_name() + " " + emp.getLast_name());
				this.deptrep.save(fdept.get(0));
			}

		} else {
			if (emp.getDesignation() == Designation.HOD)
				fdept.get(0).setHod(emp.getFirst_name() + " " + emp.getLast_name());
			emp.setEmp_id(id);
			this.emprep.save(emp);
		}
	}

	public boolean has_resigned(int id) {
//		System.out.println("in datamanager" + id);
		log.info("checking for resignation");
		return getEmpByid(id).isHasResigned();
	}
	// -----------------------------------EmployeeSalary----------------------------------------------

	public List<EmpSalary> getSal() {
		return salrep.findAll();
	}

	public EmpSalary getsalbyid(int id) {
		return salrep.findById(id).get();
	}

	public void addSalary(EmpSalary epsl) {
		salrep.save(epsl);
	}

	public void updateSalary(int id, EmpSalary epsl) {
		epsl.setId(id);
		salrep.save(epsl);
	}

	public List<EmpSalary> salaryToPay(int size, String date) {
		return salrep.findNextEligible(5, date);
	}

	public void addSalaryDate(List<EmpSalary> list) {
		for (EmpSalary epsl : list) {
			epsl.setLastProcessed(new Date());
			salrep.save(epsl);
		}
	}

	// -----------------------------------SalaryAudit-------------------------------------------------
	public void kafkaSalary(EmpSalary epsl) {
		SalaryAudit salaryaudit = new SalaryAudit(epsl.getId(), epsl.getSalary(), new Date());
		auditrep.save(salaryaudit);
		String email = this.getEmail(epsl.getId());
		if (email != null)
			emailservice.sendMail(email, salaryaudit);
	}

	public String getEmail(int id) {
		String email = this.emprep.selectEmail(id);
		return email;
	}

	public List<SalaryAudit> allSalaryAudit() {
		return auditrep.findAll();
	}

//	-------------------------------login cred--------------------------------------------------
	public int getCurrentUserid(String userName) {
		LoginCred loginCred = loginCredRepo.findByUserName(userName);
		return loginCred.getUid();
	}

// -----------------------------------JWT repo-----------------------------------------------
	public String getJWTbyid(String userName) {
		return this.jwtRepo.getByid(userName).getJwt();
	}

	public void saveJWT(JWTSecret jwtToken) {
		this.jwtRepo.save(jwtToken);
	}

	public SalaryAudit SalaryAuditById(int id) {
		// TODO Auto-generated method stub
		return auditrep.findById(id).get();
	}

	public void updateLoginCred(LoginCred loginCred) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		String encodedPass=passwordEncoder.encode(loginCred.getPassWord());
		loginCred.setPassWord(encodedPass);
		this.loginCredRepo.save(loginCred);
	}
	public void saveSecret(JWTSecret jwtSecret) {
		this.jwtRepo.save(jwtSecret);
	}
}
