package com.aurionpro.model.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aurionpro.model.dto.BankAccountDto;
import com.aurionpro.model.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.senderAccount.customer.customerId = :customerId " +
		       "OR (t.receiverAccount IS NOT NULL AND t.receiverAccount.customer.customerId = :customerId) " +
		       "OR (t.receiverAccount IS NULL AND t.senderAccount.customer.customerId = :customerId)")
	Page<Transaction> findTransferTransactionByCustomerId(@Param("customerId") int customerId, Pageable pageable);
	
	@Query("SELECT t FROM Transaction t WHERE t.receiverAccount IS NULL AND t.senderAccount.customer.customerId = :customerId")
	Page<Transaction> findCreditDebitTransactionByCustomerId(@Param("customerId") int customerId, Pageable pageable);


}
