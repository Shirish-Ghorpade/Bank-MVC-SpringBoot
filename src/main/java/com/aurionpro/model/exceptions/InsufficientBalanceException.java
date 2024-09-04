package com.aurionpro.model.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = -2742334596181835232L;
	private double amount;
	private double balance;

	public InsufficientBalanceException(double amount, double balance) {
		super();
		this.amount = amount;
		this.balance = balance;
	}

	@Override
	public String getMessage() {
		return "Your entered amount is " + amount + ". please enter the smaller amount than your balance " + balance+". Minimum balance is 5000";

	}
}

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//
//public class InsufficientBalanceException extends RuntimeException{
//	private static final long serialVersionUID = 1L;
//	private HttpStatus status;
//	private String message;
//}

