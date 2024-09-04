package com.aurionpro.model.service;

import com.aurionpro.model.dto.LoginDto;
import com.aurionpro.model.dto.RegistrationDto;
import com.aurionpro.model.entity.User;

public interface AuthService {
	User register(RegistrationDto registrationDto);

	String login(LoginDto loginDto);
}
