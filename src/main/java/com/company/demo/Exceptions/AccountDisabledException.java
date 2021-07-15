package com.company.demo.Exceptions;

public class AccountDisabledException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107612153329647803L;
	public AccountDisabledException() {
		super("user account is deactivated");
	}
}
