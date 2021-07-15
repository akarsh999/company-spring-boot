package com.company.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "dept")
public class Departments implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7379213499364856930L;
	@Id
	@GeneratedValue
	private int dept_id;
	private String dept_name;
	private String hod;
	@OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "cp_fk", referencedColumnName = "dept_id")
	@JsonIgnore
	private List<Employee> emp_list;
	
	
	
	
	
	
	
	
	public Departments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Departments(int dept_id, String dept_name, String hod, List<Employee> emp_list) {
		super();
		this.dept_id = dept_id;
		this.dept_name = dept_name;
		this.hod = hod;
		this.emp_list = emp_list;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getHod() {
		return hod;
	}
	public void setHod(String hod) {
		this.hod = hod;
	}
	public List<Employee> getEmp_list() {
		return emp_list;
	}
	public void setEmp_list(List<Employee> emp_list) {
		this.emp_list = emp_list;
	}
	
}
