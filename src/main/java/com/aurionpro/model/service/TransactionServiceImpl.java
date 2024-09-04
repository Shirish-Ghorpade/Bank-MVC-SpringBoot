//package com.aurionpro.model.service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.model.dto.PageResponse;
//import com.aurionpro.model.dto.TransactionDto;
//import com.aurionpro.model.entity.BankAccount;
//import com.aurionpro.model.entity.Transaction;
//import com.aurionpro.model.entity.TransactionType;
//import com.aurionpro.model.exceptions.InsufficientBalanceException;
//import com.aurionpro.model.exceptions.NotFoundException;
//import com.aurionpro.model.respository.BankAccountRepository;
//import com.aurionpro.model.respository.CustomerRepository;
//import com.aurionpro.model.respository.TransactionRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//public class TransactionServiceImpl implements TransactionService {
//
//	final int MIN_BALANCE = 5000;
//
//	@Autowired
//	TransactionRepository transactionRepository;
//
//	@Autowired
//	BankAccountRepository bankAccountRepository;
//
//	@Autowired
//	CustomerRepository customerRepository;
//
//	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
//
////	@Override
////	public TransactionDto makeNewTransaction(TransactionDto transactionDto) {
////
////		TransactionType transactionType = transactionDto.getTransactionType();
////		long senderAccNumber = transactionDto.getSenderAccountNumber();
////		long receiverAccNumber = transactionDto.getReceiverAccountNumber();
////		double amount = transactionDto.getAmount();
////		transactionDto.setDate(LocalDate.now());
////
////		BankAccount senderAccount = bankAccountRepository.findByAccountNumber(senderAccNumber).orElseThrow(
////				() -> new NotFoundException(senderAccNumber, "Sender account is not found with account number : "));
////
////		BankAccount receiverAccount = bankAccountRepository.findByAccountNumber(receiverAccNumber).orElseThrow(
////				() -> new NotFoundException(receiverAccNumber, "Receiver account is not found with account number : "));
////
////		Transaction transaction = toTransactionMapper(transactionDto);
////		transaction.setSenderAccount(senderAccount);
////
////		if (transactionType == TransactionType.TRANSFER) {
////			if (senderAccount == receiverAccount) {
////				throw new NotFoundException(receiverAccNumber,
////						"In same account you cannot transfer the money. Receiver account number cannot same like sender account. ");
////			}
////			if (senderAccount.getBalance() - amount < MIN_BALANCE) {
////				throw new InsufficientBalanceException(amount, senderAccount.getBalance());
////			}
////			senderAccount.setBalance(senderAccount.getBalance() - amount);
////			receiverAccount.setBalance(receiverAccount.getBalance() + amount);
////			transaction.setReceiverAccount(receiverAccount);
////
////		} else if (transactionType == TransactionType.DEBIT) {
////			if (senderAccount.getBalance() - amount < MIN_BALANCE) {
////				throw new InsufficientBalanceException(amount, senderAccount.getBalance());
////			}
////			senderAccount.setBalance(senderAccount.getBalance() - amount);
////			transaction.setReceiverAccount(senderAccount);
////
////		} else if (transactionType == TransactionType.CREDIT) {
////			senderAccount.setBalance(senderAccount.getBalance() + amount);
////			transaction.setReceiverAccount(senderAccount);
////		}
////
//////		BankAccount bankAccount = new BankAccount();
//////		bankAccount.getSentTransactions().add(transaction);
//////		bankAccount.getRecieverTransactions().add(transaction);
////		return toTransactionDtoMapper(transactionRepository.save(transaction));
////	}
//
//	
//	@Transactional
//	private void credit(BankAccount account, double amount, LocalDate date) {
//		Transaction creditTransaction = new Transaction();
//		creditTransaction.setTransactionType(TransactionType.CREDIT);
//		creditTransaction.setAmount(amount);
//		account.setBalance(account.getBalance() + amount);
//		creditTransaction.setSenderAccount(account);
//		creditTransaction.setReceiverAccount(account);
//		creditTransaction.setDate(date);
//		transactionRepository.save(creditTransaction);
//	}
//
//	@Transactional
//	private void debit(BankAccount account, double amount, LocalDate date) {
//		if (account.getBalance() - amount < MIN_BALANCE) {
//			throw new InsufficientBalanceException(amount, account.getBalance());
//		}
//		Transaction debitTransaction = new Transaction();
//		debitTransaction.setTransactionType(TransactionType.DEBIT);
//		account.setBalance(account.getBalance() - amount);
//		debitTransaction.setAmount(amount);
//		debitTransaction.setSenderAccount(account);
//		debitTransaction.setReceiverAccount(account);
//		debitTransaction.setDate(date);
//		transactionRepository.save(debitTransaction);
//	}
//
//	@Transactional
//	@Override
//	public String makeNewTransaction(TransactionDto transactionDto) {
//
//		TransactionType transactionType = transactionDto.getTransactionType();
//		long senderAccNumber = transactionDto.getSenderAccountNumber();
//		long receiverAccNumber = transactionDto.getReceiverAccountNumber();
//		double amount = transactionDto.getAmount();
//		LocalDate date = LocalDate.now();
//
//		BankAccount senderAccount = bankAccountRepository.findByAccountNumber(senderAccNumber).orElseThrow(
//				() -> new NotFoundException(senderAccNumber, "Sender account is not found with account number : "));
//
//		BankAccount receiverAccount = bankAccountRepository.findByAccountNumber(receiverAccNumber).orElseThrow(
//				() -> new NotFoundException(receiverAccNumber, "Receiver account is not found with account number : "));
//
////		CREDIT
//		if (transactionType == TransactionType.CREDIT) {
//			credit(senderAccount, amount, date);
//			return amount+" is successfully credited in "+senderAccNumber;
//		}
//
////		DEBIT
//		if (transactionType == TransactionType.DEBIT) {
//			debit(senderAccount, amount, date);
//			return amount+" is successfully debited from "+senderAccNumber;
//		}
//
//		// TRANSFER
//		if (senderAccount == receiverAccount) {
//			throw new NotFoundException(receiverAccNumber,
//					"In same account you cannot transfer the money. Receiver account number cannot same like sender account. ");
//		}
//		debit(senderAccount, amount, date);
////		if(true)
////			throw new NotFoundException(receiverAccNumber, null);
//		credit(receiverAccount, amount, date);
//		return "Success !! "+ amount +" is transfered sucessfully from "+senderAccNumber +" to "+receiverAccNumber;
//
////		BankAccount bankAccount = new BankAccount();
////		bankAccount.getSentTransactions().add(transaction);
////		bankAccount.getRecieverTransactions().add(transaction);
//	}
//
//	@Override
//	public PageResponse<TransactionDto> getAllTransactions(int pageNo, int pageSize, String sortBy, String sortDir) {
//
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
//
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<Transaction> dbTransactionPage = transactionRepository.findAll(pageable);
//
//		PageResponse<TransactionDto> viewTransactionDtoPageResponse = new PageResponse<>();
//		viewTransactionDtoPageResponse.setTotalElements(pageSize);
//		viewTransactionDtoPageResponse.setSize(dbTransactionPage.getSize());
//		viewTransactionDtoPageResponse.setTotalElements(dbTransactionPage.getTotalElements());
////			Here it is not working so we use logic below
////			instructorPageResponse.setContent(instructorPage.getContent());
//		viewTransactionDtoPageResponse.setLastPage(dbTransactionPage.isLast());
//
////			setContent logic
//		List<TransactionDto> viewTransactionDtos = new ArrayList<>();
//
//		dbTransactionPage.getContent().forEach(transaction -> {
//			viewTransactionDtos.add(toTransactionDtoMapper(transaction));
//
//		});
//		viewTransactionDtoPageResponse.setContent(viewTransactionDtos);
//
//		return viewTransactionDtoPageResponse;
//	}
//
//	@Override
//	public PageResponse<TransactionDto> getAllTransactionByCustomerId(int customerId, int pageNo, int pageSize,
//			String sortBy, String sortDir) {
//
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
//
//		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//		Page<Transaction> dbTransactionPage = transactionRepository.findAllByCustomerId(customerId, pageable);
//
//		PageResponse<TransactionDto> viewTransactionDtoPageResponse = new PageResponse<>();
//		viewTransactionDtoPageResponse.setTotalElements(pageSize);
//		viewTransactionDtoPageResponse.setSize(dbTransactionPage.getSize());
//		viewTransactionDtoPageResponse.setTotalElements(dbTransactionPage.getTotalElements());
////			Here it is not working so we use logic below
////			instructorPageResponse.setContent(instructorPage.getContent());
//		viewTransactionDtoPageResponse.setLastPage(dbTransactionPage.isLast());
//
////			setContent logic
//		List<TransactionDto> viewTransactionDtos = new ArrayList<>();
//
//		dbTransactionPage.getContent().forEach(transaction -> {
//			viewTransactionDtos.add(toTransactionDtoMapper(transaction));
//
//		});
//		viewTransactionDtoPageResponse.setContent(viewTransactionDtos);
//
//		return viewTransactionDtoPageResponse;
//
//	}
//
//	public Transaction toTransactionMapper(TransactionDto transactionDto) {
//		Transaction transaction = new Transaction();
//		transaction.setTransactionType(transactionDto.getTransactionType());
//		transaction.setAmount(transactionDto.getAmount());
//		transaction.setDate(transactionDto.getDate());
//		return transaction;
//	}
//
//	public TransactionDto toTransactionDtoMapper(Transaction transaction) {
//		TransactionDto transactionDto = new TransactionDto();
//		transactionDto.setTransactionType(transaction.getTransactionType());
//		transactionDto.setAmount(transaction.getAmount());
//		transactionDto.setDate(transaction.getDate());
//		transactionDto.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
//		transactionDto.setReceiverAccountNumber(transaction.getReceiverAccount().getAccountNumber());
//		return transactionDto;
//	}
//
//}

package com.aurionpro.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aurionpro.model.EmailSender;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.TransactionDto;
import com.aurionpro.model.dto.TransactionResponseDto;
import com.aurionpro.model.dto.ViewTransactionByCustomerIdRequestDto;
import com.aurionpro.model.entity.BankAccount;
import com.aurionpro.model.entity.Customer;
import com.aurionpro.model.entity.Transaction;
import com.aurionpro.model.entity.TransactionType;
import com.aurionpro.model.exceptions.InsufficientBalanceException;
import com.aurionpro.model.exceptions.NotFoundException;
import com.aurionpro.model.respository.BankAccountRepository;
import com.aurionpro.model.respository.CustomerRepository;
import com.aurionpro.model.respository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

	final int MIN_BALANCE = 5000;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	BankAccountRepository bankAccountRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmailSender emailSender;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Transactional
	@Override
	public TransactionResponseDto credit(TransactionDto transactionDto) {
		int customerId = transactionDto.getCustomerId();

		LocalDateTime date = LocalDateTime.now();

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException(customerId, "Customer is not exist with customer id "));

		BankAccount account = bankAccountRepository.findByCustomer(customer)
				.orElseThrow(() -> new NotFoundException(customerId, "Account is not exist for customer Id: "));

		double amount = transactionDto.getAmount();

		Transaction creditTransaction = new Transaction();
		creditTransaction.setTransactionType(TransactionType.CREDIT);
		account.setBalance(account.getBalance() + amount);
		creditTransaction.setAmount(amount);
		creditTransaction.setSenderAccount(account);
		creditTransaction.setReceiverAccount(null);
		creditTransaction.setDate(date);
		Transaction saveTransaction = transactionRepository.save(creditTransaction);
		logger.info("Amount {} credited successfully from account {}", amount, account.getAccountNumber());
		
		// Construct the subject and body for the email
        String subject = "Success !!!!";
        String body = "Dear " + customer.getFirstName() + "In your account "+amount+ " is credited So, current balance is "+account.getBalance();

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
        } else {
            logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
        }
		return toTransactionResponseDto(saveTransaction);
	}

	@Transactional
	@Override
	public TransactionResponseDto debit(TransactionDto transactionDto) {
		int customerId = transactionDto.getCustomerId();

		LocalDateTime date = LocalDateTime.now();

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException(customerId, "Customer is not exist with customer id "));

		BankAccount account = bankAccountRepository.findByCustomer(customer)
				.orElseThrow(() -> new NotFoundException(customerId, "Account is not exist for customer Id: "));

		double amount = transactionDto.getAmount();

		if (account.getBalance() - amount < MIN_BALANCE) {
			logger.error("Insufficient balance in account {}. Current balance: {}, Required: {}",
					account.getAccountNumber(), account.getBalance(), amount);
			throw new InsufficientBalanceException(amount, account.getBalance());
		}
		Transaction debitTransaction = new Transaction();
		debitTransaction.setTransactionType(TransactionType.DEBIT);
		account.setBalance(account.getBalance() - amount);
		debitTransaction.setAmount(amount);
		debitTransaction.setSenderAccount(account);
		debitTransaction.setReceiverAccount(null);
		debitTransaction.setDate(date);
		Transaction saveTransaction = transactionRepository.save(debitTransaction);
		logger.info("Amount {} debited successfully from account {}", amount, account.getAccountNumber());
		
		// Construct the subject and body for the email
        String subject = "Success !!!!";
        String body = "Dear " + customer.getFirstName() + "In your account "+amount+ " is debited So, current balance is "+account.getBalance();

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
        } else {
            logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
        }

		return toTransactionResponseDto(saveTransaction);
	}

	@Transactional
	@Override
	public String makeNewTransaction(TransactionDto transactionDto) {

		int customerId = transactionDto.getCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException(customerId, "Customer is not exist with customer id "));

		BankAccount senderAccount = bankAccountRepository.findByCustomer(customer)
				.orElseThrow(() -> new NotFoundException(customerId, "Account is not exist for customer Id: "));

		long senderAccNumber = senderAccount.getAccountNumber();

		Long receiverAccNumber = transactionDto.getReceiverAccountNumber();

		BankAccount receiverAccount = bankAccountRepository.findByAccountNumber(receiverAccNumber)
				.orElseThrow(() -> new NotFoundException(receiverAccNumber,
						"Receiver account not found with account number: " + receiverAccNumber));

		double amount = transactionDto.getAmount();
		LocalDateTime date = LocalDateTime.now();

		if (senderAccNumber == receiverAccNumber) {
			logger.error("Cannot transfer to the same account. Sender and receiver account number: {}",
					receiverAccNumber);
			throw new NotFoundException(receiverAccNumber,
					"Cannot transfer money within the same account. Sender and receiver account number cannot be the same.");
		}
		Transaction creditTransaction = new Transaction();
		creditTransaction.setTransactionType(TransactionType.CREDIT);
		receiverAccount.setBalance(receiverAccount.getBalance() + amount);
		creditTransaction.setAmount(amount);
		creditTransaction.setSenderAccount(senderAccount);
		creditTransaction.setReceiverAccount(receiverAccount);
		creditTransaction.setDate(date);
		transactionRepository.save(creditTransaction);

		Transaction debitTransaction = new Transaction();
		debitTransaction.setTransactionType(TransactionType.DEBIT);
		senderAccount.setBalance(senderAccount.getBalance() - amount);
		debitTransaction.setAmount(amount);
		debitTransaction.setSenderAccount(senderAccount);
		debitTransaction.setReceiverAccount(receiverAccount);
		debitTransaction.setDate(date);
		transactionRepository.save(debitTransaction);
		logger.info("Transaction successful: {} transferred from account {} to account {}", amount, senderAccNumber,
				receiverAccNumber);
		
		// Construct the subject and body for the email
        String subject = "Success !!!!";
        String body = "Dear " + customer.getFirstName() + amount+ " is successfully transferred to "+ receiverAccNumber +" So, current balance is "+senderAccount.getBalance();

        // Call the sendEmail method
        String emailResult = emailSender.sendEmail(subject, body, customer.getEmail());

        // Log the result of the email sending process
        if ("Success".equals(emailResult)) {
            logger.info("Welcome email sent successfully to: {}", customer.getFirstName());
        } else {
            logger.error("Failed to send welcome email to {}: {}", customer.getFirstName(), emailResult);
        }

		return "Success !! " + amount + " is transfered sucessfully from " + senderAccNumber + " to "
				+ receiverAccNumber;

	}

	@Override
	public PageResponse<TransactionResponseDto> getAllTransactions(int pageNo, int pageSize, String sortBy, String sortDir) {
		logger.info("Fetching all transactions with pagination. Page: {}, Size: {}, SortBy: {}, SortDir: {}", pageNo,
				pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Transaction> dbTransactionPage = transactionRepository.findAll(pageable);

		PageResponse<TransactionResponseDto> viewTransactionResponseDtoPageResponse = new PageResponse<>();
		viewTransactionResponseDtoPageResponse.setTotalElements(pageSize);
		viewTransactionResponseDtoPageResponse.setSize(dbTransactionPage.getSize());
		viewTransactionResponseDtoPageResponse.setTotalElements(dbTransactionPage.getTotalElements());
		viewTransactionResponseDtoPageResponse.setLastPage(dbTransactionPage.isLast());

		List<TransactionResponseDto> viewTransactionDtos = new ArrayList<>();
		dbTransactionPage.getContent().forEach(transaction -> {
			viewTransactionDtos.add(toTransactionResponseDto(transaction));
		});

		viewTransactionResponseDtoPageResponse.setContent(viewTransactionDtos);
		logger.info("Fetched {} transactions", viewTransactionDtos.size());

		return viewTransactionResponseDtoPageResponse;
	}

	@Override
	public PageResponse<TransactionResponseDto> getTransferTransactionByCustomerId(ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		
		int customerId = viewTransactionByCustomerIdRequestDto.getCustomerId();
		logger.info(
				"Fetching transactions for customer ID {} with pagination. Page: {}, Size: {}, SortBy: {}, SortDir: {}",
				customerId, pageNo, pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Transaction> dbTransactionPage = transactionRepository.findTransferTransactionByCustomerId(customerId, pageable);

		PageResponse<TransactionResponseDto> viewTransactionResponseDtoPageResponse = new PageResponse<>();
		viewTransactionResponseDtoPageResponse.setTotalElements(pageSize);
		viewTransactionResponseDtoPageResponse.setSize(dbTransactionPage.getSize());
		viewTransactionResponseDtoPageResponse.setTotalElements(dbTransactionPage.getTotalElements());
		viewTransactionResponseDtoPageResponse.setLastPage(dbTransactionPage.isLast());

		List<TransactionResponseDto> viewTransactionResponseDtos = new ArrayList<>();
		dbTransactionPage.getContent().forEach(transaction -> {
//			System.out.println(transaction.getReceiverAccount().getAccountNumber());
			viewTransactionResponseDtos.add(toTransactionResponseDto(transaction));
		});

		viewTransactionResponseDtoPageResponse.setContent(viewTransactionResponseDtos);
		logger.info("Fetched {} transactions for customer ID {}", viewTransactionResponseDtos.size(), customerId);

		return viewTransactionResponseDtoPageResponse;
	}
	
	@Override
	public PageResponse<TransactionResponseDto> getCreditDebitTransactionByCustomerId(ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		
		int customerId = viewTransactionByCustomerIdRequestDto.getCustomerId();
		logger.info(
				"Fetching transactions for customer ID {} with pagination. Page: {}, Size: {}, SortBy: {}, SortDir: {}",
				customerId, pageNo, pageSize, sortBy, sortDir);

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Transaction> dbTransactionPage = transactionRepository.findCreditDebitTransactionByCustomerId(customerId, pageable);

		PageResponse<TransactionResponseDto> viewTransactionResponseDtoPageResponse = new PageResponse<>();
		viewTransactionResponseDtoPageResponse.setTotalElements(pageSize);
		viewTransactionResponseDtoPageResponse.setSize(dbTransactionPage.getSize());
		viewTransactionResponseDtoPageResponse.setTotalElements(dbTransactionPage.getTotalElements());
		viewTransactionResponseDtoPageResponse.setLastPage(dbTransactionPage.isLast());

		List<TransactionResponseDto> viewTransactionResponseDtos = new ArrayList<>();
		dbTransactionPage.getContent().forEach(transaction -> {
//			System.out.println(transaction.getReceiverAccount().getAccountNumber());
			viewTransactionResponseDtos.add(toTransactionResponseDto(transaction));
		});

		viewTransactionResponseDtoPageResponse.setContent(viewTransactionResponseDtos);
		logger.info("Fetched {} transactions for customer ID {}", viewTransactionResponseDtos.size(), customerId);

		return viewTransactionResponseDtoPageResponse;
	}

	public Transaction toTransactionMapper(TransactionDto transactionDto) {
		logger.info("Mapping TransactionDto to Transaction entity");
		Transaction transaction = new Transaction();
		transaction.setAmount(transactionDto.getAmount());
		return transaction;
	}

	public TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
		logger.info("Mapping Transaction entity to TransactionResponseDto");
		TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
		transactionResponseDto.setTransactionType(transaction.getTransactionType());
		transactionResponseDto.setAmount(transaction.getAmount());
		transactionResponseDto.setDate(transaction.getDate());
		transactionResponseDto.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber());
		transactionResponseDto.setReceiverAccountNumber(transaction.getReceiverAccount()==null ? null : transaction.getReceiverAccount().getAccountNumber());
		return transactionResponseDto;
	}
}
