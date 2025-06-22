package com.hexaware.assetmanagement.services;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.assetmanagement.dto.AssetAllocationDTO;
import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.dto.AssetRequestDTO;
import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.exceptions.DataAlreadyExistException;
import com.hexaware.assetmanagement.exceptions.DataNotFoundException;

// Functionalities of all Services
public interface IAssetManagementService {

	// Asset operations
	AssetDTO addAsset(AssetDTO assetDto) throws DataAlreadyExistException;

	AssetDTO updateAsset(AssetDTO assetDto) throws DataNotFoundException, DataAlreadyExistException;

	AssetDTO updateAssetAvailability(Long assetNo, String availability) throws DataNotFoundException;

	List<AssetDTO> getAllAssets() throws DataNotFoundException;

	AssetDTO getAssetByAssetNo(Long assetNo) throws DataNotFoundException;

	List<AssetDTO> getAssetsByCategory(String category) throws DataNotFoundException;

	List<AssetDTO> getAssetsByAvailability(String availability) throws DataNotFoundException;

	String deleteAssetByAssetNo(Long assetNo) throws DataNotFoundException;

	// Asset Allocation Operations
	AssetAllocationDTO allocateAssetToUser(Long usersId, Long assetNo, LocalDate returnDate)
			throws DataNotFoundException;

	List<AssetAllocationDTO> getAllAllocations() throws DataNotFoundException;

	AssetAllocationDTO getAllocationByAllocationId(Long allocationId) throws DataNotFoundException;

	AssetAllocationDTO getAllocationByAssetNo(Long assetNo) throws DataNotFoundException;

	List<AssetAllocationDTO> getAllocationsByUserId(Long usersId) throws DataNotFoundException;

	String returnAsset(Long assetNo) throws DataNotFoundException;

	// Audit Request Operation
	AuditRequestDTO addAuditRequest(AuditRequestDTO auditRequestDto) throws DataAlreadyExistException;

	List<AuditRequestDTO> getAllAuditRequests() throws DataNotFoundException;

	List<AuditRequestDTO> getAuditRequestsByUsersId(Long usersId) throws DataNotFoundException;

	List<AuditRequestDTO> getAuditRequestsByStatus(String status) throws DataNotFoundException;

	AuditRequestDTO getAuditRequestsByRequestId(Long requestId) throws DataNotFoundException;

	AuditRequestDTO updateAuditRequestStatus(Long requestId, String status) throws DataNotFoundException;

	String deleteAuditRequest(Long requestId);

	// Asset Request Operations
	AssetRequestDTO addAssetRequest(AssetRequestDTO assetRequestDto) throws DataAlreadyExistException;

	List<AssetRequestDTO> getAllAssetRequests() throws DataNotFoundException;

	List<AssetRequestDTO> getAssetRequestsByUsersId(Long usersId) throws DataNotFoundException;

	List<AssetRequestDTO> getAssetRequestsByStatus(String status) throws DataNotFoundException;

	AssetRequestDTO getAssetRequestsByRequestId(Long requestId) throws DataNotFoundException;

	AssetRequestDTO updateAssetRequestStatus(Long requestId, String status) throws DataNotFoundException;

	String deleteAssetRequest(Long requestId);

	// Service Request Operations

	ServiceRequestDTO addServiceRequest(ServiceRequestDTO serviceRequestDto)
			throws DataNotFoundException, DataAlreadyExistException;

	ServiceRequestDTO updateServiceRequestStatus(Long requestId, String status) throws DataNotFoundException;

	List<ServiceRequestDTO> getAllServiceRequests() throws DataNotFoundException;

	List<ServiceRequestDTO> getAllServiceRequestsByUsersId(Long usersId) throws DataNotFoundException;

	ServiceRequestDTO getServiceRequestsByRequestId(Long requestId) throws DataNotFoundException;

	List<ServiceRequestDTO> getAllServiceRequestsByStatus(String status) throws DataNotFoundException;

	List<ServiceRequestDTO> getAllServiceRequestsByIssueType(String issueType) throws DataNotFoundException;

	String deleteServiceRequest(Long requestId);

	void deleteAssetAllocationByUsersId(Long usersId) throws DataNotFoundException;

	void deleteAssetRequestByUsersId(Long usersId);

	void deleteServiceRequestByUsersId(Long usersId);

	void deleteAuditRequestByUsersId(Long usersId);

}
