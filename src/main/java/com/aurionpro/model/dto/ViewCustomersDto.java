package com.aurionpro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ViewCustomersDto {
	private int customerId;
	private String firstName;
	private String lastName;
	private Long AccountNumber;
	private Double balance;
}
