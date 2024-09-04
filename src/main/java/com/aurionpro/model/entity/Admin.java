package com.aurionpro.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminId")
    private int adminId;


	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "First name must contain only letters.")
	@Column(name = "firstName")
	private String firstName;

	@Pattern(regexp = "^[A-Za-z ,.'-]+{2,30}$", message = "Last name must contain only letters.")
	@Column(name = "lastName")
	private String lastName;

	@NotNull(message = "Email is mandatory.")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Not Valid format of Mail Id. Please enter the valid Mail Id")
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User user;
    
}
