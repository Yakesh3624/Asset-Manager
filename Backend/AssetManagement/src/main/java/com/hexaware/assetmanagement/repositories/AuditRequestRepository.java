package com.hexaware.assetmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entities.AuditRequest;

public interface AuditRequestRepository extends JpaRepository<AuditRequest, Long> {

	List<AuditRequest> findAllByUser_UsersId(Long usersId);

	List<AuditRequest> findAllByStatus(String status);

	boolean existsByUser_UsersIdAndAsset_AssetNo(Long usersId, Long assetNo);

	void deleteByAsset_AssetNo(Long assetNo);

	void deleteByUser_UsersId(Long usersId);

}
