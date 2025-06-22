package com.hexaware.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersDTO {

	private Long usersId;
	private String firstName;
	private String lastName;
	private String role;
	private String email;
	private String gender;
	private String contactNumber;
	private String address;

	public UsersDTO() {
		super();
	}

}
