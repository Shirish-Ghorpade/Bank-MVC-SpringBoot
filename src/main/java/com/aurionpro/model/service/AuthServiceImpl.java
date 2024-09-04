//package com.aurionpro.model.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.model.dto.LoginDto;
//import com.aurionpro.model.dto.RegistrationDto;
//import com.aurionpro.model.entity.Role;
//import com.aurionpro.model.entity.User;
//import com.aurionpro.model.exceptions.UserApiException;
//import com.aurionpro.model.respository.RoleRepository;
//import com.aurionpro.model.respository.UserRepository;
//import com.aurionpro.model.security.JwtTokenProvider;
//
//import jakarta.transaction.Transactional;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
//@Data
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
//
//	@Transactional
//	@Override
//	public User register(RegistrationDto registrationDto) {
//		if (userRepository.existsByUsername(registrationDto.getUsername())) {
//			throw new UserApiException(HttpStatus.BAD_REQUEST, "User already exists");
//		}
//		User user = new User();
//		user.setUsername(registrationDto.getUsername());
//		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
//		List<Role> roles = new ArrayList<Role>();
//
//		Role userRole = roleRepository.findByRoleName(registrationDto.getRoleName());
//		roles.add(userRole);
//		user.setRoles(roles);
//		return userRepository.save(user);
//	}
//
//	@Override
//	public String login(LoginDto loginDto) {
//
//		try {
////			System.out.println(loginDto.getUsername() + " " + loginDto.getPassword());
//			Authentication authentication = authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			String token = jwtTokenProvider.generateToken(authentication);
//			return token;
//		} catch (BadCredentialsException e) {
//			throw new UserApiException(HttpStatus.UNAUTHORIZED, "Username and password is not valid");
//		}
//	}
//
//}

package com.aurionpro.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.model.dto.LoginDto;
import com.aurionpro.model.dto.RegistrationDto;
import com.aurionpro.model.entity.Role;
import com.aurionpro.model.entity.User;
import com.aurionpro.model.exceptions.UserApiException;
import com.aurionpro.model.respository.RoleRepository;
import com.aurionpro.model.respository.UserRepository;
import com.aurionpro.model.security.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
@Data
@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Transactional
	@Override
	public User register(RegistrationDto registrationDto) {
		logger.info("Starting registration for username: {}", registrationDto.getUsername());

		if (userRepository.existsByUsername(registrationDto.getUsername())) {
			logger.error("Registration failed: User already exists with username: {}", registrationDto.getUsername());
			throw new UserApiException(HttpStatus.BAD_REQUEST, "User already exists");
		}

		User user = new User();
		user.setUsername(registrationDto.getUsername());
		user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		List<Role> roles = new ArrayList<>();

		Role userRole = roleRepository.findByRoleName(registrationDto.getRoleName());
		roles.add(userRole);
		user.setRoles(roles);

		User savedUser = userRepository.save(user);
		logger.info("User registered successfully with ID: {}", savedUser.getUserId());
		return savedUser;
	}

	@Override
	public String login(LoginDto loginDto) {
		logger.info("Attempting login for username: {}", loginDto.getUsername());

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			logger.info("Login successful for username: {}", loginDto.getUsername());
			return token;
		} catch (BadCredentialsException e) {
			logger.error("Login failed for username: {}. Invalid credentials provided.", loginDto.getUsername());
			throw new UserApiException(HttpStatus.UNAUTHORIZED, "Username and password are not valid");
		}
	}

}

