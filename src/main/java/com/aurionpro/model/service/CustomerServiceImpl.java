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
//import com.aurionpro.model.dto.CustomerDataDto;
//import com.aurionpro.model.dto.CustomerDto;
//import com.aurionpro.model.dto.PageResponse;
//import com.aurionpro.model.dto.RegistrationDto;
//import com.aurionpro.model.dto.ViewCustomersDto;
//import com.aurionpro.model.entity.Customer;
//import com.aurionpro.model.entity.User;
//import com.aurionpro.model.exceptions.NotFoundException;
//import com.aurionpro.model.respository.CustomerRepository;
//import com.aurionpro.model.respository.UserRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//	@Autowired
//	CustomerRepository customerRepository;
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
//	public CustomerDto addNewCustomer(CustomerDataDto customerDataDto) {
////		Add customer user name and password in user table for login purpose
//		RegistrationDto registrationDto = new RegistrationDto();
//		registrationDto.setUsername(customerDataDto.getUsername());
//		registrationDto.setPassword(customerDataDto.getPassword());
//		registrationDto.setRoleName("ROLE_CUSTOMER");
//
//		User user = authService.register(registrationDto);
//
////		Add customer in the database
//		Customer customer = toCustomerMapper(customerDataDto);
//		customer.setUser(user);
//		return toCustomerDtoMapper(customerRepository.save(customer));
//	}
//
//	@Override
//	public CustomerDto getCustomerById(int customerId) {
//		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//		if (!optionalCustomer.isPresent()) {
//			throw new NotFoundException(customerId, "Customer not found with id : ");
//		}
//		return toCustomerDtoMapper(optionalCustomer.get());
//	}
//
//	@Override
//	public PageResponse<ViewCustomersDto> getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {
//
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
//
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<Customer> dbCustomerPage = customerRepository.findAll(pageable);
//
//		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
//		viewCustomerDtoPageResponse.setTotalElements(pageSize);
//		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
//		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
////			Here it is not working so we use logic below
////			instructorPageResponse.setContent(instructorPage.getContent());
//		viewCustomerDtoPageResponse.setLastPage(dbCustomerPage.isLast());
//
////			setContent logic
//		List<ViewCustomersDto> viewCustomerDtos = new ArrayList<>();
//
//		dbCustomerPage.getContent().forEach(customer -> {
//			ViewCustomersDto viewCustomerDto = new ViewCustomersDto();
//			viewCustomerDto.setCustomerId(customer.getCustomerId());
//			viewCustomerDto.setFirstName(customer.getFirstName());
//			viewCustomerDto.setLastName(customer.getLastName());
//			viewCustomerDto.setAccountNumber(customer.getBankAccount() !=null ? customer.getBankAccount().getAccountNumber() : null);
//			viewCustomerDto.setBalance(customer.getBankAccount() != null ? customer.getBankAccount().getBalance() : null);
//
//			viewCustomerDtos.add(viewCustomerDto);
//
//		});
//		viewCustomerDtoPageResponse.setContent(viewCustomerDtos);
//
//		return viewCustomerDtoPageResponse;
//
//	}
//
//	@Override
//	public PageResponse<ViewCustomersDto> getAllCustomersSortedByBalance(int pageNo, int pageSize) {
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
//		Page<ViewCustomersDto> dbCustomerPage = customerRepository.getAllCustomersSortedByBalance(pageable);
//
//		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
//		viewCustomerDtoPageResponse.setTotalElements(pageSize);
//		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
//		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
//		viewCustomerDtoPageResponse.setContent(dbCustomerPage.getContent());
//		return viewCustomerDtoPageResponse;
//	}
//
//	@Override
//	public PageResponse<ViewCustomersDto> getAllCustomersSortedByDescBalance(int pageNo, int pageSize) {
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
//		Page<ViewCustomersDto> dbCustomerPage = customerRepository.getAllCustomersSortedByDescBalance(pageable);
//
//		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
//		viewCustomerDtoPageResponse.setTotalElements(pageSize);
//		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
//		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
//		viewCustomerDtoPageResponse.setContent(dbCustomerPage.getContent());
//		return viewCustomerDtoPageResponse;
//	}
//
//	@Override
//	public CustomerDto updateCustomerById(int customerId, CustomerDataDto customerDataDto) {
//		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//		if (!optionalCustomer.isPresent()) {
//			throw new NotFoundException(customerId, "Customer not found with id : ");
//		}
//		Customer customer = optionalCustomer.get();
//		customer.setCustomerId(customerId);
//		customer.setFirstName(customerDataDto.getFirstName());
//		customer.setLastName(customerDataDto.getLastName());
//		customer.setEmail(customerDataDto.getEmail());
//
////		change in user table
//		User user = customer.getUser(); 
//		user.setUsername(customerDataDto.getUsername());
//		user.setPassword(passwordEncoder.encode(customerDataDto.getPassword()));
//		userRepository.save(user);
//		return toCustomerDtoMapper(customerRepository.save(customer));
//	}
//
//	@Override
//	public String deleteCustomerById(int customerId) {
//		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//		if (!optionalCustomer.isPresent()) {
//			throw new NotFoundException(customerId, "Customer not found with id : ");
//		}
//		customerRepository.deleteById(customerId);
//		return "Customer Deleted Sucessfully";
//
//	}
//
//	public CustomerDto toCustomerDtoMapper(Customer customer) {
//		CustomerDto customerDto = new CustomerDto();
//		customerDto.setCustomerId(customer.getCustomerId());
//		customerDto.setFirstName(customer.getFirstName());
//		customerDto.setLastName(customer.getLastName());
//		customerDto.setEmail(customer.getEmail());
//		return customerDto;
//	}
//
//	public Customer toCustomerMapper(CustomerDataDto customerDataDto) {
//		Customer customer = new Customer();
//		customer.setFirstName(customerDataDto.getFirstName());
//		customer.setLastName(customerDataDto.getLastName());
//		customer.setEmail(customerDataDto.getEmail());
//		return customer;
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
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aurionpro.model.EmailSender;
import com.aurionpro.model.dto.CustomerDataDto;
import com.aurionpro.model.dto.CustomerDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.RegistrationDto;
import com.aurionpro.model.dto.ViewCustomersDto;
import com.aurionpro.model.entity.Customer;
import com.aurionpro.model.entity.User;
import com.aurionpro.model.exceptions.NotFoundException;
import com.aurionpro.model.exceptions.UserApiException;
import com.aurionpro.model.respository.CustomerRepository;
import com.aurionpro.model.respository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

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
	public CustomerDto addNewCustomer(CustomerDataDto customerDataDto) {
		logger.info("Adding a new customer: {}", customerDataDto.getUsername());

		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setUsername(customerDataDto.getUsername());
		registrationDto.setPassword(customerDataDto.getPassword());
		registrationDto.setRoleName("ROLE_CUSTOMER");

		User user = authService.register(registrationDto);
		logger.info("User registered successfully for customer: {}", customerDataDto.getUsername());

		Customer customer = toCustomerMapper(customerDataDto);
		customer.setUser(user);
		CustomerDto savedCustomer = toCustomerDtoMapper(customerRepository.save(customer));

		logger.info("Customer saved successfully with ID: {}", savedCustomer.getCustomerId());

		// Construct the subject and body for the email
		String subject = "Welcome to Aurionpro - Your Financial Journey Begins Here!";
		String body = "Dear " + customerDataDto.getUsername()
				+ ", Welcome to the Aurionpro family! We are thrilled to have you as a valued customer and look forward to helping you achieve your financial goals.\r\n"
				+ "\r\n"
				+ "At Aurionpro, we are committed to providing you with exceptional service and a wide range of financial solutions tailored to meet your needs. Whether you’re saving for the future, managing daily expenses, or planning a big investment, we’re here to support you every step of the way.\r\n"
				+ "\r\n" + "Here’s what you can expect from us:\r\n" + "\r\n"
				+ "Personalized Banking Solutions: Tailored financial products and services to suit your lifestyle.\r\n"
				+ "24/7 Customer Support: Our dedicated team is here to assist you whenever you need us.\r\n"
				+ "Secure and Convenient Banking: Manage your account with ease using our online and mobile banking platforms.\r\n"
				+ "We invite you to explore all the benefits of your new account and discover the many ways [Bank Name] can help you grow your financial future.\r\n"
				+ "\r\n"
				+ "If you have any questions or need assistance, please don't hesitate to reach out to our customer support team at [Customer Support Email/Phone].\r\n"
				+ "\r\n"
				+ "Thank you for choosing [Bank Name]. We look forward to a long and prosperous relationship!\r\n"
				+"Your initial password is "+customerDataDto.getPassword()+" you can changed it afterwards\r\n"
				+ "\r\n" + "Warm regards,\r\n" + "\r\n Aurionpro Teams" + "";

		// Call the sendEmail method
		String emailResult = emailSender.sendEmail(subject, body, customerDataDto.getEmail());

		// Log the result of the email sending process
		if ("Success".equals(emailResult)) {
			logger.info("Welcome email sent successfully to: {}", customerDataDto.getUsername());
		} else {
			logger.error("Failed to send welcome email to {}: {}", customerDataDto.getUsername(), emailResult);
		}

		return savedCustomer;
	}

	@Override
	public CustomerDto getCustomerById(int customerId) {

		logger.info("Fetching customer by ID: {}", customerId);
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			logger.error("Customer not found with ID: {}", customerId);
			throw new NotFoundException(customerId, "Customer not found with id: ");
		}
		logger.info("Customer found with ID: {}", customerId);
		return toCustomerDtoMapper(optionalCustomer.get());
	}

	@Override
	public PageResponse<ViewCustomersDto> getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {
		logger.info("Fetching all customers with pagination - Page: {}, Size: {}, SortBy: {}, SortDir: {}", pageNo,
				pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Customer> dbCustomerPage = customerRepository.findAll(pageable);

		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
		viewCustomerDtoPageResponse.setTotalElements(pageSize);
		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
		viewCustomerDtoPageResponse.setLastPage(dbCustomerPage.isLast());

		List<ViewCustomersDto> viewCustomerDtos = new ArrayList<>();
		dbCustomerPage.getContent().forEach(customer -> {
			ViewCustomersDto viewCustomerDto = new ViewCustomersDto();
			viewCustomerDto.setCustomerId(customer.getCustomerId());
			viewCustomerDto.setFirstName(customer.getFirstName());
			viewCustomerDto.setLastName(customer.getLastName());
			viewCustomerDto.setAccountNumber(
					customer.getBankAccount() != null ? customer.getBankAccount().getAccountNumber() : null);
			viewCustomerDto
					.setBalance(customer.getBankAccount() != null ? customer.getBankAccount().getBalance() : null);
			viewCustomerDtos.add(viewCustomerDto);
		});

		viewCustomerDtoPageResponse.setContent(viewCustomerDtos);
		logger.info("Fetched {} customers", viewCustomerDtos.size());

		return viewCustomerDtoPageResponse;
	}

	@Override
	public PageResponse<ViewCustomersDto> getAllCustomersSortedByBalance(int pageNo, int pageSize) {
		logger.info("Fetching all customers sorted by balance with pagination - Page: {}, Size: {}", pageNo, pageSize);
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<ViewCustomersDto> dbCustomerPage = customerRepository.getAllCustomersSortedByBalance(pageable);

		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
		viewCustomerDtoPageResponse.setTotalElements(pageSize);
		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
		viewCustomerDtoPageResponse.setContent(dbCustomerPage.getContent());

		logger.info("Fetched customers sorted by balance with total elements: {}", dbCustomerPage.getTotalElements());

		return viewCustomerDtoPageResponse;
	}

	@Override
	public PageResponse<ViewCustomersDto> getAllCustomersSortedByDescBalance(int pageNo, int pageSize) {
		logger.info("Fetching all customers sorted by descending balance with pagination - Page: {}, Size: {}", pageNo,
				pageSize);
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<ViewCustomersDto> dbCustomerPage = customerRepository.getAllCustomersSortedByDescBalance(pageable);

		PageResponse<ViewCustomersDto> viewCustomerDtoPageResponse = new PageResponse<>();
		viewCustomerDtoPageResponse.setTotalElements(pageSize);
		viewCustomerDtoPageResponse.setSize(dbCustomerPage.getSize());
		viewCustomerDtoPageResponse.setTotalElements(dbCustomerPage.getTotalElements());
		viewCustomerDtoPageResponse.setContent(dbCustomerPage.getContent());

		logger.info("Fetched customers sorted by descending balance with total elements: {}",
				dbCustomerPage.getTotalElements());

		return viewCustomerDtoPageResponse;
	}

	@Override
	public CustomerDto updateCustomerById(int customerId, CustomerDataDto customerDataDto) {

		logger.info("Updating customer with ID: {}", customerId);
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			logger.error("Customer not found with ID: {}", customerId);
			throw new NotFoundException(customerId, "Customer not found with id: ");
		}
		if (userRepository.existsByUsername(customerDataDto.getUsername())) {
			logger.error("Updated failed: User already exists with username: {}", customerDataDto.getUsername());
			throw new UserApiException(HttpStatus.BAD_REQUEST, "User already exists");
		}
		Customer customer = optionalCustomer.get();
		customer.setFirstName(customerDataDto.getFirstName());
		customer.setLastName(customerDataDto.getLastName());
		customer.setEmail(customerDataDto.getEmail());

		User user = customer.getUser();
		user.setUsername(customerDataDto.getUsername());
		user.setPassword(passwordEncoder.encode(customerDataDto.getPassword()));
		userRepository.save(user);

		CustomerDto updatedCustomer = toCustomerDtoMapper(customerRepository.save(customer));
		logger.info("Customer updated successfully with ID: {}", updatedCustomer.getCustomerId());

		// Construct the subject and body for the email
		String subject = "Customer details is sucessfully updated";
		String body = "Dear " + customer.getFirstName() + " your customer details section is sucessfully updated";
		// Call the sendEmail method
		String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

		// Log the result of the email sending process
		if ("Success".equals(emailResult)) {
			logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
		} else {
			logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
		}

		return updatedCustomer;
	}

	@Override
	public String deleteCustomerById(int customerId) {
		logger.info("Deleting customer with ID: {}", customerId);
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			logger.error("Customer not found with ID: {}", customerId);
			throw new NotFoundException(customerId, "Customer not found with id: ");
		}
		customerRepository.deleteById(customerId);
		logger.info("Customer deleted successfully with ID: {}", customerId);

		return "Customer Deleted Successfully";
	}

	private CustomerDto toCustomerDtoMapper(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(customer.getCustomerId());
		customerDto.setFirstName(customer.getFirstName());
		customerDto.setLastName(customer.getLastName());
		customerDto.setEmail(customer.getEmail());
		return customerDto;
	}

	private Customer toCustomerMapper(CustomerDataDto customerDataDto) {
		Customer customer = new Customer();
		customer.setFirstName(customerDataDto.getFirstName());
		customer.setLastName(customerDataDto.getLastName());
		customer.setEmail(customerDataDto.getEmail());
		return customer;
	}
}
