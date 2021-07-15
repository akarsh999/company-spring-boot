package com.company.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JWTSecret implements Serializable{

	
	private static final long serialVersionUID = -8262635414184349403L;
	
	@Id
	private String userId;
	private String jwt;
	public JWTSecret() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JWTSecret(String userId, String jwt) {
		super();
		this.userId = userId;
		this.jwt = jwt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	

}
