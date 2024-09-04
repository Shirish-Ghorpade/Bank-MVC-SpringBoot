package com.aurionpro.model.service;

import com.aurionpro.model.dto.AccountCreationRequest;
import com.aurionpro.model.dto.BankAccountDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.ViewCustomersDto;
import com.aurionpro.model.entity.Customer;

public interface BankAccountService {

	BankAccountDto addBankAccount(AccountCreationRequest accountCreationRequest);

	BankAccountDto getBankAccountByAccountId(int accountId);
	
	PageResponse<BankAccountDto> getAllBankAccounts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	BankAccountDto getBankAccountByAccountNumber(long accountNumber);
	
	BankAccountDto getBankAccountByCustomerId(int customerId);

	BankAccountDto updateBankAccountByAccountId(int accountId, BankAccountDto bankAccountDto);

	String deleteBankAccountByAccountId(int accountId);
}
