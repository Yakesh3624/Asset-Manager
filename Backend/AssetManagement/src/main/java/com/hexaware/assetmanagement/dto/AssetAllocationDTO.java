package com.hexaware.assetmanagement.dto;

import java.time.LocalDate;

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
public class AssetAllocationDTO {

	private Long allocationId;
	private LocalDate allocationDate;
	private LocalDate returnDate;
	private Long assetNo;
	private Long usersId;
}
