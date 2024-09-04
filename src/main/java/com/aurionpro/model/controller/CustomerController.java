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

import com.aurionpro.model.dto.CustomerDataDto;
import com.aurionpro.model.dto.CustomerDto;
import com.aurionpro.model.dto.PageResponse;
import com.aurionpro.model.dto.ViewCustomersDto;
import com.aurionpro.model.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bankapp")
public class CustomerController {

	@Autowired
	CustomerService customerService;

////////////////////////////////////////////////////////////////////////////
//CRUD ON CUSTOMERS

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/customer")
	public ResponseEntity<CustomerDto> addNewCustomer(@Valid @RequestBody CustomerDataDto customerDataDto) {
		return ResponseEntity.ok(customerService.addNewCustomer(customerDataDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/customer/{customerId}")
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable int customerId) {
		return ResponseEntity.ok(customerService.getCustomerById(customerId));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/customers")
	ResponseEntity<PageResponse<ViewCustomersDto>> viewCustomers(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "5", required = false) int pageSize,
			@RequestParam(defaultValue = "customerId", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		return ResponseEntity.ok(customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir));
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@PutMapping("/customer/{customerId}")
	public ResponseEntity<CustomerDto> updateCustomerById(@PathVariable int customerId,
			@Valid @RequestBody CustomerDataDto customerDataDto) {
		return ResponseEntity.ok(customerService.updateCustomerById(customerId, customerDataDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/admin/customer/{customerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int customerId) {
		return ResponseEntity.ok(customerService.deleteCustomerById(customerId));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/customers/sort/balance")
	ResponseEntity<PageResponse<ViewCustomersDto>> viewCustomersBySortedBalance(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "5", required = false) int pageSize) {
		return ResponseEntity.ok(customerService.getAllCustomersSortedByBalance(pageNo, pageSize));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/customers/desc-sort/balance")
	ResponseEntity<PageResponse<ViewCustomersDto>> viewCustomersByDescSortedBalance(
			@RequestParam(defaultValue = "0", required = false) int pageNo,
			@RequestParam(defaultValue = "5", required = false) int pageSize) {
		return ResponseEntity.ok(customerService.getAllCustomersSortedByDescBalance(pageNo, pageSize));
	}

}
