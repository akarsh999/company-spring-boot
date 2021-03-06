package com.company.demo.Exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<?> handleNoSuchElementException(){
		return new ResponseEntity("error", HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
	}
}
