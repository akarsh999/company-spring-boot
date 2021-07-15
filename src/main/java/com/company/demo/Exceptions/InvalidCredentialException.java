package com.company.demo.Exceptions;

public class InvalidCredentialException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6664848898046597361L;
	public InvalidCredentialException() {
		super("credentials are incorrect/invalid");
	}
	public InvalidCredentialException(String message) {
		super(message);
	}

}
