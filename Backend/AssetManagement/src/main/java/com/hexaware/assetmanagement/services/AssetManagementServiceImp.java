package com.hexaware.assetmanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dto.AssetAllocationDTO;
import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.dto.AssetRequestDTO;
import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.entities.Asset;
import com.hexaware.assetmanagement.entities.AssetAllocation;
import com.hexaware.assetmanagement.entities.AssetRequest;
import com.hexaware.assetmanagement.entities.AuditRequest;
import com.hexaware.assetmanagement.entities.ServiceRequest;
import com.hexaware.assetmanagement.entities.Users;
import com.hexaware.assetmanagement.exceptions.DataAlreadyExistException;
import com.hexaware.assetmanagement.exceptions.DataNotFoundException;
import com.hexaware.assetmanagement.repositories.AssetAllocationRepository;
import com.hexaware.assetmanagement.repositories.AssetRepository;
import com.hexaware.assetmanagement.repositories.AssetRequestRepository;
import com.hexaware.assetmanagement.repositories.AuditRequestRepository;
import com.hexaware.assetmanagement.repositories.ServiceRequestRepository;
import com.hexaware.assetmanagement.repositories.UsersRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class responsible for handling all business logic related to asset
 * management.
 * 
 * This includes operations such as adding, updating, retrieving, and listing
 * assets, as well as managing asset availability and category-based queries.
 * 
 * Acts as an intermediary between the controller layer and the repository
 * layer, ensuring that asset-related transactions follow business rules and
 * validations.
 * 
 * @author Yakesh
 * @version 1.0
 * @since 2025-05-28
 */
@Service
@Slf4j
public class AssetManagementServiceImp implements IAssetManagementService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AssetRepository assetRepo;

	@Autowired
	AssetAllocationRepository assetAllocationRepo;

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	AuditRequestRepository auditRequestRepo;

	@Autowired
	AssetRequestRepository assetRequestRepo;

	@Autowired
	ServiceRequestRepository serviceRequestRepo;

	@Override
	public AssetDTO addAsset(AssetDTO assetDto) throws DataAlreadyExistException {
		log.info("Attempting to add Asset with asset number: {}", assetDto.getAssetNo());
		Asset oldAsset = assetRepo.findByAssetName(assetDto.getAssetName());
		if (oldAsset != null && oldAsset.getAssetName().equals(assetDto.getAssetName())
				&& oldAsset.getAssetModel().equals(assetDto.getAssetModel())) {
			log.warn("Asset already exist");
			throw new DataAlreadyExistException("Asset already exist");
		}
		Asset asset = modelMapper.map(assetDto, Asset.class);

		asset = assetRepo.save(asset);

		log.info("Asset added successfully with asset number: {}", asset.getAssetNo());
		return modelMapper.map(asset, AssetDTO.class);
	}

	@Override
	public AssetDTO updateAsset(AssetDTO asset) throws DataNotFoundException, DataAlreadyExistException {

		log.info("Attempting to update asset with asset number: {}", asset.getAssetNo());

		Asset updatedAsset = modelMapper.map(asset, Asset.class);
		updatedAsset = assetRepo.save(updatedAsset);
		log.info("Asset updated successfully with asset number: {}", asset.getAssetNo());
		return modelMapper.map(updatedAsset, AssetDTO.class);
	}

	@Override
	public AssetDTO updateAssetAvailability(Long assetNo, String availability) throws DataNotFoundException {

		log.info("Attempting to update asset availability with asset number: {}", assetNo);

		Asset asset = assetRepo.findById(assetNo).orElseThrow(() -> {
			log.error("Asset not found with asset number: {}", assetNo);
			return new DataNotFoundException("Requested asset not found");
		});

		asset.setAvailability(availability);

		log.info("Asset availability updated successfully with asset number: {}", assetNo);

		assetRepo.save(asset);

		return modelMapper.map(asset, AssetDTO.class);
	}

	@Override
	public List<AssetDTO> getAllAssets() throws DataNotFoundException {

		log.info("Fetch all assets");

		List<Asset> assetList = assetRepo.findAll();
		if (assetList.isEmpty()) {
			log.warn("No asset found in the database");
			throw new DataNotFoundException("No data to display");
		}

		log.info("Fetched all assets");

		return assetList.stream().map(asset -> modelMapper.map(asset, AssetDTO.class)).collect(Collectors.toList());

	}

	@Override
	public AssetDTO getAssetByAssetNo(Long assetNo) throws DataNotFoundException {

		log.info("Fetching asset with asset number: {}", assetNo);

		Asset asset = assetRepo.findById(assetNo).orElseThrow(() -> {
			log.warn("No asset found with asset number: {}", assetNo);
			return new DataNotFoundException("No asset found with asset number " + assetNo);
		});

		log.info("Fetched asset with asset number: {}", assetNo);

		return modelMapper.map(asset, AssetDTO.class);
	}

	@Override
	public List<AssetDTO> getAssetsByCategory(String category) throws DataNotFoundException {

		log.info("Fetching asset by category: {}", category);

		List<Asset> assetList = assetRepo.findAllByCategory(category);
		if (assetList.isEmpty()) {
			log.warn("No asset on {} category found in the database", category);
			throw new DataNotFoundException("No data to display");
		}

		log.info("Fetched asset with category: {}", category);

		return assetList.stream().map(asset -> modelMapper.map(asset, AssetDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<AssetDTO> getAssetsByAvailability(String availability) throws DataNotFoundException {

		log.info("Fetching asset by availability: {}", availability);

		List<Asset> assetList = assetRepo.findAllByAvailability(availability);
		if (assetList.isEmpty()) {
			log.warn("No asset available to assign");
			throw new DataNotFoundException("No data to display");
		}

		log.info("Fetched asset with availability: {}", availability);

		return assetList.stream().map(asset -> modelMapper.map(asset, AssetDTO.class)).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public String deleteAssetByAssetNo(Long assetNo) throws DataNotFoundException {

		log.info("Attempting to delete asset with asset number: {}", assetNo);

		assetRepo.findById(assetNo).orElseThrow(() -> {
			log.warn("Asset with asset number {} does not exists", assetNo);
			return new DataNotFoundException("Requested asset not found");
		});
		assetAllocationRepo.deleteByAsset_AssetNo(assetNo);
		assetRequestRepo.deleteByAsset_AssetNo(assetNo);
		auditRequestRepo.deleteByAsset_AssetNo(assetNo);
		serviceRequestRepo.deleteByAsset_AssetNo(assetNo);

		assetRepo.deleteById(assetNo);

		log.info("Asset with ID {} deleted successfully", assetNo);

		return "Asset with asset number " + assetNo + " is deleted";
	}

	// Asset Allocation Operations

	@Override
	@Transactional
	public AssetAllocationDTO allocateAssetToUser(Long usersId, Long assetNo, LocalDate returnDate)
			throws DataNotFoundException {

		log.info("Allocating asset {} to user {} with return date {}", assetNo, usersId, returnDate);

		Users user = usersRepo.findById(usersId).orElseThrow(() -> {
			log.warn("User with ID: {} not found", usersId);
			return new DataNotFoundException("No user found with ID: " + usersId);
		});

		Asset asset = assetRepo.findById(assetNo).orElseThrow(() -> {
			log.warn("Asset with assetNo: {} not found", assetNo);
			return new DataNotFoundException("No asset found with assetNo: " + assetNo);
		});

		AssetAllocation allocation = new AssetAllocation();
		allocation.setReturnDate(returnDate);
		allocation.setUser(user);
		allocation.setAsset(asset);

		if (asset.getAvailability().equals("unavailable")) {
			throw new DataNotFoundException("Asset already assigned to another user");
		}

		allocation = assetAllocationRepo.save(allocation);
		asset.setAvailability("unavailable");
		assetRepo.save(asset);

		log.info("Allocated asset {} to user {} with return date {}", assetNo, usersId, returnDate);

		return modelMapper.map(allocation, AssetAllocationDTO.class);
	}

	@Override
	public List<AssetAllocationDTO> getAllAllocations() throws DataNotFoundException {
		log.info("Fetching all asset allocations");

		List<AssetAllocation> assetAllocationList = assetAllocationRepo.findAll();

		if (assetAllocationList.isEmpty()) {
			log.warn("No asset allocations found");
			throw new DataNotFoundException("No asset allocations available");
		}

		log.info("Found {} asset allocations", assetAllocationList.size());
		return assetAllocationList.stream()
				.map(assetAllocation -> modelMapper.map(assetAllocation, AssetAllocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public AssetAllocationDTO getAllocationByAllocationId(Long allocationId) throws DataNotFoundException {
		log.info("Fetching asset allocation with ID: {}", allocationId);

		AssetAllocation assetAllocation = assetAllocationRepo.findById(allocationId).orElseThrow(() -> {
			log.warn("Asset allocation not found for ID: {}", allocationId);
			return new DataNotFoundException("No asset allocation found with ID: " + allocationId);
		});

		log.info("Found asset allocation with ID: {}", allocationId);
		return modelMapper.map(assetAllocation, AssetAllocationDTO.class);
	}

	@Override
	public AssetAllocationDTO getAllocationByAssetNo(Long assetNo) throws DataNotFoundException {
		log.info("Fetching asset allocation with asset number: {}", assetNo);

		AssetAllocation assetAllocation = assetAllocationRepo.findByAsset_AssetNo(assetNo).orElseThrow(() -> {
			log.warn("Asset allocation not found for asset number: {}", assetNo);
			return new DataNotFoundException("No asset allocation found with asset number: " + assetNo);
		});

		log.info("Found asset allocation with asset number: {}", assetNo);
		return modelMapper.map(assetAllocation, AssetAllocationDTO.class);

	}

	@Override
	public List<AssetAllocationDTO> getAllocationsByUserId(Long usersId) throws DataNotFoundException {
		log.info("Fetching asset allocations for user ID: {}", usersId);

		List<AssetAllocation> assetAllocationList = assetAllocationRepo.findByUser_UsersId(usersId).orElse(null);
		if (assetAllocationList.isEmpty()) {
			log.warn("No asset allocations found for user ID: {}", usersId);
			throw new DataNotFoundException("No asset allocations found for user ID: " + usersId);
		}

		log.info("Found {} asset allocations for user ID: {}", assetAllocationList.size(), usersId);
		return assetAllocationList.stream()
				.map(assetAllocation -> modelMapper.map(assetAllocation, AssetAllocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public String returnAsset(Long assetNo) throws DataNotFoundException {
		log.info("Returning asset with asset No: {}", assetNo);

		AssetAllocation assetAllocation = assetAllocationRepo.findByAsset_AssetNo(assetNo).orElse(null);

		assetAllocationRepo.deleteById(assetAllocation.getAllocationId());
		auditRequestRepo.deleteByAsset_AssetNo(assetNo);
		serviceRequestRepo.deleteByAsset_AssetNo(assetNo);
		log.info("Deleted asset allocation with ID: {}", assetAllocation.getAllocationId());

		Asset asset = assetRepo.findById(assetAllocation.getAsset().getAssetNo()).orElse(null);

		asset.setAvailability("available");
		assetRepo.save(asset);
		return "Asset allocation with ID " + assetAllocation.getAllocationId() + " deleted successfully";
	}

	// Audit Request Operations

	@Override
	public AuditRequestDTO addAuditRequest(AuditRequestDTO auditRequestDto) throws DataAlreadyExistException {

		log.info("Adding a new audit request for userId: {} for the asset ID: {}", auditRequestDto.getUsersId(),
				auditRequestDto.getAssetNo());

		AuditRequest auditRequest = new AuditRequest();

		Asset assetAlreadyExists = assetRepo.findById(auditRequestDto.getAssetNo()).orElse(null);
		Users usersAlreadyExists = usersRepo.findById(auditRequestDto.getUsersId()).orElse(null);

		boolean assetAllocationExists = assetAllocationRepo
				.existsByUser_UsersIdAndAsset_AssetNo(auditRequestDto.getUsersId(), auditRequestDto.getAssetNo());
		boolean exists = auditRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(auditRequestDto.getUsersId(),
				auditRequestDto.getAssetNo());

		if (!assetAllocationExists) {
			log.warn("Asset is not allocated for userId: {}", auditRequestDto.getUsersId());
			throw new DataAlreadyExistException("Asset is not allocated to this User");
		}
		if (exists) {
			log.warn("Audit request already exists for userId: {} and assetNo: {}", auditRequestDto.getUsersId(),
					auditRequestDto.getAssetNo());
			throw new DataAlreadyExistException("An audit request has already been sent for this asset.");
		}

		auditRequest.setAsset(assetAlreadyExists);
		auditRequest.setUser(usersAlreadyExists);
		auditRequest = auditRequestRepo.save(auditRequest);

		log.info("Audit request saved with ID: {}", auditRequest.getRequestId());

		return modelMapper.map(auditRequest, AuditRequestDTO.class);
	}

	@Override
	public List<AuditRequestDTO> getAllAuditRequests() throws DataNotFoundException {
		log.info("Fetching all audit requests");

		List<AuditRequest> auditRequestList = auditRequestRepo.findAll();

		if (auditRequestList.isEmpty()) {
			log.warn("No audit requests found in the database.");
			throw new DataNotFoundException("No audit requests available to display.");
		}

		List<AuditRequestDTO> auditRequestDtoList = auditRequestList.stream()
				.map(auditRequest -> modelMapper.map(auditRequest, AuditRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} audit requests.", auditRequestDtoList.size());
		return auditRequestDtoList;
	}

	@Override
	public List<AuditRequestDTO> getAuditRequestsByUsersId(Long usersId) throws DataNotFoundException {
		log.info("Fetching audit requests for usersId: {}", usersId);

		List<AuditRequest> auditRequestList = auditRequestRepo.findAllByUser_UsersId(usersId);

		if (auditRequestList.isEmpty()) {
			log.warn("No audit requests found for usersId: {}", usersId);
			throw new DataNotFoundException("No audit requests found for userId: " + usersId);
		}

		List<AuditRequestDTO> auditRequestDtoList = auditRequestList.stream()
				.map(auditRequest -> modelMapper.map(auditRequest, AuditRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} audit requests for userId: {}", auditRequestDtoList.size(), usersId);
		return auditRequestDtoList;
	}

	@Override
	public List<AuditRequestDTO> getAuditRequestsByStatus(String status) throws DataNotFoundException {
		log.info("Fetching audit requests with status: {}", status);

		List<AuditRequest> auditRequestList = auditRequestRepo.findAllByStatus(status);

		if (auditRequestList.isEmpty()) {
			log.warn("No audit requests found with status: {}", status);
			throw new DataNotFoundException("No audit requests found with status: " + status);
		}

		List<AuditRequestDTO> auditRequestDtoList = auditRequestList.stream()
				.map(auditRequest -> modelMapper.map(auditRequest, AuditRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} audit requests with status: {}", auditRequestDtoList.size(), status);
		return auditRequestDtoList;
	}

	@Override
	public AuditRequestDTO getAuditRequestsByRequestId(Long requestId) throws DataNotFoundException {
		log.info("Fetching audit request with ID: {}", requestId);

		AuditRequest auditRequest = auditRequestRepo.findById(requestId).orElseThrow(() -> {
			log.warn("Audit request not found with ID: {}", requestId);
			return new DataNotFoundException("Audit request not found with ID: " + requestId);
		});

		log.info("Audit request found with ID: {}", requestId);

		return modelMapper.map(auditRequest, AuditRequestDTO.class);
	}

	@Override
	@Transactional
	public AuditRequestDTO updateAuditRequestStatus(Long requestId, String status) throws DataNotFoundException {
		log.info("Attempting to update audit request status. Request ID: {}, New Status: {}", requestId, status);

		AuditRequest auditRequest = auditRequestRepo.findById(requestId).orElseThrow(() -> {
			log.warn("Audit request with ID {} not found for status update", requestId);
			return new DataNotFoundException("Audit request not found with ID: " + requestId);
		});

		auditRequest.setStatus(status);
		auditRequest = auditRequestRepo.save(auditRequest);

		log.info("Successfully updated audit request ID: {} with new status: {}", requestId, status);
		return modelMapper.map(auditRequest, AuditRequestDTO.class);
	}

	// Asset Request Operations

	@Override
	public AssetRequestDTO addAssetRequest(AssetRequestDTO assetRequestDto) throws DataAlreadyExistException {

		log.info("Adding a new asset request for userId: {} for the asset ID: {}", assetRequestDto.getUsersId(),
				assetRequestDto.getAssetNo());

		AssetRequest assetRequest = new AssetRequest();

		Asset assetAlreadyExists = assetRepo.findById(assetRequestDto.getAssetNo()).orElse(null);
		Users usersAlreadyExists = usersRepo.findById(assetRequestDto.getUsersId()).orElse(null);

		boolean exists = assetRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(assetRequestDto.getUsersId(),
				assetRequestDto.getAssetNo());

		if (exists) {
			log.warn("Asset request already exists for userId: {} and assetNo: {}", assetRequestDto.getUsersId(),
					assetRequestDto.getAssetNo());
			throw new DataAlreadyExistException("An asset request has already been sent for this asset.");
		}

		assetRequest.setAsset(assetAlreadyExists);
		assetRequest.setUser(usersAlreadyExists);
		assetRequest = assetRequestRepo.save(assetRequest);

		log.info("Asset request saved with ID: {}", assetRequest.getRequestId());

		return modelMapper.map(assetRequest, AssetRequestDTO.class);
	}

	@Override
	public List<AssetRequestDTO> getAllAssetRequests() throws DataNotFoundException {
		log.info("Fetching all Asset requests");

		List<AssetRequest> assetRequestList = assetRequestRepo.findAll();

		if (assetRequestList.isEmpty()) {
			log.warn("No Asset requests found in the database.");
			throw new DataNotFoundException("No Asset requests available to display.");
		}

		List<AssetRequestDTO> assetRequestDtoList = assetRequestList.stream()
				.map(assetRequest -> modelMapper.map(assetRequest, AssetRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} Asset requests.", assetRequestDtoList.size());
		return assetRequestDtoList;
	}

	@Override
	public List<AssetRequestDTO> getAssetRequestsByUsersId(Long usersId) throws DataNotFoundException {
		log.info("Fetching Asset requests for usersId: {}", usersId);

		List<AssetRequest> assetRequestList = assetRequestRepo.findAllByUser_UsersId(usersId);

		if (assetRequestList.isEmpty()) {
			log.warn("No Asset requests found for usersId: {}", usersId);
			throw new DataNotFoundException("No Asset requests found for userId: " + usersId);
		}

		List<AssetRequestDTO> assetRequestDtoList = assetRequestList.stream()
				.map(assetRequest -> modelMapper.map(assetRequest, AssetRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} Asset requests for userId: {}", assetRequestDtoList.size(), usersId);
		return assetRequestDtoList;
	}

	@Override
	public List<AssetRequestDTO> getAssetRequestsByStatus(String status) throws DataNotFoundException {
		log.info("Fetching Asset requests with status: {}", status);

		List<AssetRequest> assetRequestList = assetRequestRepo.findAllByStatus(status);

		if (assetRequestList.isEmpty()) {
			log.warn("No Asset requests found with status: {}", status);
			throw new DataNotFoundException("No Asset requests found with status: " + status);
		}

		List<AssetRequestDTO> assetRequestDtoList = assetRequestList.stream()
				.map(assetRequest -> modelMapper.map(assetRequest, AssetRequestDTO.class)).collect(Collectors.toList());

		log.info("Successfully retrieved {} Asset requests with status: {}", assetRequestDtoList.size(), status);
		return assetRequestDtoList;
	}

	@Override
	public AssetRequestDTO getAssetRequestsByRequestId(Long requestId) throws DataNotFoundException {
		log.info("Fetching Asset request with ID: {}", requestId);

		AssetRequest assetRequest = assetRequestRepo.findById(requestId).orElseThrow(() -> {
			log.warn("Asset request not found with ID: {}", requestId);
			return new DataNotFoundException("Asset request not found with ID: " + requestId);
		});

		log.info("Asset request found with ID: {}", requestId);

		return modelMapper.map(assetRequest, AssetRequestDTO.class);
	}

	@Override
	@Transactional
	public AssetRequestDTO updateAssetRequestStatus(Long requestId, String status) throws DataNotFoundException {
		log.info("Attempting to update Asset request status. Request ID: {}, New Status: {}", requestId, status);

		AssetRequest assetRequest = assetRequestRepo.findById(requestId).orElseThrow(() -> {
			log.warn("Asset request with ID {} not found for status update", requestId);
			return new DataNotFoundException("Asset request not found with ID: " + requestId);
		});

		assetRequest.setStatus(status);
		assetRequest = assetRequestRepo.save(assetRequest);

		if (status.equalsIgnoreCase("Approved")) {
			AssetAllocation allocation = new AssetAllocation();
			allocation.setReturnDate(LocalDate.now().plusMonths(1));
			allocation.setAsset(assetRequest.getAsset());
			allocation.setUser(assetRequest.getUser());
			assetAllocationRepo.save(allocation);

			Asset asset = assetRequest.getAsset();
			asset.setAvailability("unavailable");
			assetRepo.save(asset);

			assetRequestRepo.delete(assetRequest);
		}

		log.info("Successfully updated Asset request ID: {} with new status: {}", requestId, status);
		return modelMapper.map(assetRequest, AssetRequestDTO.class);
	}

	// Service request operations

	@Override
	public ServiceRequestDTO addServiceRequest(ServiceRequestDTO serviceRequestDto)
			throws DataNotFoundException, DataAlreadyExistException {

		log.info("Attempting to add service request for userId={} and assetNo={}", serviceRequestDto.getUsersId(),
				serviceRequestDto.getAssetNo());

		Users user = usersRepo.findById(serviceRequestDto.getUsersId()).orElseThrow(() -> {
			log.error("User with ID {} not found", serviceRequestDto.getUsersId());
			return new DataNotFoundException("User does not exist");
		});

		Asset asset = assetRepo.findById(serviceRequestDto.getAssetNo()).orElseThrow(() -> {
			log.error("Asset with assetNo {} not found", serviceRequestDto.getAssetNo());
			return new DataNotFoundException("Asset does not exist");
		});

		boolean exists = serviceRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(user.getUsersId(), asset.getAssetNo());
		if (exists) {
			log.warn("Service request already exists for userId={} and assetNo={}", user.getUsersId(),
					asset.getAssetNo());
			throw new DataAlreadyExistException("Service request already exists");
		}

		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setDescription(serviceRequestDto.getDescription());
		serviceRequest.setIssueType(serviceRequestDto.getIssueType());
		serviceRequest.setUser(user);
		serviceRequest.setAsset(asset);

		serviceRequestRepo.save(serviceRequest);

		log.info("Service request created successfully for requestId={}", serviceRequest.getRequestId());

		return modelMapper.map(serviceRequest, ServiceRequestDTO.class);
	}

	@Override
	@Transactional
	public ServiceRequestDTO updateServiceRequestStatus(Long requestId, String status) throws DataNotFoundException {

		log.info("Updating status of service request with ID={} to '{}'", requestId, status);

		ServiceRequest serviceRequest = serviceRequestRepo.findById(requestId).orElseThrow(() -> {
			log.error("Service request with ID {} not found", requestId);
			return new DataNotFoundException("Service request does not exist");
		});

		serviceRequest.setStatus(status);
		serviceRequestRepo.save(serviceRequest);

		if (status.equalsIgnoreCase("Completed")) {
			Asset asset = serviceRequest.getAsset();
			asset.setAvailability("available");
			assetRepo.save(asset);
			serviceRequestRepo.delete(serviceRequest);
			log.info("Service request with ID={} marked as completed and deleted", requestId);
		}

		return modelMapper.map(serviceRequest, ServiceRequestDTO.class);

	}

	@Override
	public List<ServiceRequestDTO> getAllServiceRequests() throws DataNotFoundException {

		log.info("Fetching all service requests");

		List<ServiceRequest> serviceRequestDtoList = serviceRequestRepo.findAll();
		if (serviceRequestDtoList.isEmpty()) {
			log.warn("No service requests found");
			throw new DataNotFoundException("No data to display");
		}

		return serviceRequestDtoList.stream()
				.map(serviceRequest -> modelMapper.map(serviceRequest, ServiceRequestDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ServiceRequestDTO> getAllServiceRequestsByUsersId(Long usersId) throws DataNotFoundException {

		log.info("Fetching service requests for userId={}", usersId);

		List<ServiceRequest> serviceRequestDtoList = serviceRequestRepo.findAllByUser_UsersId(usersId);
		if (serviceRequestDtoList.isEmpty()) {
			log.warn("No service requests found for userId={}", usersId);
			throw new DataNotFoundException("No data to display");
		}
		return serviceRequestDtoList.stream()
				.map(serviceRequest -> modelMapper.map(serviceRequest, ServiceRequestDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ServiceRequestDTO getServiceRequestsByRequestId(Long requestId) throws DataNotFoundException {

		log.info("Fetching service request with ID={}", requestId);

		ServiceRequest serviceRequest = serviceRequestRepo.findById(requestId).orElseThrow(() -> {

			log.error("Service request with ID {} not found", requestId);
			return new DataNotFoundException("Service request does not exist");
		});

		return modelMapper.map(serviceRequest, ServiceRequestDTO.class);
	}

	@Override
	public List<ServiceRequestDTO> getAllServiceRequestsByStatus(String status) throws DataNotFoundException {

		log.info("Fetching service requests with status='{}'", status);

		List<ServiceRequest> serviceRequestDtoList = serviceRequestRepo.findAllByStatus(status);
		if (serviceRequestDtoList.isEmpty()) {
			log.warn("No service requests found with status '{}'", status);
			throw new DataNotFoundException("No data to display");
		}
		return serviceRequestDtoList.stream()
				.map(serviceRequest -> modelMapper.map(serviceRequest, ServiceRequestDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ServiceRequestDTO> getAllServiceRequestsByIssueType(String issueType) throws DataNotFoundException {

		log.info("Fetching service requests with issueType='{}'", issueType);

		List<ServiceRequest> serviceRequestDtoList = serviceRequestRepo.findAllByIssueType(issueType);
		if (serviceRequestDtoList.isEmpty()) {
			log.warn("No service requests found with issueType '{}'", issueType);
			throw new DataNotFoundException("No data to display");
		}
		return serviceRequestDtoList.stream()
				.map(serviceRequest -> modelMapper.map(serviceRequest, ServiceRequestDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public String deleteAuditRequest(Long requestId) {

		auditRequestRepo.deleteById(requestId);
		return "Request deleted successfully";
	}

	@Override
	public String deleteAssetRequest(Long requestId) {

		assetRequestRepo.deleteById(requestId);
		return "Request deleted successfully";
	}

	@Override
	public String deleteServiceRequest(Long requestId) {

		serviceRequestRepo.deleteById(requestId);
		return "Request deleted successfully";
	}

	@Override
	@Transactional
	public void deleteAssetAllocationByUsersId(Long usersId) throws DataNotFoundException {
		List<AssetAllocation> list = assetAllocationRepo.findByUser_UsersId(usersId).orElse(null);
		if(!list.isEmpty()) {
		for (AssetAllocation allocation : list) {
			Asset asset = assetRepo.findById(allocation.getAsset().getAssetNo()).orElse(null);
			if (asset != null) {
				asset.setAvailability("available");
			}
			assetRepo.save(asset);
		}
		
		assetAllocationRepo.deleteByUser_UsersId(usersId);}

	}

	@Override
	@Transactional
	public void deleteAssetRequestByUsersId(Long usersId) {
		assetRequestRepo.deleteByUser_UsersId(usersId);

	}

	@Override
	@Transactional
	public void deleteServiceRequestByUsersId(Long usersId) {
		serviceRequestRepo.deleteByUser_UsersId(usersId);

	}

	@Override
	@Transactional
	public void deleteAuditRequestByUsersId(Long usersId) {
		auditRequestRepo.deleteByUser_UsersId(usersId);

	}

}
