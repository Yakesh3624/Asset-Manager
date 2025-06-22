package com.hexaware.usermicroservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.usermicroservice.dto.CredentialDTO;
import com.hexaware.usermicroservice.entity.Credential;
import com.hexaware.usermicroservice.exception.DataNotFoundException;
import com.hexaware.usermicroservice.service.IUsersService;

import jakarta.transaction.Transactional;

/**
 * REST controller for managing user credentials in the Asset Management System.
 * 
 * Handles operations such as creating, updating, and validating user
 * credentials.
 * 
 * This controller works with the authentication layer to securely manage
 * username, password, and role mappings stored in the system.
 * 
 * Endpoints may include: /create, /updatePassword, /validate, etc.
 * 
 * All endpoints are secured and follow best practices for password handling
 * (e.g., hashing).
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/credential")
public class CredentialRestController {

	@Autowired
	IUsersService service;

	@PutMapping("/update")
	@PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
	public CredentialDTO updateCredential(Credential credential) throws DataNotFoundException {
		return service.updateCredential(credential);
	}

	@Modifying
	@Transactional
	@DeleteMapping("/delete/{usersId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String deleteCredential(@PathVariable Long usersId) throws DataNotFoundException {
		return service.deleteCredential(usersId);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<CredentialDTO> getAllCredentials() throws DataNotFoundException {
		return service.getAllCredentials();
	}

	@GetMapping("/get-by-id/{usersId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CredentialDTO getCredentialByUsersId(@PathVariable Long usersId) throws DataNotFoundException {
		return service.getCredentialByUsersId(usersId);
	}

	@GetMapping("/get-by-username/{userName}")
	public CredentialDTO getCredentialByUserName(@PathVariable String userName) throws DataNotFoundException {
		return service.getCredentialByUserName(userName);
	}

}
