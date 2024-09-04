package com.aurionpro.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aurionpro.model.dto.AccountCreationRequest;
import com.aurionpro.model.dto.BankAccountDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.service.BankAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bankapp")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;

//////////////////////////////////////////////////////////////////
//CRUD ON BANK ACCOUNT

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("admin/account")
	ResponseEntity<BankAccountDto> addBankAccount(@Valid @RequestBody AccountCreationRequest accountCreationRequest) {
		return ResponseEntity.ok(bankAccountService.addBankAccount(accountCreationRequest));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/account/{accountId}")
	ResponseEntity<BankAccountDto> getBankAccountById(@PathVariable int accountId) {
		return ResponseEntity.ok(bankAccountService.getBankAccountByAccountId(accountId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/accounts")
	ResponseEntity<PageResponse<BankAccountDto>> getAllBankAccounts(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "5", required = false) int pageSize,
			@RequestParam(defaultValue = "accountId", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		return ResponseEntity.ok(bankAccountService.getAllBankAccounts(pageNo, pageSize, sortBy, sortDir));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/account/{accountId}")
	ResponseEntity<BankAccountDto> updateBankAccountById(@PathVariable int accountId,
			@Valid @RequestBody BankAccountDto bankAccountDto) {
		return ResponseEntity.ok(bankAccountService.updateBankAccountByAccountId(accountId, bankAccountDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/account/{accountId}")
	ResponseEntity<String> deleteBankAccountById(@PathVariable int accountId) {
		return ResponseEntity.ok(bankAccountService.deleteBankAccountByAccountId(accountId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/account/number/{accountNumber}")
	ResponseEntity<BankAccountDto> getBankAccountByAccountNumber(@PathVariable long accountNumber) {
		return ResponseEntity.ok(bankAccountService.getBankAccountByAccountNumber(accountNumber));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/account/customer/{customerId}")
	ResponseEntity<BankAccountDto> getBankAccountByCustomerId(@PathVariable int customerId) {
		return ResponseEntity.ok(bankAccountService.getBankAccountByCustomerId(customerId));
	}

}
