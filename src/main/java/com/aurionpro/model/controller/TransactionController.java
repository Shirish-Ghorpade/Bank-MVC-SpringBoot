package com.aurionpro.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.TransactionDto;
import com.aurionpro.model.dto.TransactionResponseDto;
import com.aurionpro.model.dto.ViewTransactionByCustomerIdRequestDto;
import com.aurionpro.model.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bankapp")

public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/customer/credit")
	ResponseEntity<TransactionResponseDto> credit(@Valid @RequestBody TransactionDto transactionDto) {
		return ResponseEntity.ok(transactionService.credit(transactionDto));
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/customer/debit")
	ResponseEntity<TransactionResponseDto> debit(@Valid @RequestBody TransactionDto transactionDto) {
		return ResponseEntity.ok(transactionService.debit(transactionDto));
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/customer/transfer")
	ResponseEntity<String> makeNewTransaction(@Valid @RequestBody TransactionDto transactionDto) {
		return ResponseEntity.ok(transactionService.makeNewTransaction(transactionDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/transactions")
	ResponseEntity<PageResponse<TransactionResponseDto>> viewTransactions(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "3", required = false) int pageSize,
			@RequestParam(defaultValue = "date", required = false) String sortBy,
			@RequestParam(defaultValue = "desc", required = false) String sortDir) {
		return ResponseEntity.ok(transactionService.getAllTransactions(pageNo, pageSize, sortBy, sortDir));
	}

	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/customer/transfer-transactions")
	ResponseEntity<PageResponse<TransactionResponseDto>> viewTransferTransactionsByCustomerId(
			@RequestBody ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto,
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "3", required = false) int pageSize,
			@RequestParam(defaultValue = "date", required = false) String sortBy,
			@RequestParam(defaultValue = "desc", required = false) String sortDir) {
		return ResponseEntity
				.ok(transactionService.getTransferTransactionByCustomerId(viewTransactionByCustomerIdRequestDto, pageNo, pageSize, sortBy, sortDir));
	}
	
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/customer/transactions")
	ResponseEntity<PageResponse<TransactionResponseDto>> viewCreditDebitTransactionsByCustomerId(
			@RequestBody ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto,
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "3", required = false) int pageSize,
			@RequestParam(defaultValue = "date", required = false) String sortBy,
			@RequestParam(defaultValue = "desc", required = false) String sortDir) {
		return ResponseEntity
				.ok(transactionService.getCreditDebitTransactionByCustomerId(viewTransactionByCustomerIdRequestDto, pageNo, pageSize, sortBy, sortDir));
	}
}
