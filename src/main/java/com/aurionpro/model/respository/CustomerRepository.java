package com.aurionpro.model.respository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aurionpro.model.dto.ViewCustomersDto;
import com.aurionpro.model.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT new com.aurionpro.model.dto.ViewCustomersDto(c.customerId, c.firstName, c.lastName, b.accountNumber, b.balance ) "
			+ "FROM Customer c " + " JOIN c.bankAccount b ORDER BY b.balance ASC")
	Page<ViewCustomersDto> getAllCustomersSortedByBalance(Pageable pageable);
	
	@Query("SELECT new com.aurionpro.model.dto.ViewCustomersDto(c.customerId, c.firstName, c.lastName, b.accountNumber, b.balance ) "
			+ "FROM Customer c " + " JOIN c.bankAccount b ORDER BY b.balance DESC")
	Page<ViewCustomersDto> getAllCustomersSortedByDescBalance(Pageable pageable);



}
