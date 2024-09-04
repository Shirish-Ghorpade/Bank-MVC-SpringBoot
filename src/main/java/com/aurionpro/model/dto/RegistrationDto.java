package com.aurionpro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RegistrationDto {
	private String username;
	private String password;
	private String roleName;
}
