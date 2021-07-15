package com.company.demo.Exceptions;

public class NoDataFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8481205575376825792L;
	
	public NoDataFoundException() {
		super("No Data Fouund");
	}
}
