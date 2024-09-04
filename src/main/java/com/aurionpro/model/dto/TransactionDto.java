package com.aurionpro.model.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto {
	
	private int customerId;

	@DecimalMin(value = "0.0", message = "Balance must positive")
	@Column(name = "balance")
	private double amount;
	
	private Long receiverAccountNumber;
}
