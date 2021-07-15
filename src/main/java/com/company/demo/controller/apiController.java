package com.company.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.demo.DAO.LoginCredRepo;
import com.company.demo.Exceptions.ApiError;
import com.company.demo.Exceptions.NoDataFoundException;
import com.company.demo.Exceptions.SuccessMsg;
import com.company.demo.Exceptions.UnauthorizedUserException;
import com.company.demo.Utility.JWTAuthorization;
import com.company.demo.Utility.JwtUtil;
import com.company.demo.entity.AuthRequest;
import com.company.demo.entity.Departments;
import com.company.demo.entity.Designation;
import com.company.demo.entity.EmpSalary;
import com.company.demo.entity.Employee;
import com.company.demo.entity.JWTSecret;
import com.company.demo.entity.LoginCred;
import com.company.demo.entity.SalaryAudit;
import com.company.demo.service.DataManager;
import com.company.demo.service.populateDatabase;

import net.bytebuddy.implementation.bytecode.Throw;

@RestController

public class apiController {

	private static final Logger log = LoggerFactory.getLogger(apiController.class);
	@Autowired
	DataManager dataManager;
	@Autowired
	populateDatabase populatedatabase;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTAuthorization jwtAuth;

//	-----------------------------------------------Department---------------------------------------------
	@GetMapping("/dept")
	ResponseEntity<List<Departments>> getDept(HttpServletRequest request) {

		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting all departments info");
		List<Departments> allDepts = this.dataManager.findAlldept();

		if (allDepts != null)
			return ResponseEntity.ok(allDepts);
		else
			throw new NoDataFoundException();

	}

	@GetMapping("/dept/{id}")
	ResponseEntity<Departments> getDeptid(HttpServletRequest request, @PathVariable("id") int id) {

		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");

		log.info("getting department by id from database");
		Departments dept1 = this.dataManager.getDeptByid(id);
		if (dept1 != null)
			return ResponseEntity.ok(dept1);
		else
			throw new NoDataFoundException();
	}

	@PostMapping("/dept")
	ResponseEntity<?> saveDept(HttpServletRequest request, @RequestBody Departments dept) {

		log.info("checking for VP authorization");
		if (!jwtAuth.VPauthorization(request))
			throw new UnauthorizedUserException("Current user is unauthorized");

		log.info("Saving new department to database");
		this.dataManager.savedept(dept);
		SuccessMsg sm= new SuccessMsg(HttpStatus.CREATED, LocalDateTime.now(), "Department created with id "+dept.getDept_id());
		return new ResponseEntity<>(sm,HttpStatus.CREATED);
	}

	@PutMapping("/dept/{id}")
	ResponseEntity<?> updateDept(HttpServletRequest request, @RequestBody Departments dept,
			@PathVariable("id") int id) {
		log.info("checking for VP authorization");
		if (!jwtAuth.VPauthorization(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
//		boolean checks whether user permitted to change data or not
//		System.out.println(currentUserid);
		this.dataManager.updateDept(dept, id);
		SuccessMsg sm= new SuccessMsg(HttpStatus.CREATED, LocalDateTime.now(), "Department details updated id: "+id);
		return new ResponseEntity<>(sm,HttpStatus.CREATED);
	}
//	----------------------------------------Employee-----------------------------------------------

	@GetMapping("/emp")
	ResponseEntity<List<Employee>> getEmp(HttpServletRequest request) {
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting all employees info from database");
		List<Employee> allEmps = this.dataManager.findAllemp();
		if (allEmps != null)
			return ResponseEntity.ok(allEmps);
		else
			throw new NoDataFoundException();
	}

	@GetMapping("/emp/{id}")
	ResponseEntity<Employee> getEmpid(HttpServletRequest request, @PathVariable("id") int id) {
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		Employee emp = this.dataManager.getEmpByid(id);
//		log.debug(emp.getFirst_name());
		if (emp != null)
			return ResponseEntity.ok(emp);
		else
			throw new NoDataFoundException();
	}

	@PostMapping("/emp")
	ResponseEntity<?> SaveEmp(HttpServletRequest request, @RequestBody Employee emp) {

		log.info("checking for EMPSave authorization");
		if (!this.jwtAuth.EmpSaveAuth(request, emp))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("saving employee data");
		this.dataManager.saveEmp(emp);
		SuccessMsg sm= new SuccessMsg(HttpStatus.CREATED, LocalDateTime.now(), "User created with id "+emp.getEmp_id());
		return new ResponseEntity<>(sm,HttpStatus.CREATED);
	}

	@PostMapping("/emp/list")
	ResponseEntity<?> saveEmpList(HttpServletRequest request, @RequestBody List<Employee> list) {
		for (Employee emp : list) {
			if (!this.jwtAuth.EmpSaveAuth(request, emp))
				throw new UnauthorizedUserException("Current user is unauthorized");
		}
		for (Employee emp : list) {
			log.info("saving employee data");
			this.dataManager.saveEmp(emp);
		}
		SuccessMsg sm= new SuccessMsg(HttpStatus.CREATED, LocalDateTime.now(), "Users created");
		return new ResponseEntity<>(sm,HttpStatus.CREATED);
	}

	@PutMapping("/emp/{id}")
	ResponseEntity<?> updateEmp(HttpServletRequest request, @RequestBody Employee emp, @PathVariable("id") int id) {

		log.info("checking for EMPEdit authorization");
		if (!this.jwtAuth.EmpEditAuth(request, emp, id))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("updating employee data");
		this.dataManager.updateEmp(emp, id);
		SuccessMsg sm= new SuccessMsg(HttpStatus.ACCEPTED, LocalDateTime.now(), "User Updated with id "+emp.getEmp_id());
		return new ResponseEntity<>(sm,HttpStatus.ACCEPTED);
	}

//	--------------------------------------------salary inf--------------------------------------

	@GetMapping("/salary")
	ResponseEntity<List<EmpSalary>> getallSalary(HttpServletRequest request) {

		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting all salary data from database");
		return ResponseEntity.ok(this.dataManager.getSal());
	}

	@GetMapping("/salary/{id}")
	ResponseEntity<EmpSalary> getsalarybyid(HttpServletRequest request, @PathVariable("id") int id) {

		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting salary data by id from database");
		return ResponseEntity.ok(this.dataManager.getsalbyid(id));
	}

	@PostMapping("/salary")
	ResponseEntity<?> postSalary(HttpServletRequest request, @RequestBody EmpSalary empsl) {

		log.info("checking for VP authorization");
		if (!jwtAuth.VPauthorization(request))
			throw new UnauthorizedUserException("Current user is unauthorized");

		log.info("saving salary data of employee");

		this.dataManager.addSalary(empsl);
		SuccessMsg sm= new SuccessMsg(HttpStatus.CREATED, LocalDateTime.now(), "Salary added for employee "+empsl.getId());
		return new ResponseEntity<>(sm,HttpStatus.CREATED);
	}

	@PutMapping("/salary/{id}")
	ResponseEntity<?> putSalary(HttpServletRequest request, @PathVariable("id") int id,
			@RequestBody EmpSalary empsl) {

		log.info("checking for VP authorization");
		if (!jwtAuth.VPauthorization(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("changing salary for employee");

		this.dataManager.updateSalary(id, empsl);
		SuccessMsg sm= new SuccessMsg(HttpStatus.ACCEPTED, LocalDateTime.now(), "Salary Updated for user "+id);
		return new ResponseEntity<>(sm,HttpStatus.ACCEPTED);

	}

//	-----------------------------------------salary audit-------------------------------------
	@GetMapping("/salary/audit")
	ResponseEntity<List<SalaryAudit>> getallSalaryAudit(HttpServletRequest request) {

		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting audited salary from database");
		return ResponseEntity.ok(this.dataManager.allSalaryAudit());
	}

	@GetMapping("/salary/audit/{id}")
	ResponseEntity<SalaryAudit> getSalaryAuditbyid(HttpServletRequest request, @PathVariable("id") int id) {

		if (!jwtAuth.basicJWTAuth(request))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting audited salary from database");
		return ResponseEntity.ok(this.dataManager.SalaryAuditById(id));
	}

//   ----------------------------------Populate-------------------
	@GetMapping("/populate")
	String populate() {
		this.populatedatabase.populateDB();
		return "ok";
	}

//------------------------------------login cred-------------------
	@PostMapping("/login/update")
	ResponseEntity<?> postLoginCred(HttpServletRequest request, @RequestBody LoginCred loginCred) {

		if (!jwtAuth.SelfDataEditAuth(request, loginCred.getUid()))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("changing password for employee");
		this.dataManager.updateLoginCred(loginCred);
		SuccessMsg sm= new SuccessMsg(HttpStatus.ACCEPTED, LocalDateTime.now(), "User Credentials Updated");
		return new ResponseEntity<>(sm,HttpStatus.ACCEPTED);
	}

//---------------------------------------JWT-------------------------------------
	@GetMapping("/home")
	String Start() {
		return "Welcome to companies employee database";
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> generateJwtToken(@RequestBody AuthRequest authRequest) throws Exception {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassWord()));

		String token = jwtUtil.generateToken(authRequest.getUserName(),
				this.dataManager.getJWTbyid(authRequest.getUserName()));
		return ResponseEntity.ok(token);

	}
}
