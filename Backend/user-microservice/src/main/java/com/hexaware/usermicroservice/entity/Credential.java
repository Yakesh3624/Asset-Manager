package com.hexaware.usermicroservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing login credentials of a user in the Asset Management System.
 * 
 * Maps to the 'credentials' table and contains fields such as username, password,
 * role, and references to the associated user.
 * 
 * This entity is used during authentication to validate users and assign appropriate access roles.
 * 
 * Passwords should be stored in a hashed and encrypted format for security.
 * 
 * Author: Yakesh
 * @version 1.0
 * @since 2025-05-28
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Table(name = "credential")
public class Credential {

	@Id
	@Column(name = "users_id")
	private Long usersId;

	@NotBlank(message = "Username is required")
	@Size(min = 4, max = 16, message = "Username must be between 4 and 20 characters")
	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;

	@NotBlank(message = "Password is required")
	@Size(min = 4, message = "Password must be at least 4 characters")
	@Column(nullable = false)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@MapsId
	@JoinColumn(name = "users_id")
	private Users user;
}
