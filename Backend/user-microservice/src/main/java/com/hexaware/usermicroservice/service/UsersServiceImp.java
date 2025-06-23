package com.hexaware.usermicroservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.usermicroservice.dto.CredentialDTO;
import com.hexaware.usermicroservice.dto.UsersDTO;
import com.hexaware.usermicroservice.entity.Credential;
import com.hexaware.usermicroservice.entity.Users;
import com.hexaware.usermicroservice.exception.DataAlreadyExistException;
import com.hexaware.usermicroservice.exception.DataNotFoundException;
import com.hexaware.usermicroservice.repository.CredentialRepository;
import com.hexaware.usermicroservice.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class that handles business logic related to User operations such as
 * registration, login, and retrieval of user details.
 *
 * This class communicates with the UserRepository to perform CRUD operations.
 * It also handles exceptions and input validations.
 * 
 * @author Yakesh
 * @version 1.0
 * @since 2025-05-28
 */
@Service
@Slf4j
public class UsersServiceImp implements IUsersService {

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	CredentialRepository credentialRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UsersDTO addUsers(Users user) throws DataAlreadyExistException {

		log.info("Attempting to add user with ID: {}", user.getUsersId());
		user.setRole(user.getRole().toUpperCase());
		usersRepo.save(user);
		log.info("User added successfully with ID: {}", user.getUsersId());
		return users2dto(user);
	}

	@Override
	public UsersDTO updateUsers(Users user) throws DataNotFoundException {

		log.info("Attempting to update user with ID: {}", user.getUsersId());

		usersRepo.findById(user.getUsersId()).orElseThrow(() -> {
			log.error("User not found with ID: {}", user.getUsersId());
			return new DataNotFoundException("Requested user not found");
		});

		log.info("User updated successfully with ID: {}", user.getUsersId());
		usersRepo.save(user);
		return users2dto(user);
	}

	@Override
	public List<UsersDTO> getAllUsers() throws DataNotFoundException {

		log.info("Fetch all users");

		List<Users> usersList = usersRepo.findAll();
		if (usersList.isEmpty()) {
			log.warn("No users found in the database");
			throw new DataNotFoundException("No data to display");
		}

		log.info("Fetched all users");

		return users2dtolist(usersList);
	}

	@Override
	public UsersDTO getUsersByUsersId(Long usersId) throws DataNotFoundException {

		log.info("Fetching users with ID: {}", usersId);

		Users users = usersRepo.findById(usersId).orElseThrow(() -> {
			log.warn("No user found with ID: {}", usersId);
			return new DataNotFoundException("No user found with id " + usersId);
		});

		log.info("Fetched users with ID: {}", usersId);

		return users2dto(users);
	}

	@Override
	public String deleteUsersById(Long usersId) throws DataNotFoundException {

		log.info("Attempting to delete user with ID: {}", usersId);

		usersRepo.findById(usersId).orElseThrow(() -> {
			log.warn("User with ID {} does not exists", usersId);
			return new DataNotFoundException("Requested user not found");
		});
		usersRepo.deleteById(usersId);

		log.info("User with ID {} deleted successfully", usersId);

		return "User with Id " + usersId + " is deleted";
	}

	@Override
	public CredentialDTO addCredential(Credential credential) throws DataAlreadyExistException, DataNotFoundException {

		log.info("Attempting to add credential with ID: {}", credential.getUsersId());

		Credential oldCredential = credentialRepo.findById(credential.getUsersId()).orElse(null);
		if (oldCredential != null) {
			log.warn("Credential with user Id : {} already exist", credential.getUsersId());
			throw new DataAlreadyExistException("Credential already exist");
		}

		Users user = usersRepo.findById(credential.getUsersId())
				.orElseThrow(() -> new DataNotFoundException("User not found"));

		if (credentialRepo.existsById(user.getUsersId())) {
			throw new DataAlreadyExistException("Credential already exists");
		}

		credential.setUsersId(null);
		credential.setUser(user);
		credential.setUserName(credential.getUserName());
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));

		credentialRepo.save(credential);

		log.info("Credential added successfully with ID: {}", credential.getUsersId());

		return credential2dto(credential);

	}

	@Override
	public CredentialDTO updateCredential(Credential credential) throws DataNotFoundException {

		log.info("Attempting to update credential with ID: {}", credential.getUsersId());

		Credential oldCredential = credentialRepo.findById(credential.getUsersId()).orElse(null);
		if (oldCredential == null) {
			log.warn("Credential with user Id : {} does not exist", credential.getUsersId());
			throw new DataNotFoundException("Credential does not exist");
		}
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		credentialRepo.save(credential);

		log.info("Credential updated successfully with ID: {}", credential.getUsersId());

		return credential2dto(credential);
	}

	@Override
	public List<CredentialDTO> getAllCredentials() throws DataNotFoundException {

		log.info("Fetch all users");
		List<Credential> credentialList = credentialRepo.findAll();
		if (credentialList.isEmpty()) {
			log.warn("No credential found in the database");
			throw new DataNotFoundException("No data to display");
		}
		return credential2dtolist(credentialList);
	}

	@Override
	public CredentialDTO getCredentialByUsersId(Long usersId) throws DataNotFoundException {

		log.info("Fetching credentials with ID: {}", usersId);

		Credential credential = credentialRepo.findById(usersId).orElseThrow(() -> {
			log.warn("No credential found with user ID: {}", usersId);
			return new DataNotFoundException("No credential found with user id " + usersId);
		});
		log.info("Fetched credentials with ID: {}", usersId);
		return credential2dto(credential);
	}

	@Override
	public CredentialDTO getCredentialByUserName(String userName) throws DataNotFoundException {

		log.info("Fetching credentials with username: {}", userName);

		Credential credential = credentialRepo.findByUserName(userName).orElseThrow(() -> {
			log.warn("No credential found with username: {}", userName);
			return new DataNotFoundException("No credential found with user id " + userName);
		});
		log.info("Fetched credentials with username: {}", userName);
		return credential2dto(credential);
	}

	@Override
	public String deleteCredential(Long usersId) throws DataNotFoundException {

		log.info("Attempting to delete credential with ID: {}", usersId);

		credentialRepo.findById(usersId).orElseThrow(() -> {
			log.warn("Credential with ID {} does not exists", usersId);
			return new DataNotFoundException("Requested credential not found");
		});

		credentialRepo.deleteById(usersId);

		log.info("Credential with ID {} deleted successfully", usersId);

		return "Credential of the user with ID : " + usersId + " is deleted successfully";
	}

	public static UsersDTO users2dto(Users users) {
		UsersDTO usersDto = new UsersDTO();
		usersDto.setUsersId(users.getUsersId());
		usersDto.setFirstName(users.getFirstName());
		usersDto.setLastName(users.getLastName());
		usersDto.setRole(users.getRole());
		usersDto.setGender(users.getGender());
		usersDto.setAddress(users.getAddress());
		usersDto.setContactNumber(users.getContactNumber());
		usersDto.setEmail(users.getEmail());

		return usersDto;
	}

	public static List<UsersDTO> users2dtolist(List<Users> usersList) {
		List<UsersDTO> usersListDto = new ArrayList<>();
		for (Users users : usersList) {
			UsersDTO usersDto = new UsersDTO();
			usersDto.setUsersId(users.getUsersId());
			usersDto.setFirstName(users.getFirstName());
			usersDto.setLastName(users.getLastName());
			usersDto.setRole(users.getRole());
			usersDto.setGender(users.getGender());
			usersDto.setAddress(users.getAddress());
			usersDto.setContactNumber(users.getContactNumber());
			usersDto.setEmail(users.getEmail());

			usersListDto.add(usersDto);
		}
		return usersListDto;
	}

	public static CredentialDTO credential2dto(Credential credential) {
		CredentialDTO credentialDto = new CredentialDTO();
		credentialDto.setUsersId(credential.getUsersId());
		credentialDto.setPassword(credential.getPassword());
		credentialDto.setUserName(credential.getUserName());

		return credentialDto;
	}

	public static List<CredentialDTO> credential2dtolist(List<Credential> credentialList) {
		List<CredentialDTO> credentialListDto = new ArrayList<>();
		for (Credential credential : credentialList) {
			CredentialDTO credentialDto = new CredentialDTO();
			credentialDto.setUsersId(credential.getUsersId());
			credentialDto.setPassword(credential.getPassword());
			credentialDto.setUserName(credential.getUserName());

			credentialListDto.add(credentialDto);
		}
		return credentialListDto;
	}

}
