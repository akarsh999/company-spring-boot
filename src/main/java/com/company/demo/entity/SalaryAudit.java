package com.company.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SalaryAudit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8082948775056910045L;
	@Id
	private int id;
	private int salary;
	@Temporal(TemporalType.DATE)
	private Date date;
	public SalaryAudit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SalaryAudit(int id, int salary, Date date) {
		super();
		this.id = id;
		this.salary = salary;
		this.date = date;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
