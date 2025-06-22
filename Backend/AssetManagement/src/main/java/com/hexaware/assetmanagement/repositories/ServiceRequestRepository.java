package com.hexaware.assetmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entities.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

	boolean existsByUser_UsersIdAndAsset_AssetNo(Long usersId, Long assetNo);

	List<ServiceRequest> findAllByUser_UsersId(Long usersId);

	List<ServiceRequest> findAllByStatus(String status);

	List<ServiceRequest> findAllByIssueType(String issueType);

	void deleteByAsset_AssetNo(Long assetNo);

	void deleteByUser_UsersId(Long usersId);

}
