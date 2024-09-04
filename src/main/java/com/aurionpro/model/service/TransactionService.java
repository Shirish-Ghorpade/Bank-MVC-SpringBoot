package com.aurionpro.model.service;

import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.TransactionDto;
import com.aurionpro.model.dto.TransactionResponseDto;
import com.aurionpro.model.dto.ViewTransactionByCustomerIdRequestDto;


public interface TransactionService {
	
	TransactionResponseDto credit(TransactionDto transactionDto);
	
	TransactionResponseDto debit(TransactionDto transactionDto);
	
	String makeNewTransaction(TransactionDto transactionDto);

	PageResponse<TransactionResponseDto> getAllTransactions(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PageResponse<TransactionResponseDto> getTransferTransactionByCustomerId(ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto, int pageNo, int pageSize, String sortBy, String sortDir);
	
	PageResponse<TransactionResponseDto> getCreditDebitTransactionByCustomerId(ViewTransactionByCustomerIdRequestDto viewTransactionByCustomerIdRequestDto, int pageNo, int pageSize, String sortBy, String sortDir);
	
}
