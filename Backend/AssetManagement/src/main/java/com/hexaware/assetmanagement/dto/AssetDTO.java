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
public class AssetDTO {

	private Long assetNo;
	private String assetName;
	private String category;
	private String assetModel;
	private LocalDate manufacturingDate;
	private LocalDate expiryDate;
	private Double assetValue;
	private String availability;
	private String image;

}
