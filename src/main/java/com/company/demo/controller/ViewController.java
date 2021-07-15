package com.company.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company.demo.Exceptions.UnauthorizedUserException;
import com.company.demo.Utility.JwtUtil;
import com.company.demo.Utility.WebJwtAuth;
import com.company.demo.entity.Departments;
import com.company.demo.entity.Employee;
import com.company.demo.service.DataManager;

@Controller
@RequestMapping("/jsp")
public class ViewController {

	@Autowired
	DataManager dataManager;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	WebJwtAuth jwtAuth;

	private static final Logger log = LoggerFactory.getLogger(ViewController.class);

	@RequestMapping("/home")
	public ModelAndView homeView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public String authenticate(@RequestParam("UserName") String UserName, @RequestParam("PassWord") String PassWord,
			HttpServletResponse response) {
		String token;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(UserName, PassWord));

			token = jwtUtil.generateToken(UserName, this.dataManager.getJWTbyid(UserName));

		} catch (Exception e) {
			// TODO: handle exception
			return "LoginFailed";
		}
		Cookie cookie1 = new Cookie("Authorization", "Bearer" + token);
		Cookie cookie2 = new Cookie("UserName", UserName);
		cookie1.setMaxAge(10 * 60 * 60);
		cookie2.setMaxAge(10 * 60 * 60);
		cookie1.setHttpOnly(true);
		cookie2.setHttpOnly(true);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		return "redirect:empHome";
	}

	@RequestMapping("/empHome")
	public String empHome(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName"))) {
			throw new UnauthorizedUserException("Current user is unauthorized");
		}
			
		return "index";
	}

	@RequestMapping("/dept")
	public String deptform(HttpServletRequest request) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		return "dept";
	}

	@RequestMapping("/deptview")
	public ModelAndView DeptDetails(HttpServletRequest request, @RequestParam("deptName") String deptName) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting department by name from database");
		ModelAndView modelAndView = new ModelAndView();
		List<Departments> dept1;
		if (deptName.equals("ALL"))
			dept1 = this.dataManager.findAlldept();
		else
			dept1 = this.dataManager.findDeptbyName(deptName);
		modelAndView.addObject("DeptList", dept1);
		modelAndView.setViewName("deptView");
		return modelAndView;
	}

	@RequestMapping("/emp")
	public String empform(HttpServletRequest request) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		return "emp";
	}

	@RequestMapping("/empview")
	public ModelAndView EmpDetails(HttpServletRequest request, @RequestParam("empID") int empID) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting emp by id from database");
		ModelAndView modelAndView = new ModelAndView();
		List<Employee> list = new ArrayList<>();
		Employee emp = this.dataManager.getEmpByid(empID);
		System.out.println("Akarsh");
		list.add(emp);
		modelAndView.addObject("EmpList", list);
		modelAndView.setViewName("empView");
		return modelAndView;
	}

	@RequestMapping("/empviewAll")
	public ModelAndView AllEmpDetails(HttpServletRequest request) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("getting emp by id from database");
		ModelAndView modelAndView = new ModelAndView();
		List<Employee> list = this.dataManager.findAllemp();
		modelAndView.addObject("EmpList", list);
		modelAndView.setViewName("empView");
		return modelAndView;
	}

	@RequestMapping("/logout")
	public ModelAndView Logout(HttpServletRequest request, HttpServletResponse resp) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie c : cookies) {
				c.setMaxAge(0);
				resp.addCookie(c);
			}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("/addemp")
	public String addEmp(HttpServletRequest request) {
		Map<String, String> req = setRequest(request);
		log.info("checking for basicJWT authorization");
		if (!jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		return "addEmpform";
	}

	public Map<String, String> setRequest(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			map.put(c.getName(), c.getValue());
		}
		return map;
	}

	@RequestMapping(path = "/saveEmp", method = RequestMethod.POST)
	public String SaveEmp(HttpServletRequest request, @ModelAttribute("Employee") Employee emp, BindingResult result) {
		log.info("checking for EMPSave authorization");
		Map<String, String> req = setRequest(request);
		if (!this.jwtAuth.EmpSaveAuth(req.get("Authorization"), req.get("UserName"), emp))
			throw new UnauthorizedUserException("Current user is unauthorized");
		if (result.hasErrors())
			return "error";
		log.info("saving employee data");
		this.dataManager.saveEmp(emp);
		return "savesuccess";
	}

	@RequestMapping("/empEdit/{id}")
	public ModelAndView EditEmpform(HttpServletRequest request, @PathVariable("id") int id) {
		Map<String, String> req = setRequest(request);
		if (!this.jwtAuth.basicJWTAuth(req.get("Authorization"), req.get("UserName")))
			throw new UnauthorizedUserException("Current user is unauthorized");
		log.info("generating employee edit form");
		Employee emp = this.dataManager.getEmpByid(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("employee", emp);
		modelAndView.setViewName("EmpEditForm");
		return modelAndView;
	}

	@RequestMapping(path = "/empEdit/updateEmp/{id}", method = RequestMethod.POST)
	public String UpdateEmp(HttpServletRequest request, @PathVariable("id") int id,
			@ModelAttribute("Employee") Employee emp, BindingResult result) {
		log.info("checking for EMPEdit authorization");
		Map<String, String> req = setRequest(request);
		if (!this.jwtAuth.EmpEditAuth(req.get("Authorization"), req.get("UserName"), emp, id))
			throw new UnauthorizedUserException("Current user is unauthorized");
		if (result.hasErrors())
			return "error";
		log.info("saving employee data");
		this.dataManager.updateEmp(emp, id);
		return "savesuccess";
	}
}
