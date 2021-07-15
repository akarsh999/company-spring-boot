package com.company.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EmpSalary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6606957569748127772L;
	@Id
	private int id;
	private int salary;
	@Temporal(TemporalType.DATE)
	private Date lastProcessed;
	public EmpSalary() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmpSalary(int id, int salary, Date lastProcessed) {
		super();
		this.id = id;
		this.salary = salary;
		this.lastProcessed = lastProcessed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Date getLastProcessed() {
		return lastProcessed;
	}
	public void setLastProcessed(Date lastProcessed) {
		this.lastProcessed = lastProcessed;
	}
	
	
	
	
}
