package com.hexaware.assetmanagement.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing the allocation of assets to users in the Asset
 * Management System.
 * 
 * Maps to the 'asset_allocation' table and contains details such as allocation
 * ID, associated user, allocated asset, allocation date, and return date.
 * 
 * This entity tracks which assets are currently assigned to which employees,
 * helping manage asset usage and availability.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "asset", "user" })
@Table(name = "asset_allocation")
public class AssetAllocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long allocationId;

	@NotNull(message = "Allocation date is required")
	@Column(nullable = false, name = "allocation_date")
	private LocalDate allocationDate = LocalDate.now();

	@NotNull(message = "Return date is required")
	@Future(message = "Return date must be a future date")
	@Column(nullable = false, name = "return_date")
	private LocalDate returnDate;

	@OneToOne(optional = false)
	@JoinColumn(name = "asset_no")
	private Asset asset;

	@ManyToOne(optional = false)
	@JoinColumn(name = "users_id")
	private Users user;

}
