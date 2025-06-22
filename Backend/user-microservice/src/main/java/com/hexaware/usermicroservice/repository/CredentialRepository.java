package com.hexaware.usermicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.usermicroservice.entity.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

	Optional<Credential> findByUserName(String userName);

}
