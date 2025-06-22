package com.hexaware.assetmanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Asset Management System Spring Boot application.
 * 
 * This class contains the entry point (main method) that bootstraps
 * and launches the Spring Boot application context.
 * 
 * It enables component scanning, auto-configuration, and sets up
 * all the necessary beans and configurations to run the application.
 * 
 * Author: Yakesh
 * @version 1.0
 * @since 2025-05-28
 */

@SpringBootApplication
public class AssetManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementApplication.class, args);
	}

	@Bean
	ModelMapper mapper()
	{
		return new ModelMapper();
	}
}
