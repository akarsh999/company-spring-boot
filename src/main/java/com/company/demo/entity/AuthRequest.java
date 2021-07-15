package com.company.demo.entity;

import java.io.Serializable;

public class AuthRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6843391429511261673L;
	private String userName;
	private String passWord;
	public AuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthRequest(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
