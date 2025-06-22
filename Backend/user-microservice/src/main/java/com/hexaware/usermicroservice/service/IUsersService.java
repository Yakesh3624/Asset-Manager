package com.hexaware.usermicroservice.service;

import java.util.List;

import com.hexaware.usermicroservice.dto.CredentialDTO;
import com.hexaware.usermicroservice.dto.UsersDTO;
import com.hexaware.usermicroservice.entity.Credential;
import com.hexaware.usermicroservice.entity.Users;
import com.hexaware.usermicroservice.exception.DataAlreadyExistException;
import com.hexaware.usermicroservice.exception.DataNotFoundException;

public interface IUsersService {

	// Users Operations
	public UsersDTO addUsers(Users users) throws DataAlreadyExistException;

	public UsersDTO updateUsers(Users users) throws DataNotFoundException;

	public List<UsersDTO> getAllUsers() throws DataNotFoundException;

	public UsersDTO getUsersByUsersId(Long usersId) throws DataNotFoundException;

	public String deleteUsersById(Long usersId) throws DataNotFoundException;

	// Credential Operations
	public CredentialDTO addCredential(Credential credential) throws DataAlreadyExistException, DataNotFoundException;

	public CredentialDTO updateCredential(Credential credential) throws DataNotFoundException;

	public String deleteCredential(Long usersId) throws DataNotFoundException;

	public List<CredentialDTO> getAllCredentials() throws DataNotFoundException;

	public CredentialDTO getCredentialByUsersId(Long usersId) throws DataNotFoundException;

	public CredentialDTO getCredentialByUserName(String userName) throws DataNotFoundException;

}
