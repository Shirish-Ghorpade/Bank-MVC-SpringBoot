package com.aurionpro.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminDataDto {
	@NotNull(message = "username is mandatory")
	@Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{2,20}$", message = "Username length must be greater than 3 and must be start with the letter and only contains letter digit and underscore")
	private String username;

	@NotNull(message = "firstname is mandatory")
	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "First name must contain only letters.")
	private String firstName;

	@NotNull(message = "lastname is mandatory")
	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "Last name must contain only letters.")
	private String lastName;

	@NotNull(message = "Email is mandatory.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Not Valid format of Mail Id. Please enter the valid Mail Id")
	private String email;

	@NotNull(message = "Password is mandatory.")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Minimum eight and maximum 20 characters, at least one uppercase letter, one lowercase letter, one number and one special character ")
	@Column(name = "password", nullable = false)
	private String password;

}
