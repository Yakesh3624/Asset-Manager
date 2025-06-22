package com.hexaware.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.usermicroservice.dto.CredentialDTO;
import com.hexaware.usermicroservice.dto.UsersDTO;
import com.hexaware.usermicroservice.entity.Credential;
import com.hexaware.usermicroservice.entity.Users;
import com.hexaware.usermicroservice.exception.DataAlreadyExistException;
import com.hexaware.usermicroservice.exception.DataNotFoundException;
import com.hexaware.usermicroservice.repository.CredentialRepository;
import com.hexaware.usermicroservice.repository.UsersRepository;

class UsersServiceImpTest {

	@Mock
	private UsersRepository usersRepo;

	@Mock
	private CredentialRepository credentialRepo;

	@InjectMocks
	private UsersServiceImp service;

	private Users user;
	private UsersDTO usersDto;
	private Credential credential;
	private CredentialDTO credentialDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		user = new Users(1L, "John", "Doe", "Admin", "john.doe@example.com", "Male", "1234567890", "123 Main St");

		usersDto = new UsersDTO(1L, "John", "Doe", "Admin", "john.doe@example.com", "Male", "1234567890",
				"123 Main St");

		credential = new Credential(1L, "john123", "password", user);

		credentialDto = new CredentialDTO(1L, "john123", "password");
	}

	@Test
	void addUsers() throws DataAlreadyExistException {
		when(usersRepo.save(any(Users.class))).thenReturn(user);

		UsersDTO usersDto = service.addUsers(user);

		assertNotNull(usersDto);
		assertEquals(user.getUsersId(), usersDto.getUsersId());
		verify(usersRepo, times(1)).save(user);
	}

	@Test
	void updateUsers() throws DataNotFoundException {
		when(usersRepo.findById(user.getUsersId())).thenReturn(Optional.of(user));
		when(usersRepo.save(user)).thenReturn(user);

		UsersDTO usersDto = service.updateUsers(user);

		assertNotNull(usersDto);
		assertEquals(user.getUsersId(), usersDto.getUsersId());
		verify(usersRepo).findById(user.getUsersId());
		verify(usersRepo).save(user);
	}

	@Test
	void getAllUsers() throws DataNotFoundException {
		List<Users> usersList = Collections.singletonList(user);
		when(usersRepo.findAll()).thenReturn(usersList);

		List<UsersDTO> usersDto = service.getAllUsers();

		assertFalse(usersDto.isEmpty());
		assertEquals(1, usersDto.size());
		verify(usersRepo).findAll();
	}

	@Test
	void getUsersByUsersId() throws DataNotFoundException {
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user));

		UsersDTO usersDto = service.getUsersByUsersId(1L);

		assertNotNull(usersDto);
		assertEquals(user.getUsersId(), usersDto.getUsersId());
		verify(usersRepo).findById(1L);
	}

	@Test
	void deleteUsersById() throws DataNotFoundException {
		when(usersRepo.findById(1L)).thenReturn(Optional.of(user));
		doNothing().when(usersRepo).deleteById(1L);

		String usersDto = service.deleteUsersById(1L);

		assertTrue(usersDto.contains("deleted"));
		verify(usersRepo).findById(1L);
		verify(usersRepo).deleteById(1L);
	}

	@Test
	void addCredential() throws Exception {
		when(credentialRepo.findById(credential.getUsersId())).thenReturn(Optional.empty());
		when(usersRepo.findById(credential.getUsersId())).thenReturn(Optional.of(user));
		when(credentialRepo.existsById(user.getUsersId())).thenReturn(false);
		when(credentialRepo.save(any(Credential.class))).thenReturn(credential);

		CredentialDTO credentialDto = service.addCredential(credential);

		assertNotNull(credentialDto);
		assertEquals(credential.getUserName(), credentialDto.getUserName());
		verify(credentialRepo).save(credential);
	}

	@Test
	void updateCredential() throws DataNotFoundException {
		when(credentialRepo.findById(credential.getUsersId())).thenReturn(Optional.of(credential));
		when(credentialRepo.save(credential)).thenReturn(credential);

		CredentialDTO credentialDto = service.updateCredential(credential);

		assertNotNull(credentialDto);
		assertEquals(credential.getUserName(), credentialDto.getUserName());
		verify(credentialRepo).save(credential);
	}

	@Test
	void getAllCredentials() throws DataNotFoundException {
		List<Credential> list = Collections.singletonList(credential);
		when(credentialRepo.findAll()).thenReturn(list);

		List<CredentialDTO> credentialDto = service.getAllCredentials();

		assertFalse(credentialDto.isEmpty());
		assertEquals(1, credentialDto.size());
		verify(credentialRepo).findAll();
	}

	@Test
	void getCredentialByUsersId() throws DataNotFoundException {
		when(credentialRepo.findById(1L)).thenReturn(Optional.of(credential));

		CredentialDTO credentialDto = service.getCredentialByUsersId(1L);

		assertNotNull(credentialDto);
		assertEquals(credential.getUserName(), credentialDto.getUserName());
		verify(credentialRepo).findById(1L);
	}

	@Test
	void getCredentialByUserName() throws DataNotFoundException {
		when(credentialRepo.findByUserName("john123")).thenReturn(Optional.of(credential));

		CredentialDTO credentialDto = service.getCredentialByUserName("john123");

		assertNotNull(credentialDto);
		assertEquals(credential.getUsersId(), credentialDto.getUsersId());
		verify(credentialRepo).findByUserName("john123");
	}

	@Test
	void deleteCredential() throws DataNotFoundException {
		when(credentialRepo.findById(1L)).thenReturn(Optional.of(credential));
		doNothing().when(credentialRepo).deleteById(1L);

		String credentialDto = service.deleteCredential(1L);

		assertTrue(credentialDto.contains("deleted"));
		verify(credentialRepo).findById(1L);
		verify(credentialRepo).deleteById(1L);
	}

}
