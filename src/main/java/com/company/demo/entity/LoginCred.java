package com.company.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoginCred implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6555193156651068663L;
	@Id
	private int uid;
	private String userName;
	private String passWord;
	public LoginCred() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginCred(int uid, String userName, String passWord) {
		super();
		this.uid = uid;
		this.userName = userName;
		this.passWord = passWord;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
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
