package com.hexaware.assetmanagement.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class representing a request made by a user to borrow or access an
 * asset.
 * 
 * Maps to the 'asset_request' table and includes fields such as request ID,
 * user reference, asset reference, request date, and status.
 * 
 * This entity helps track asset allocation requests and their approval status.
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
@ToString(exclude = { "asset", "user" })
@Table(name = "asset_request")
public class AssetRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long requestId;

	@NotNull(message = "Request timestamp is required")
	@Column(nullable = false, name = "requested_at")
	private Timestamp requestedAt = new Timestamp(System.currentTimeMillis());

	@NotNull(message = "Status is required")
	@Pattern(regexp = "Pending|Approved|Rejected", flags = Flag.CASE_INSENSITIVE, message = "Status must be one of: PENDING, APPROVED, REJECTED")
	@Column(nullable = false)
	private String status = "Pending";

	@OneToOne
	@JoinColumn(name = "asset_no")
	private Asset asset;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users user;
}
