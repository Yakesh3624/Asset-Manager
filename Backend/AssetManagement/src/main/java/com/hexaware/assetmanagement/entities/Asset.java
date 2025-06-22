package com.hexaware.assetmanagement.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing an asset in the Asset Management System.
 * 
 * Maps to the 'assets' table in the database and contains fields such as asset
 * number, name, category, availability status, and purchase details.
 * 
 * This class is used for ORM (Object Relational Mapping) with JPA/Hibernate.
 * 
 * An asset can be allocated, serviced, or audited based on business
 * requirements.
 * 
 * @author Yakesh
 * @version 1.0
 * @since 2025-05-28
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "asset_no")
	private Long assetNo;

	@NotBlank(message = "Asset name is required")
	@Size(min = 4, max = 20, message = "Asset name must be between 2 and 20 characters")
	@Column(nullable = false)
	private String assetName;

	@Pattern(regexp = "Laptop|Furniture|Car|Gadget", flags = Flag.CASE_INSENSITIVE, message = "Category must be one of: Laptop, Furniture, Car, Gadgets")
	@Column(nullable = false)
	private String category;

	@Size(min = 1, max = 10, message = "Model must be between 1 and 10 characters")
	@Column(nullable = true, name = "model")
	private String assetModel;

	@NotNull(message = "Manufacturing date is required")
	@PastOrPresent(message = "Manufacturing date cannot be in the future")
	@Column(nullable = false)
	private LocalDate manufacturingDate;

	@Future(message = "Expiry date must be in the future")
	@Column(nullable = true)
	private LocalDate expiryDate;

	@DecimalMin(value = "0.0", inclusive = false, message = "Asset value must be greater than 0")
	@Column(nullable = false)
	private Double assetValue;

	@NotNull(message = "Availability should not be null")
	@Pattern(regexp = "available|unavailable", message = "Availability must be either 'available' or 'unavailable'")
	private String availability;

	@Lob
	private byte[] image;

}
