package com.hexaware.assetmanagement.dto;

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
public class AssetRequestDTO {

	private Long requestId;
	private Timestamp requestedAt;
	private String status;
	private Long assetNo;
	private Long usersId;

}
