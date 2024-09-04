package com.aurionpro.model.exceptions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.aurionpro.model.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleInsufficientAmountException(InsufficientBalanceException exception) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage(exception.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleMisMatchException(MethodArgumentTypeMismatchException exception) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage("Id must be an integer");
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach((error) -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse>handleSQLExceptions(UserApiException exception){
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage("Similar user is exist.!!!");
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse>handleNoResourcesFoundExceptions(NoResourceFoundException exception){
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setErrorMessage("Invalid url, Please try again !!!");
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse>handleAllExceptions(Exception exception){
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage("Something went wrong !!!");
		error.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
