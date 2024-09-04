//package com.aurionpro.model.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.model.dto.AdminDataDto;
//import com.aurionpro.model.dto.AdminDto;
//import com.aurionpro.model.dto.PageResponse;
//import com.aurionpro.model.dto.RegistrationDto;
//import com.aurionpro.model.entity.Admin;
//import com.aurionpro.model.entity.User;
//import com.aurionpro.model.exceptions.NotFoundException;
//import com.aurionpro.model.respository.AdminRepository;
//import com.aurionpro.model.respository.UserRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//	
//	@Autowired
//	AdminRepository adminRepository;
//
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	AuthService authService;
//
//	@Transactional
//	@Override
//	public AdminDto addNewAdmin(AdminDataDto adminDataDto) {
////		Add admin user name and password in user table for login purpose
//		RegistrationDto registrationDto = new RegistrationDto();
//		registrationDto.setUsername(adminDataDto.getUsername());
//		registrationDto.setPassword(adminDataDto.getPassword());
//		registrationDto.setRoleName("ROLE_ADMIN");
//
//		User user = authService.register(registrationDto);
//
////		Add admin in the database
//		Admin admin = toAdminMapper(adminDataDto);
//		admin.setUser(user);
//		return toAdminDtoMapper(adminRepository.save(admin));
//	}
//
//	@Override
//	public AdminDto getAdminById(int adminId) {
//		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
//		if (!optionalAdmin.isPresent()) {
//			throw new NotFoundException(adminId, "Admin not found with id : ");
//		}
//		return toAdminDtoMapper(optionalAdmin.get());
//	}
//
//	@Override
//	public PageResponse<AdminDto> getAllAdmins(int pageNo, int pageSize, String sortBy, String sortDir) {
//
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
//
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<Admin> dbAdminPage = adminRepository.findAll(pageable);
//
//		PageResponse<AdminDto> viewAdminDtoPageResponse = new PageResponse<>();
//		viewAdminDtoPageResponse.setTotalElements(pageSize);
//		viewAdminDtoPageResponse.setSize(dbAdminPage.getSize());
//		viewAdminDtoPageResponse.setTotalElements(dbAdminPage.getTotalElements());
////			Here it is not working so we use logic below
////			instructorPageResponse.setContent(instructorPage.getContent());
//		viewAdminDtoPageResponse.setLastPage(dbAdminPage.isLast());
//
////			setContent logic
//		List<AdminDto> viewAdminDtos = new ArrayList<>();
//
//		dbAdminPage.getContent().forEach(admin -> {
//			AdminDto viewAdminDto = new AdminDto();
//			viewAdminDto.setAdminId(admin.getAdminId());
//			viewAdminDto.setFirstName(admin.getFirstName());
//			viewAdminDto.setLastName(admin.getLastName());
//
//			viewAdminDtos.add(viewAdminDto);
//
//		});
//		viewAdminDtoPageResponse.setContent(viewAdminDtos);
//
//		return viewAdminDtoPageResponse;
//
//	}
//
//	@Override
//	public AdminDto updateAdminById(int adminId, AdminDataDto adminDataDto) {
//		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
//		if (!optionalAdmin.isPresent()) {
//			throw new NotFoundException(adminId, "Admin not found with id : ");
//		}
//		Admin admin = optionalAdmin.get();
//		admin.setAdminId(adminId);
//		admin.setFirstName(adminDataDto.getFirstName());
//		admin.setLastName(adminDataDto.getLastName());
//		admin.setEmail(adminDataDto.getEmail());
//
////		change in user table
//		User user = admin.getUser(); 
//		user.setUsername(adminDataDto.getUsername());
//		user.setPassword(passwordEncoder.encode(adminDataDto.getPassword()));
//		userRepository.save(user);
//		return toAdminDtoMapper(adminRepository.save(admin));
//	}
//
//	@Override
//	public String deleteAdminById(int adminId) {
//		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
//		if (!optionalAdmin.isPresent()) {
//			throw new NotFoundException(adminId, "Admin not found with id : ");
//		}
//		adminRepository.deleteById(adminId);
//		return "Admin Deleted Sucessfully";
//
//	}
//
//	public AdminDto toAdminDtoMapper(Admin admin) {
//		AdminDto adminDto = new AdminDto();
//		adminDto.setAdminId(admin.getAdminId());
//		adminDto.setFirstName(admin.getFirstName());
//		adminDto.setLastName(admin.getLastName());
//		adminDto.setEmail(admin.getEmail());
//		return adminDto;
//	}
//
//	public Admin toAdminMapper(AdminDataDto adminDataDto) {
//		Admin admin = new Admin();
//		admin.setFirstName(adminDataDto.getFirstName());
//		admin.setLastName(adminDataDto.getLastName());
//		admin.setEmail(adminDataDto.getEmail());
//		return admin;
//	}
//
//}


package com.aurionpro.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.model.EmailSender;
import com.aurionpro.model.dto.AdminDataDto;
import com.aurionpro.model.dto.AdminDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.RegistrationDto;
import com.aurionpro.model.entity.Admin;
import com.aurionpro.model.entity.User;
import com.aurionpro.model.exceptions.NotFoundException;
import com.aurionpro.model.respository.AdminRepository;
import com.aurionpro.model.respository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	AuthService authService;
	
	@Autowired
	EmailSender emailSender;

	@Transactional
	@Override
	public AdminDto addNewAdmin(AdminDataDto adminDataDto) {
		logger.info("Starting addNewAdmin for username: {}", adminDataDto.getUsername());

//		Add admin user name and password in user table for login purpose
		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setUsername(adminDataDto.getUsername());
		registrationDto.setPassword(adminDataDto.getPassword());
		registrationDto.setRoleName("ROLE_ADMIN");

		User user = authService.register(registrationDto);

//		Add admin in the database
		Admin admin = toAdminMapper(adminDataDto);
		admin.setUser(user);

		AdminDto adminDto = toAdminDtoMapper(adminRepository.save(admin));
		logger.info("New admin created successfully with ID: {}", adminDto.getAdminId());
		
		 // Construct the subject and body for the email
        String subject = "Welcome to Aurionpro - Your Financial Journey Begins Here!";
        String body = "Dear " + adminDataDto.getUsername() + ", Welcome to the Aurionpro family! We are thrilled to have you as a valued customer and look forward to helping you achieve your financial goals.\r\n"
        		+ "\r\n"
        		+ "At Aurionpro, we are committed to providing you with exceptional service and a wide range of financial solutions tailored to meet your needs. Whether you’re saving for the future, managing daily expenses, or planning a big investment, we’re here to support you every step of the way.\r\n"
        		+ "\r\n"
        		+ "Here’s what you can expect from us:\r\n"
        		+ "\r\n"
        		+ "Personalized Banking Solutions: Tailored financial products and services to suit your lifestyle.\r\n"
        		+ "24/7 Customer Support: Our dedicated team is here to assist you whenever you need us.\r\n"
        		+ "Secure and Convenient Banking: Manage your account with ease using our online and mobile banking platforms.\r\n"
        		+ "We invite you to explore all the benefits of your new account and discover the many ways [Bank Name] can help you grow your financial future.\r\n"
        		+ "\r\n"
        		+ "If you have any questions or need assistance, please don't hesitate to reach out to our customer support team at [Customer Support Email/Phone].\r\n"
        		+ "\r\n"
        		+ "Thank you for choosing [Bank Name]. We look forward to a long and prosperous relationship!\r\n"
        		+"Your initial password is "+adminDataDto.getPassword()+" you can changed it afterwards\r\n"
        		+ "\r\n"
        		+ "Warm regards,\r\n"
        		+ "\r\n Aurionpro Teams"
        		+ "";

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, adminDataDto.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", adminDataDto.getUsername());
        } else {
            logger.error("Failed to send welcome email to {}: {}", adminDataDto.getUsername(), emailResult);
        }
		
		return adminDto;
	}

	@Override
	public AdminDto getAdminById(int adminId) {
		logger.info("Fetching admin with ID: {}", adminId);

		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
		if (!optionalAdmin.isPresent()) {
			logger.error("Admin not found with ID: {}", adminId);
			throw new NotFoundException(adminId, "Admin not found with id : ");
		}
		logger.info("Admin found with ID: {}", adminId);
		return toAdminDtoMapper(optionalAdmin.get());
	}

	@Override
	public PageResponse<AdminDto> getAllAdmins(int pageNo, int pageSize, String sortBy, String sortDir) {
		logger.info("Fetching all admins with pagination - pageNo: {}, pageSize: {}, sortBy: {}, sortDir: {}", 
				pageNo, pageSize, sortBy, sortDir);

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Admin> dbAdminPage = adminRepository.findAll(pageable);

		PageResponse<AdminDto> viewAdminDtoPageResponse = new PageResponse<>();
		viewAdminDtoPageResponse.setTotalElements(pageSize);
		viewAdminDtoPageResponse.setSize(dbAdminPage.getSize());
		viewAdminDtoPageResponse.setTotalElements(dbAdminPage.getTotalElements());
		viewAdminDtoPageResponse.setLastPage(dbAdminPage.isLast());

//		setContent logic
		List<AdminDto> viewAdminDtos = new ArrayList<>();

		dbAdminPage.getContent().forEach(admin -> {
			AdminDto viewAdminDto = new AdminDto();
			viewAdminDto.setAdminId(admin.getAdminId());
			viewAdminDto.setFirstName(admin.getFirstName());
			viewAdminDto.setLastName(admin.getLastName());

			viewAdminDtos.add(viewAdminDto);

		});
		viewAdminDtoPageResponse.setContent(viewAdminDtos);

		logger.info("Admins fetched successfully with total elements: {}", dbAdminPage.getTotalElements());
		return viewAdminDtoPageResponse;
	}

	@Override
	public AdminDto updateAdminById(int adminId, AdminDataDto adminDataDto) {
		logger.info("Updating admin with ID: {}", adminId);

		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
		if (!optionalAdmin.isPresent()) {
			logger.error("Admin not found with ID: {}", adminId);
			throw new NotFoundException(adminId, "Admin not found with id : ");
		}
		Admin admin = optionalAdmin.get();
		admin.setAdminId(adminId);
		admin.setFirstName(adminDataDto.getFirstName());
		admin.setLastName(adminDataDto.getLastName());
		admin.setEmail(adminDataDto.getEmail());

//		Change in user table
		User user = admin.getUser(); 
		user.setUsername(adminDataDto.getUsername());
		user.setPassword(passwordEncoder.encode(adminDataDto.getPassword()));
		userRepository.save(user);

		AdminDto updatedAdminDto = toAdminDtoMapper(adminRepository.save(admin));
		logger.info("Admin updated successfully with ID: {}", updatedAdminDto.getAdminId());
			
		String subject = "Your are sucessfully updated your profile";
        String body = "Dear " + admin.getFirstName() + "successfully updated in our system";

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, admin.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", admin.getFirstName());
        } else {
            logger.error("Failed to send welcome email to {}: {}", admin.getFirstName(), emailResult);
        }
		
		return updatedAdminDto;
	}

	@Override
	public String deleteAdminById(int adminId) {
		logger.info("Deleting admin with ID: {}", adminId);

		Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
		if (!optionalAdmin.isPresent()) {
			logger.error("Admin not found with ID: {}", adminId);
			throw new NotFoundException(adminId, "Admin not found with id : ");
		}
		adminRepository.deleteById(adminId);
		logger.info("Admin deleted successfully with ID: {}", adminId);
		
		Admin admin = optionalAdmin.get();		
		String subject = "Your are sucessfully existed from our system";
        String body = "Dear " + admin.getFirstName() + "sucessfully existed from our system";

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, admin.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", admin.getFirstName());
        } else {
            logger.error("Failed to send welcome email to {}: {}", admin.getFirstName(), emailResult);
        }
		
		return "Admin Deleted Successfully";
	}

	public AdminDto toAdminDtoMapper(Admin admin) {
		AdminDto adminDto = new AdminDto();
		adminDto.setAdminId(admin.getAdminId());
		adminDto.setFirstName(admin.getFirstName());
		adminDto.setLastName(admin.getLastName());
		adminDto.setEmail(admin.getEmail());
		return adminDto;
	}

	public Admin toAdminMapper(AdminDataDto adminDataDto) {
		Admin admin = new Admin();
		admin.setFirstName(adminDataDto.getFirstName());
		admin.setLastName(adminDataDto.getLastName());
		admin.setEmail(adminDataDto.getEmail());
		return admin;
	}

}


