//package com.aurionpro.model.service;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.model.dto.AccountCreationRequest;
//import com.aurionpro.model.dto.BankAccountDto;
//import com.aurionpro.model.dto.PageResponse;
//import com.aurionpro.model.entity.BankAccount;
//import com.aurionpro.model.entity.Customer;
//import com.aurionpro.model.exceptions.NotFoundException;
//import com.aurionpro.model.respository.BankAccountRepository;
//import com.aurionpro.model.respository.CustomerRepository;
//import com.aurionpro.model.respository.TransactionRepository;
//
//import jakarta.validation.Valid;
//
//@Service
//public class BankAccountServiceImpl implements BankAccountService {
//
//	@Autowired
//	CustomerRepository customerRepository;
//
//	@Autowired
//	BankAccountRepository bankAccountRepository;
//
//	HashSet<Long> accountNumbers = new HashSet<>();
//
//	@Override
//	public BankAccountDto addBankAccount(@Valid AccountCreationRequest accountCreationRequest) {
//
//		int customerId = accountCreationRequest.getCustomerId();
//		double initialBalance = accountCreationRequest.getInitialBalance();
//
//		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
//		if (!optionalCustomer.isPresent()) {
//			throw new NotFoundException(customerId, "Customer not found which having id : ");
//		}
//
//		BankAccount bankAccount = new BankAccount();
//		bankAccount.setAccountNumber(generateAccountNumber());
//		bankAccount.setBalance(initialBalance);
//		bankAccount.setCustomer(optionalCustomer.get());
//		return toBankAccountDtoMapper(bankAccountRepository.save(bankAccount));
//	}
//
//	@Override
//	public BankAccountDto getBankAccountByAccountId(int accountId) {
//		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
//		if (!optionalBankAccount.isPresent()) {
//			throw new NotFoundException(accountId, "Account not found which having id : ");
//		}
//		return toBankAccountDtoMapper(optionalBankAccount.get());
//	}
//
//	@Override
//	public PageResponse<BankAccountDto> getAllBankAccounts(int pageNo, int pageSize, String sortBy, String sortDir) {
//		
//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		}
//		else {
//			sort = Sort.by(sortBy).descending();
//		}
//		
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<BankAccount> dbBankAccountPage = bankAccountRepository.findAll(pageable);
//
//		PageResponse<BankAccountDto> bankAccountDtoPageResponse = new PageResponse<>();
//		bankAccountDtoPageResponse.setTotalElements(pageSize);
//		bankAccountDtoPageResponse.setSize(dbBankAccountPage.getSize());
//		bankAccountDtoPageResponse.setTotalElements(dbBankAccountPage.getTotalElements());
////			Here it is not working so we use logic below
////			instructorPageResponse.setContent(instructorPage.getContent());
//		bankAccountDtoPageResponse.setLastPage(dbBankAccountPage.isLast());
//
////			setContent logic
//		List<BankAccountDto> bankAccountDtos = new ArrayList<>();
//
//		dbBankAccountPage.getContent().forEach(bankAccount -> {
//			BankAccountDto bankAccountDto = new BankAccountDto();
//
//			bankAccountDto.setAccountId(bankAccount.getAccountId());
//			bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
//			bankAccountDto.setBalance(bankAccount.getBalance());
//
//			bankAccountDtos.add(bankAccountDto);
//
//		});
//		bankAccountDtoPageResponse.setContent(bankAccountDtos);
//
//		return bankAccountDtoPageResponse;
//
//	}
//
//	@Override
//	public BankAccountDto getBankAccountByAccountNumber(long accountNumber) {
//		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
//		if (!optionalBankAccount.isPresent()) {
//			throw new NotFoundException(accountNumber, "Account not found which having account Number : ");
//		}
//		return toBankAccountDtoMapper(optionalBankAccount.get());
//	}
//
//	@Override
//	public BankAccountDto getBankAccountByCustomerId(int customerId) {
//		Customer customer = customerRepository.findById(customerId)
//				.orElseThrow(() -> new NotFoundException(customerId, "Customer is not found with id :"));
//
//		BankAccount bankAccount = bankAccountRepository.findByCustomer(customer).orElseThrow(
//				() -> new NotFoundException(customerId, "Bank Account is not exist for the customer id : "));
//
//		return toBankAccountDtoMapper(bankAccount);
//
//	}
//
//	@Override
//	public String deleteBankAccountByAccountId(int accountId) {
//		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
//		if (!optionalBankAccount.isPresent()) {
//			throw new NotFoundException(accountId, "Account not found which having id : ");
//		}
//		bankAccountRepository.deleteById(accountId);
//		return "Account is sucessfully deleted";
//	}
//
//	@Override
//	public BankAccountDto updateBankAccountByAccountId(int accountId, BankAccountDto bankAccountDto) {
//		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
//		if (!optionalBankAccount.isPresent()) {
//			throw new NotFoundException(accountId, "Account not found which having id : ");
//		}
//		BankAccount bankAccount = optionalBankAccount.get();
//		bankAccount.setBalance(bankAccountDto.getBalance());
//		return toBankAccountDtoMapper(bankAccountRepository.save(bankAccount));
//	}
//
//	@Autowired
//	TransactionRepository transactionRepository;
//
//	private Long generateAccountNumber() {
//		long accountNumber = 0L;
//		while (true) {
//			long randomNumber = generateAccountNumberHelper();
//			if (!accountNumbers.contains(randomNumber)) {
//				accountNumber = randomNumber;
//				break;
//			}
//		}
//		return accountNumber;
//	}
//
//	private static long generateAccountNumberHelper() {
//		Random random = new Random();
//		return 10000000000L + (long) (random.nextDouble() * 9000000000L);
//	}
//
//	public BankAccountDto toBankAccountDtoMapper(BankAccount bankAccount) {
//		BankAccountDto bankAccountDto = new BankAccountDto();
//		bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
//		bankAccountDto.setBalance(bankAccount.getBalance());
//		bankAccountDto.setAccountId(bankAccount.getAccountId());
//		return bankAccountDto;
//	}
//
//	public BankAccount toBankAccountMapper(BankAccountDto bankAccountDto) {
//		BankAccount bankAccount = new BankAccount();
//		bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
//		bankAccount.setBalance(bankAccountDto.getBalance());
//		bankAccount.setAccountId(bankAccount.getAccountId());
//		return bankAccount;
//	}
//
//}

package com.aurionpro.model.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aurionpro.model.EmailSender;
import com.aurionpro.model.dto.AccountCreationRequest;
import com.aurionpro.model.dto.BankAccountDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.entity.BankAccount;
import com.aurionpro.model.entity.Customer;
import com.aurionpro.model.exceptions.NotFoundException;
import com.aurionpro.model.respository.BankAccountRepository;
import com.aurionpro.model.respository.CustomerRepository;
import com.aurionpro.model.respository.TransactionRepository;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	EmailSender emailSender;

	HashSet<Long> accountNumbers = new HashSet<>();

	@Override
	public BankAccountDto addBankAccount(@Valid AccountCreationRequest accountCreationRequest) {
		logger.info("Starting bank account creation for customer ID: {}", accountCreationRequest.getCustomerId());

		int customerId = accountCreationRequest.getCustomerId();
		double initialBalance = accountCreationRequest.getInitialBalance();

		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			logger.error("Customer not found with ID: {}", customerId);
			throw new NotFoundException(customerId, "Customer not found which having id : ");
		}

		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccountNumber(generateAccountNumber());
		bankAccount.setBalance(initialBalance);
		Customer customer = optionalCustomer.get();
		bankAccount.setCustomer(customer);
		

		BankAccount savedAccount = bankAccountRepository.save(bankAccount);
		logger.info("Bank account created successfully with account number: {}", savedAccount.getAccountNumber());

		// Construct the subject and body for the email
		String subject = "Welcome to Aurionpro - Your Financial Journey Begins Here!";
		String body = "Dear " + customer.getFirstName()
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
				+ "\r\n" + "Warm regards,\r\n" + "\r\n Aurionpro Teams" + "";

		// Call the sendEmail method
		String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

		// Log the result of the email sending process
		if ("Success".equals(emailResult)) {
			logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
		} else {
			logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
		}

		return toBankAccountDtoMapper(savedAccount);
	}

	@Override
	public BankAccountDto getBankAccountByAccountId(int accountId) {
		logger.info("Fetching bank account by ID: {}", accountId);

		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
		if (!optionalBankAccount.isPresent()) {
			logger.error("Bank account not found with ID: {}", accountId);
			throw new NotFoundException(accountId, "Account not found which having id : ");
		}
		return toBankAccountDtoMapper(optionalBankAccount.get());
	}

	@Override
	public PageResponse<BankAccountDto> getAllBankAccounts(int pageNo, int pageSize, String sortBy, String sortDir) {
		logger.info("Fetching all bank accounts with page number: {}, page size: {}, sort by: {}, sort direction: {}",
				pageNo, pageSize, sortBy, sortDir);

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<BankAccount> dbBankAccountPage = bankAccountRepository.findAll(pageable);

		PageResponse<BankAccountDto> bankAccountDtoPageResponse = new PageResponse<>();
		bankAccountDtoPageResponse.setTotalElements(pageSize);
		bankAccountDtoPageResponse.setSize(dbBankAccountPage.getSize());
		bankAccountDtoPageResponse.setTotalElements(dbBankAccountPage.getTotalElements());
		bankAccountDtoPageResponse.setLastPage(dbBankAccountPage.isLast());

		List<BankAccountDto> bankAccountDtos = new ArrayList<>();
		dbBankAccountPage.getContent().forEach(bankAccount -> {
			BankAccountDto bankAccountDto = new BankAccountDto();
			bankAccountDto.setAccountId(bankAccount.getAccountId());
			bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
			bankAccountDto.setBalance(bankAccount.getBalance());
			bankAccountDtos.add(bankAccountDto);
		});
		bankAccountDtoPageResponse.setContent(bankAccountDtos);

		logger.info("Fetched {} bank accounts", bankAccountDtos.size());

		return bankAccountDtoPageResponse;
	}

	@Override
	public BankAccountDto getBankAccountByAccountNumber(long accountNumber) {
		logger.info("Fetching bank account by account number: {}", accountNumber);

		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByAccountNumber(accountNumber);
		if (!optionalBankAccount.isPresent()) {
			logger.error("Bank account not found with account number: {}", accountNumber);
			throw new NotFoundException(accountNumber, "Account not found which having account Number : ");
		}
		return toBankAccountDtoMapper(optionalBankAccount.get());
	}

	@Override
	public BankAccountDto getBankAccountByCustomerId(int customerId) {
		logger.info("Fetching bank account by customer ID: {}", customerId);

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException(customerId, "Customer is not found with id :"));

		BankAccount bankAccount = bankAccountRepository.findByCustomer(customer)
				.orElseThrow(() -> new NotFoundException(customerId, "Bank Account does not exist for customer ID : "));

		return toBankAccountDtoMapper(bankAccount);
	}

	@Override
	public String deleteBankAccountByAccountId(int accountId) {
		logger.info("Deleting bank account with ID: {}", accountId);

		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
		if (!optionalBankAccount.isPresent()) {
			logger.error("Bank account not found with ID: {}", accountId);
			throw new NotFoundException(accountId, "Account not found which having id : ");
		}
		bankAccountRepository.deleteById(accountId);
		logger.info("Bank account with ID: {} deleted successfully", accountId);
		
		Customer customer = optionalBankAccount.get().getCustomer();
	
		String firstNameOfCustomer = optionalBankAccount.get().getCustomer().getFirstName();
		
		// Construct the subject and body for the email
				String subject = "You account is sucessfully deleted";
				String body = "Dear " +firstNameOfCustomer + " you are successfully deleted";
						

				// Call the sendEmail method
				String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

				// Log the result of the email sending process
				if ("Success".equals(emailResult)) {
					logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
				} else {
					logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
				}
		return "Account is successfully deleted";
	}

	@Override
	public BankAccountDto updateBankAccountByAccountId(int accountId, BankAccountDto bankAccountDto) {
		logger.info("Updating bank account with ID: {}", accountId);

		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(accountId);
		if (!optionalBankAccount.isPresent()) {
			logger.error("Bank account not found with ID: {}", accountId);
			throw new NotFoundException(accountId, "Account not found which having id : ");
		}
		BankAccount bankAccount = optionalBankAccount.get();
		bankAccount.setBalance(bankAccountDto.getBalance());
		BankAccount updatedAccount = bankAccountRepository.save(bankAccount);

		logger.info("Bank account with ID: {} updated successfully", accountId);
		
		Customer customer = optionalBankAccount.get().getCustomer();
		
		// Construct the subject and body for the email
		String subject = "Account details is successfully updated";
		String body = "Dear " + customer.getFirstName()+" account details is sucessfully updated";

		// Call the sendEmail method
		String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

		// Log the result of the email sending process
		if ("Success".equals(emailResult)) {
			logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
		} else {
			logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
		}
		return toBankAccountDtoMapper(updatedAccount);
	}

	@Autowired
	TransactionRepository transactionRepository;

	private Long generateAccountNumber() {
		long accountNumber = 0L;
		while (true) {
			long randomNumber = generateAccountNumberHelper();
			if (!accountNumbers.contains(randomNumber)) {
				accountNumber = randomNumber;
				break;
			}
		}
		logger.info("Generated new account number: {}", accountNumber);
		return accountNumber;
	}

	private static long generateAccountNumberHelper() {
		Random random = new Random();
		return 10000000000L + (long) (random.nextDouble() * 9000000000L);
	}

	public BankAccountDto toBankAccountDtoMapper(BankAccount bankAccount) {
		BankAccountDto bankAccountDto = new BankAccountDto();
		bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
		bankAccountDto.setBalance(bankAccount.getBalance());
		bankAccountDto.setAccountId(bankAccount.getAccountId());
		return bankAccountDto;
	}

	public BankAccount toBankAccountMapper(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
		bankAccount.setBalance(bankAccountDto.getBalance());
		bankAccount.setAccountId(bankAccountDto.getAccountId());
		return bankAccount;
	}

}
