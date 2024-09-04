package com.aurionpro.model.service;

import com.aurionpro.model.dto.CustomerDataDto;
import com.aurionpro.model.dto.CustomerDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.ViewCustomersDto;

public interface CustomerService {

	CustomerDto addNewCustomer(CustomerDataDto customerDataDto);

	CustomerDto getCustomerById(int customerId);
	
	PageResponse<ViewCustomersDto> getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PageResponse<ViewCustomersDto> getAllCustomersSortedByBalance(int pageNo, int pageSize);
	
	PageResponse<ViewCustomersDto> getAllCustomersSortedByDescBalance(int pageNo, int pageSize);
	
	CustomerDto updateCustomerById(int customerId, CustomerDataDto customerDataDto);

	String deleteCustomerById(int id);

}
