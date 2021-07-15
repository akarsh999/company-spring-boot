package com.company.demo.Exceptions;

public class UnauthorizedUserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7354269600446098821L;
	public UnauthorizedUserException(String message) {
		super(message);
	}
}
