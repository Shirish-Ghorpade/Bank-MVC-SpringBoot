package com.aurionpro.model.entity;

import java.util.List;

import com.aurionpro.model.dto.TransactionDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "bankAccounts")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accountId")
	private int accountId;

	@NotNull(message = "Account number cannot be null.")
	@Min(value = 1000000000L, message = "Account number must be at least 10 digits long.")
	@Column(name = "accountNumber")
	private Long accountNumber;

	@DecimalMin(value = "5000.0", message = "Balance must be above 5000.")
	@Column(name = "balance")
	private double balance;

	@OneToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "senderAccount", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Transaction> sentTransactions;
	
	@OneToMany(mappedBy = "receiverAccount", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Transaction> receiverTransactions;
	

}
