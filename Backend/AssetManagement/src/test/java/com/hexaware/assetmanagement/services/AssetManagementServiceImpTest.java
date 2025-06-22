package com.hexaware.assetmanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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

class AssetManagementServiceImpTest {

	@Mock
	private AssetRepository assetRepo;

	@Mock
	private UsersRepository usersRepo;

	@Mock
	private AssetAllocationRepository assetAllocationRepo;

	@Mock
	private AuditRequestRepository auditRequestRepo;

	@Mock
	private AssetRequestRepository assetRequestRepo;

	@Mock
	private ServiceRequestRepository serviceRequestRepo;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AssetManagementServiceImp service;

	private Asset asset;
	private AssetDTO assetDto;
	private Users user;
	private AssetAllocation allocation;
	private AssetAllocationDTO allocationDto;
	private AuditRequestDTO auditRequestDto;
	private AuditRequest auditRequest;
	private AssetRequest assetRequest;
	private AssetRequestDTO assetRequestDto;
	private ServiceRequest serviceRequest;
	private ServiceRequestDTO serviceRequestDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		asset = new Asset(1L, "Dell Laptop", "Laptop", "XPS", LocalDate.now().minusYears(1),
				LocalDate.now().plusYears(2), 85000.0, "available", null);

		assetDto = new AssetDTO(1L, "Dell Laptop", "Laptop", "XPS", asset.getManufacturingDate(), asset.getExpiryDate(),
				85000.0, "available", null);

		user = new Users();
		user.setUsersId(1L);

		allocation = new AssetAllocation();
		allocation.setAllocationId(1L);
		allocation.setUser(user);
		allocation.setAsset(asset);
		allocation.setAllocationDate(LocalDate.now());
		allocation.setReturnDate(LocalDate.now().plusDays(10));

		allocationDto = new AssetAllocationDTO(1L, allocation.getAllocationDate(), allocation.getReturnDate(),
				asset.getAssetNo(), user.getUsersId());

		auditRequest = new AuditRequest(1L, new Timestamp(System.currentTimeMillis()), "Pending", asset, user);
		auditRequestDto = new AuditRequestDTO(1L, auditRequest.getRequestedAt(), "Pending", asset.getAssetNo(),
				user.getUsersId());

		assetRequest = new AssetRequest(1L, new Timestamp(System.currentTimeMillis()), "Pending", asset, user);
		assetRequestDto = new AssetRequestDTO(1L, auditRequest.getRequestedAt(), "Pending", asset.getAssetNo(),
				user.getUsersId());

		serviceRequest = new ServiceRequest(1L, "Battery issue", "Malfunction", "Pending",
				new Timestamp(System.currentTimeMillis()), asset, user);

		serviceRequestDto = new ServiceRequestDTO(1L, "Battery issue", "Malfunction", "Pending",
				serviceRequest.getRequestedAt(), 1L, 1L);
	}

	@Test
	void testAddAsset() throws DataAlreadyExistException {
		when(assetRepo.findByAssetName(assetDto.getAssetName())).thenReturn(null);
		when(modelMapper.map(assetDto, Asset.class)).thenReturn(asset);
		when(assetRepo.save(asset)).thenReturn(asset);
		when(modelMapper.map(asset, AssetDTO.class)).thenReturn(assetDto);

		AssetDTO result = service.addAsset(assetDto);

		assertThat(result).isNotNull();
		assertThat(result.getAssetName()).isEqualTo("Dell Laptop");
	}

	@Test
	void testUpdateAsset() throws DataNotFoundException, DataAlreadyExistException {

		when(assetRepo.findById(assetDto.getAssetNo())).thenReturn(Optional.of(asset));
		Asset updatedAsset = new Asset(assetDto.getAssetNo(), "New Laptop", "Laptop", "XPS",
				asset.getManufacturingDate(), asset.getExpiryDate(), 90000.0, "available", null);
		when(modelMapper.map(assetDto, Asset.class)).thenReturn(updatedAsset);
		when(assetRepo.save(updatedAsset)).thenReturn(updatedAsset);
		when(modelMapper.map(updatedAsset, AssetDTO.class)).thenReturn(assetDto);

		assetDto.setAssetName("New Laptop");
		AssetDTO result = service.updateAsset(assetDto);

		assertThat(result).isNotNull();
	}

	@Test
	void testUpdateAssetAvailability() throws DataNotFoundException {
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
		when(modelMapper.map(asset, AssetDTO.class)).thenReturn(assetDto);

		AssetDTO result = service.updateAssetAvailability(1L, "unavailable");

		assertThat(result).isNotNull();
		verify(assetRepo, times(1)).save(asset);
	}

	@Test
	void testGetAllAssets() throws DataNotFoundException {
		List<Asset> assetList = List.of(asset);
		when(assetRepo.findAll()).thenReturn(assetList);
		when(modelMapper.map(any(Asset.class), eq(AssetDTO.class))).thenReturn(assetDto);

		List<AssetDTO> result = service.getAllAssets();

		assertThat(result).hasSize(1);
	}

	@Test
	void testGetAssetByAssetNo() throws DataNotFoundException {
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
		when(modelMapper.map(asset, AssetDTO.class)).thenReturn(assetDto);

		AssetDTO result = service.getAssetByAssetNo(1L);

		assertThat(result).isNotNull();
	}

	@Test
	void testGetAssetsByCategory() throws DataNotFoundException {
		List<Asset> assetList = List.of(asset);
		when(assetRepo.findAllByCategory("Laptop")).thenReturn(assetList);
		when(modelMapper.map(any(Asset.class), eq(AssetDTO.class))).thenReturn(assetDto);

		List<AssetDTO> result = service.getAssetsByCategory("Laptop");

		assertThat(result).hasSize(1);
	}

	@Test
	void testGetAssetsByAvailability() throws DataNotFoundException {
		List<Asset> assetList = List.of(asset);
		when(assetRepo.findAllByAvailability("available")).thenReturn(assetList);
		when(modelMapper.map(any(Asset.class), eq(AssetDTO.class))).thenReturn(assetDto);

		List<AssetDTO> result = service.getAssetsByAvailability("available");

		assertThat(result).hasSize(1);
	}

	@Test
	void testDeleteAssetByAssetNo() throws DataNotFoundException {
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));

		String result = service.deleteAssetByAssetNo(1L);

		assertThat(result).isEqualTo("Asset with asset number 1 is deleted");
		verify(assetRepo).deleteById(1L);
	}

	// Asset Allocation

	@Test
	void testAllocateAssetToUser() throws DataNotFoundException {
		when(usersRepo.findById(user.getUsersId())).thenReturn(Optional.of(user));
		when(assetRepo.findById(asset.getAssetNo())).thenReturn(Optional.of(asset));
		when(assetAllocationRepo.save(any(AssetAllocation.class))).thenReturn(allocation);
		when(modelMapper.map(allocation, AssetAllocationDTO.class)).thenReturn(allocationDto);
		AssetAllocationDTO result = service.allocateAssetToUser(user.getUsersId(), asset.getAssetNo(),
				allocation.getReturnDate());

		assertNotNull(result);
		assertEquals(allocationDto.getAllocationId(), result.getAllocationId());
		verify(assetRepo).save(asset);
	}

	@Test
	void testGetAllAllocations() throws DataNotFoundException {
		List<AssetAllocation> list = List.of(allocation);
		when(assetAllocationRepo.findAll()).thenReturn(list);
		when(modelMapper.map(allocation, AssetAllocationDTO.class)).thenReturn(allocationDto);

		List<AssetAllocationDTO> result = service.getAllAllocations();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(allocationDto.getAllocationId(), result.get(0).getAllocationId());
	}

	@Test
	void testGetAllocationByAllocationId() throws DataNotFoundException {
		when(assetAllocationRepo.findById(1L)).thenReturn(Optional.of(allocation));
		when(modelMapper.map(allocation, AssetAllocationDTO.class)).thenReturn(allocationDto);

		AssetAllocationDTO result = service.getAllocationByAllocationId(1L);

		assertNotNull(result);
		assertEquals(allocationDto.getAllocationId(), result.getAllocationId());
	}

	@Test
	void testGetAllocationByAssetNo() throws DataNotFoundException {
		when(assetAllocationRepo.findByAsset_AssetNo(asset.getAssetNo())).thenReturn(Optional.of(allocation));
		when(modelMapper.map(allocation, AssetAllocationDTO.class)).thenReturn(allocationDto);

		AssetAllocationDTO result = service.getAllocationByAssetNo(asset.getAssetNo());

		assertNotNull(result);
		assertEquals(allocationDto.getAllocationId(), result.getAllocationId());
	}

	@Test
	void testGetAllocationsByUserId() throws DataNotFoundException {
		List<AssetAllocation> list = List.of(allocation);
		when(assetAllocationRepo.findByUser_UsersId(user.getUsersId())).thenReturn(Optional.of(list));
		when(modelMapper.map(allocation, AssetAllocationDTO.class)).thenReturn(allocationDto);

		List<AssetAllocationDTO> result = service.getAllocationsByUserId(user.getUsersId());

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(allocationDto.getAllocationId(), result.get(0).getAllocationId());
	}

	@Test
	void testReturnAsset() throws DataNotFoundException {
		when(assetAllocationRepo.findById(allocation.getAllocationId())).thenReturn(Optional.of(allocation));
		when(assetRepo.findById(asset.getAssetNo())).thenReturn(Optional.of(asset));

		String response = service.returnAsset(1L);

		assertEquals("Asset allocation with ID " + allocation.getAllocationId() + " deleted successfully", response);
		verify(assetAllocationRepo).deleteById(allocation.getAllocationId());
		verify(assetRepo).save(asset);
		assertEquals("available", asset.getAvailability());
	}

	// Audit Request

	@Test
	void testAddAuditRequest() throws DataAlreadyExistException {
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
		when(assetAllocationRepo.existsByUser_UsersIdAndAsset_AssetNo(1L, 1L)).thenReturn(true);
		when(auditRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(1L, 1L)).thenReturn(false);
		when(auditRequestRepo.save(any())).thenReturn(auditRequest);
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		AuditRequestDTO result = service.addAuditRequest(auditRequestDto);
		assertThat(result).isNotNull();
		assertThat(result.getAssetNo()).isEqualTo(1L);
		assertThat(result.getUsersId()).isEqualTo(1L);
	}

	@Test
	void testGetAllAuditRequests() throws DataNotFoundException {
		List<AuditRequest> list = List.of(auditRequest);
		when(auditRequestRepo.findAll()).thenReturn(list);
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		List<AuditRequestDTO> result = service.getAllAuditRequests();
		assertThat(result).hasSize(1);
	}

	@Test
	void testGetAuditRequestsByUsersId() throws DataNotFoundException {
		List<AuditRequest> list = List.of(auditRequest);
		when(auditRequestRepo.findAllByUser_UsersId(1L)).thenReturn(list);
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		List<AuditRequestDTO> result = service.getAuditRequestsByUsersId(1L);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getUsersId()).isEqualTo(1L);
	}

	@Test
	void testGetAuditRequestsByStatus() throws DataNotFoundException {
		List<AuditRequest> list = List.of(auditRequest);
		when(auditRequestRepo.findAllByStatus("Pending")).thenReturn(list);
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		List<AuditRequestDTO> result = service.getAuditRequestsByStatus("Pending");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getStatus()).isEqualTo("Pending");
	}

	@Test
	void testGetAuditRequestsByRequestId() throws DataNotFoundException {
		when(auditRequestRepo.findById(1L)).thenReturn(Optional.of(auditRequest));
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		AuditRequestDTO result = service.getAuditRequestsByRequestId(1L);
		assertThat(result.getRequestId()).isEqualTo(1L);
	}

	@Test
	void testUpdateAuditRequestStatus() throws DataNotFoundException {
		when(auditRequestRepo.findById(1L)).thenReturn(Optional.of(auditRequest));
		when(auditRequestRepo.save(any())).thenReturn(auditRequest);
		when(modelMapper.map(auditRequest, AuditRequestDTO.class)).thenReturn(auditRequestDto);

		AuditRequestDTO result = service.updateAuditRequestStatus(1L, "Approved");
		assertThat(result.getStatus()).isEqualTo("Pending");
//		verify(auditRequestRepo, times(1)).delete(auditRequest);
	}

	// Asset Request

	@Test
	void testAddassetRequest() throws DataAlreadyExistException {
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
		when(assetAllocationRepo.existsByUser_UsersIdAndAsset_AssetNo(1L, 1L)).thenReturn(true);
		when(assetRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(1L, 1L)).thenReturn(false);
		when(assetRequestRepo.save(any())).thenReturn(assetRequest);
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		AssetRequestDTO result = service.addAssetRequest(assetRequestDto);
		assertThat(result).isNotNull();
		assertThat(result.getAssetNo()).isEqualTo(1L);
		assertThat(result.getUsersId()).isEqualTo(1L);
	}

	@Test
	void testGetAllassetRequests() throws DataNotFoundException {
		List<AssetRequest> list = List.of(assetRequest);
		when(assetRequestRepo.findAll()).thenReturn(list);
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		List<AssetRequestDTO> result = service.getAllAssetRequests();
		assertThat(result).hasSize(1);
	}

	@Test
	void testGetassetRequestsByUsersId() throws DataNotFoundException {
		List<AssetRequest> list = List.of(assetRequest);
		when(assetRequestRepo.findAllByUser_UsersId(1L)).thenReturn(list);
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		List<AssetRequestDTO> result = service.getAssetRequestsByUsersId(1L);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getUsersId()).isEqualTo(1L);
	}

	@Test
	void testGetassetRequestsByStatus() throws DataNotFoundException {
		List<AssetRequest> list = List.of(assetRequest);
		when(assetRequestRepo.findAllByStatus("Pending")).thenReturn(list);
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		List<AssetRequestDTO> result = service.getAssetRequestsByStatus("Pending");
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getStatus()).isEqualTo("Pending");
	}

	@Test
	void testGetassetRequestsByRequestId() throws DataNotFoundException {
		when(assetRequestRepo.findById(1L)).thenReturn(Optional.of(assetRequest));
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		AssetRequestDTO result = service.getAssetRequestsByRequestId(1L);
		assertThat(result.getRequestId()).isEqualTo(1L);
	}

	@Test
	void testUpdateassetRequestStatus() throws DataNotFoundException {
		when(assetRequestRepo.findById(1L)).thenReturn(Optional.of(assetRequest));
		when(assetRequestRepo.save(any())).thenReturn(assetRequest);
		when(modelMapper.map(assetRequest, AssetRequestDTO.class)).thenReturn(assetRequestDto);

		AssetRequestDTO result = service.updateAssetRequestStatus(1L, "Approved");
		assertThat(result.getStatus()).isEqualTo("Pending");
		verify(assetRequestRepo, times(1)).delete(assetRequest);
	}

	// Service Request

	@Test
	void addServiceRequest() throws DataNotFoundException, DataAlreadyExistException {
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
		when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
		when(serviceRequestRepo.existsByUser_UsersIdAndAsset_AssetNo(1L, 1L)).thenReturn(false);
		when(serviceRequestRepo.save(any(ServiceRequest.class))).thenReturn(serviceRequest);
		when(modelMapper.map(any(ServiceRequest.class), eq(ServiceRequestDTO.class))).thenReturn(serviceRequestDto);

		ServiceRequestDTO result = service.addServiceRequest(serviceRequestDto);

		assertThat(result).isNotNull();
		assertThat(result.getDescription()).isEqualTo("Battery issue");

		verify(serviceRequestRepo).save(any(ServiceRequest.class));
	}

	@Test
	void updateServiceRequestStatus() throws DataNotFoundException {
		serviceRequest.setStatus("Pending");

		when(serviceRequestRepo.findById(1L)).thenReturn(Optional.of(serviceRequest));
		when(assetRepo.save(any(Asset.class))).thenReturn(asset);
		when(modelMapper.map(any(ServiceRequest.class), eq(ServiceRequestDTO.class))).thenReturn(serviceRequestDto);

		ServiceRequestDTO result = service.updateServiceRequestStatus(1L, "Completed");

		assertThat(result).isNotNull();
		assertThat(asset.getAvailability()).isEqualTo("available");

		verify(serviceRequestRepo).delete(serviceRequest);
	}

	@Test
	void getAllServiceRequests() throws DataNotFoundException {
		when(serviceRequestRepo.findAll()).thenReturn(List.of(serviceRequest));
		when(modelMapper.map(serviceRequest, ServiceRequestDTO.class)).thenReturn(serviceRequestDto);

		List<ServiceRequestDTO> result = service.getAllServiceRequests();

		assertThat(result).hasSize(1);
		verify(serviceRequestRepo).findAll();
	}

	@Test
	void getAllServiceRequestsByUsersId() throws DataNotFoundException {
		when(serviceRequestRepo.findAllByUser_UsersId(1L)).thenReturn(List.of(serviceRequest));
		when(modelMapper.map(serviceRequest, ServiceRequestDTO.class)).thenReturn(serviceRequestDto);

		List<ServiceRequestDTO> result = service.getAllServiceRequestsByUsersId(1L);

		assertThat(result).hasSize(1);
	}

	@Test
	void getServiceRequestsByRequestId() throws DataNotFoundException {
		when(serviceRequestRepo.findById(1L)).thenReturn(Optional.of(serviceRequest));
		when(modelMapper.map(serviceRequest, ServiceRequestDTO.class)).thenReturn(serviceRequestDto);

		ServiceRequestDTO result = service.getServiceRequestsByRequestId(1L);

		assertThat(result).isNotNull();
	}

	@Test
	void getAllServiceRequestsByStatus() throws DataNotFoundException {
		when(serviceRequestRepo.findAllByStatus("Pending")).thenReturn(List.of(serviceRequest));
		when(modelMapper.map(serviceRequest, ServiceRequestDTO.class)).thenReturn(serviceRequestDto);

		List<ServiceRequestDTO> result = service.getAllServiceRequestsByStatus("Pending");

		assertThat(result).hasSize(1);
	}

	@Test
	void getAllServiceRequestsByIssueType() throws DataNotFoundException {
		when(serviceRequestRepo.findAllByIssueType("Malfunction")).thenReturn(List.of(serviceRequest));
		when(modelMapper.map(serviceRequest, ServiceRequestDTO.class)).thenReturn(serviceRequestDto);

		List<ServiceRequestDTO> result = service.getAllServiceRequestsByIssueType("Malfunction");

		assertThat(result).hasSize(1);
	}

}