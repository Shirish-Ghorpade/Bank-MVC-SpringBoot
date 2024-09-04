package com.aurionpro.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transactionId")
	private int transactionId;

	@Enumerated(EnumType.STRING)
	@Column(name = "transactionType")
	private TransactionType transactionType;

	@DecimalMin(value = "0.0", inclusive = true, message = "Amount must be zero or a positive number.")
	@Column(name = "amount")
	private double amount;

	@Column(name = "date")
	@PastOrPresent
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "SenderAccountId")
	private BankAccount senderAccount;

	@ManyToOne
	@JoinColumn(name = "ReceiverAccountId")
	private BankAccount receiverAccount;
}
