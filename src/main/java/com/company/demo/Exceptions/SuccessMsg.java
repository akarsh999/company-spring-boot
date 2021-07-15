package com.company.demo.Exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SuccessMsg {
	private HttpStatus status;
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime timestamp;
	   private String message;
	public SuccessMsg() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SuccessMsg(HttpStatus status, LocalDateTime timestamp, String message) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	   
}
