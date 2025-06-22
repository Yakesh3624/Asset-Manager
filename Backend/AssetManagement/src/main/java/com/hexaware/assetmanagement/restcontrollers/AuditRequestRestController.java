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

import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.exceptions.DataAlreadyExistException;
import com.hexaware.assetmanagement.exceptions.DataNotFoundException;
import com.hexaware.assetmanagement.services.IAssetManagementService;

import jakarta.transaction.Transactional;

/**
 * REST controller for managing audit requests related to assets.
 * 
 * Allows users to submit audit requests and view audit status.
 * 
 * Admins or auditors can fetch and manage these audit records.
 * 
 * Endpoints typically include: /add, /getByAssetId, /getAll, /updateStatus.
 * 
 * Helps ensure assets are periodically checked for compliance and integrity.
 * 
 * Secured with JWT authentication and role-based permissions.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/api/audit-request")
public class AuditRequestRestController {

	@Autowired
	IAssetManagementService service;

	@PostMapping("/add")
	public AuditRequestDTO addAuditRequest(@RequestBody AuditRequestDTO auditRequestDto)
			throws DataAlreadyExistException {
		return service.addAuditRequest(auditRequestDto);
	}

	@GetMapping("/getall")
	public List<AuditRequestDTO> getAllAuditRequests() throws DataNotFoundException {
		return service.getAllAuditRequests();
	}

	@GetMapping("/get/usersId/{usersId}")
	public List<AuditRequestDTO> getAuditRequestsByUsersId(@PathVariable Long usersId) throws DataNotFoundException {
		return service.getAuditRequestsByUsersId(usersId);
	}

	@GetMapping("/get/status/{status}")
	public List<AuditRequestDTO> getAuditRequestsByStatus(@PathVariable String status) throws DataNotFoundException {
		return service.getAuditRequestsByStatus(status);
	}

	@GetMapping("/get/requestId/{requestId}")
	public AuditRequestDTO getAuditRequestsByRequestId(@PathVariable Long requestId) throws DataNotFoundException {
		return service.getAuditRequestsByRequestId(requestId);
	}

	@PutMapping("/update/status/{requestId}/{status}")
	public AuditRequestDTO updateAuditRequestStatus(@PathVariable Long requestId, @PathVariable String status)
			throws DataNotFoundException {
		return service.updateAuditRequestStatus(requestId, status);
	}

	@DeleteMapping("/delete/{requestId}")
	public String deleteAuditRequest(@PathVariable Long requestId) {

		service.deleteAuditRequest(requestId);
		return "Request deleted successfully";
	}

	@Transactional
	@Modifying
	@DeleteMapping("/delete/usersId/{usersId}")
	void deleteAuditRequestByUsersId(@PathVariable Long usersId) {
		service.deleteAuditRequestByUsersId(usersId);
	}

}
