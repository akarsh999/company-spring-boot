package com.company.demo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1657200709043304232L;
	@Id
	private int emp_id;
	private String first_name;
	private String last_name;
	
	@Enumerated(EnumType.STRING)
	private Designation designation;
	private String dept_name;
	private String mob_no;
	private String email;
	private boolean hasResigned;
	
	
	
	
	public Employee(int emp_id, String first_name, String last_name, Designation designation, String dept_name,
			String mob_no, String email, boolean hasResigned) {
		super();
		this.emp_id = emp_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.designation = designation;
		this.dept_name = dept_name;
		this.mob_no = mob_no;
		this.email = email;
		this.hasResigned = hasResigned;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Designation getDesignation() {
		return designation;
	}
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}
	public String getMob_no() {
		return mob_no;
	}
	public void setMob_no(String mob_no) {
		this.mob_no = mob_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isHasResigned() {
		return hasResigned;
	}
	public void setHasResigned(boolean hasResigned) {
		this.hasResigned = hasResigned;
	}
	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", first_name=" + first_name + ", last_name=" + last_name
				+ ", designation=" + designation + ", dept_name=" + dept_name + ", mob_no=" + mob_no + ", email="
				+ email + ", hasResigned=" + hasResigned + "]";
	}
	
	
}
