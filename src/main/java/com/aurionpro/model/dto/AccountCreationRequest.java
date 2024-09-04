package com.aurionpro.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCreationRequest {

	@NotNull
	@Min(0)
	private int customerId;
	
	@DecimalMin(value = "5000.0", message = "Balance must be above 5000.")
	private double initialBalance;

}
