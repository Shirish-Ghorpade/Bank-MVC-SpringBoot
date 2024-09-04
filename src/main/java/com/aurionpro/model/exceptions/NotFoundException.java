package com.aurionpro.model.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private long id;
	private String message;
	

	public NotFoundException(long id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message + id;
	}
}

//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class NotFoundException extends RuntimeException {
//
//	private static final long serialVersionUID = 1L;
//	private HttpStatus status;
//	private String message;
//}
