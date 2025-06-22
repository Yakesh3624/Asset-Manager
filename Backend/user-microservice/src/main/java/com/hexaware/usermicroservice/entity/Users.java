package com.hexaware.usermicroservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing a user in the Asset Management System.
 * 
 * Maps to the 'users' table in the database and stores user profile information
 * such as name, email, phone number, designation, and department.
 * 
 * A user can perform operations like raising asset requests, audits, or service requests.
 * The associated credentials (username and password) are managed in a separate entity.
 * 
 * Used for role-based access control in the system (e.g., EMPLOYEE, ADMIN).
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
@ToString
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "users_id")
	private Long usersId;

	@NotBlank(message = "First name is required")
	@Size(min = 3, max = 26, message = "First name must be between 3 and 26 characters")
	@Column(nullable = false)
	private String firstName;

	@Size(min = 1, max = 10, message = "Last name must be between 3 and 26 characters")
	@Column(nullable = true)
	private String lastName;

	@Pattern(regexp = "Admin|Employee", flags = Flag.CASE_INSENSITIVE, message = "Role must be Admin or Employee")
	@Column(nullable = false)
	private String role;

	@Email(message = "Email should be valid")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "Male|Female|Other", flags = Flag.CASE_INSENSITIVE, message = "Gender must be MALE, FEMALE, or OTHER")
	@Column(nullable = false)
	private String gender;

	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "^\\d{10}$", message = "Contact number must be 10 digits")
	@Column(nullable = false, unique = true)
	private String contactNumber;

	@NotBlank(message = "Address is required")
	@Column(nullable = false)
	private String address;

}
