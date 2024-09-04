package com.aurionpro.model.dto;


import java.time.LocalDateTime;

import com.aurionpro.model.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponseDto {

	private TransactionType transactionType;

	private double amount;

	private Long senderAccountNumber;

	private Long receiverAccountNumber;

	private LocalDateTime date;

}
