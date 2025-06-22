package com.hexaware.usermicroservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.usermicroservice.entity.Credential;
import com.hexaware.usermicroservice.repository.CredentialRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CredentialRepository credentialRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Credential credential = credentialRepo.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

		return new CustomUserDetails(credential);
	}
}
