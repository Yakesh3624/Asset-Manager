package com.hexaware.assetmanagement.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.exceptions.DataAlreadyExistException;
import com.hexaware.assetmanagement.exceptions.DataNotFoundException;
import com.hexaware.assetmanagement.services.IAssetManagementService;

import jakarta.transaction.Transactional;

/**
 * REST controller for handling service/maintenance requests for assets.
 * 
 * Users can raise issues related to damaged or malfunctioning assets.
 * 
 * Admins can update or resolve service requests accordingly.
 * 
 * Endpoints include: /add, /getByAsset, /getAll, /updateStatus, etc.
 * 
 * Ensures timely servicing of organizational assets to maintain productivity.
 * 
 * All endpoints are secured using JWT and role-based access control.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/api/service-request")
public class ServiceRequestRestController {

	@Autowired
	IAssetManagementService service;

	@PostMapping("/add")
	ServiceRequestDTO addServiceRequest(@RequestBody ServiceRequestDTO serviceRequestDto)
			throws DataNotFoundException, DataAlreadyExistException {
		return service.addServiceRequest(serviceRequestDto);

	}

	@PutMapping("/update/status/{requestId}/{status}")
	ServiceRequestDTO updateServiceRequestStatus(@PathVariable Long requestId, @PathVariable String status)
			throws DataNotFoundException {
		return service.updateServiceRequestStatus(requestId, status);
	}

	@GetMapping("/getall")
	List<ServiceRequestDTO> getAllServiceRequests() throws DataNotFoundException {
		return service.getAllServiceRequests();

	}

	@GetMapping("/get/usersId/{usersId}")
	List<ServiceRequestDTO> getAllServiceRequestsByUsersId(@PathVariable Long usersId) throws DataNotFoundException {
		return service.getAllServiceRequestsByUsersId(usersId);
	}

	@GetMapping("/get/requestId/{requestId}")
	ServiceRequestDTO getServiceRequestsByRequestId(@PathVariable Long requestId) throws DataNotFoundException {
		return service.getServiceRequestsByRequestId(requestId);
	}

	@GetMapping("/get/status/{status}")
	List<ServiceRequestDTO> getAllServiceRequestsByStatus(@PathVariable String status) throws DataNotFoundException {
		return service.getAllServiceRequestsByStatus(status);
	}

	@GetMapping("/get/issue-type/{issueType}")
	List<ServiceRequestDTO> getAllServiceRequestsByIssueType(@PathVariable String issueType)
			throws DataNotFoundException {
		return service.getAllServiceRequestsByIssueType(issueType);
	}

	@DeleteMapping("/delete/{requestId}")
	public String deleteServiceRequest(@PathVariable Long requestId) {

		service.deleteServiceRequest(requestId);
		return "Request deleted successfully";
	}

	@Transactional
	@Modifying
	@DeleteMapping("/delete/usersId/{usersId}")
	void deleteServiceRequestByUsersId(@PathVariable Long usersId) {
		service.deleteServiceRequestByUsersId(usersId);
	}

}
