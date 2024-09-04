package com.aurionpro.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Roles")
public class Role {

	@Id
	@Column(name = "roleId")
	private int roleId;

	@Column(name = "rolename")
	private String roleName;

}
