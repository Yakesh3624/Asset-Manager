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
 * Entity class representing a request to audit an asset.
 * 
 * Maps to the 'audit_request' table and includes audit request ID, associated
 * user, asset details, date of request, and current status.
 * 
 * This entity is used to log and manage audit tracking for assets within the
 * system.
 * 
 * Audits help ensure asset compliance and proper usage.
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
@Table(name = "audit_request")
public class AuditRequest {

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
