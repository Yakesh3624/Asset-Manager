package com.hexaware.usermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main class for the User Microservice Spring Boot application.
 * 
 * This class serves as the entry point for bootstrapping the microservice,
 * initializing the Spring application context and enabling component scanning,
 * auto-configuration, and other Spring Boot features.
 * 
 * It is responsible for launching all configured beans and starting
 * embedded servers (e.g., Tomcat) to handle incoming requests related to user management.
 * 
 * Author: Yakesh
 * @version 1.0
 * @since 2025-05-28
 */

@SpringBootApplication
public class UserMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMicroserviceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}
