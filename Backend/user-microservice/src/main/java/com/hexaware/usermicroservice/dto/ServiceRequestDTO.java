package com.hexaware.usermicroservice.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceRequestDTO {

	private Long requestId;
	private String description;
	private String issueType;
	private String status;
	private Timestamp requestedAt;
	private Long assetNo;
	private Long usersId;

}
