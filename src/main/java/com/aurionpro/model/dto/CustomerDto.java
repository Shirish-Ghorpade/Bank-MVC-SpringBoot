package com.aurionpro.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
	
	private int customerId;

	@NotNull(message = "firstname is mandatory")
	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "First name must contain only letters.")
	private String firstName;

	@NotNull(message = "lastname is mandatory")
	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "Last name must contain only letters.")
	private String lastName;

	@NotNull(message = "Email is mandatory.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Not Valid format of Mail Id. Please enter the valid Mail Id")
	private String email;

}
