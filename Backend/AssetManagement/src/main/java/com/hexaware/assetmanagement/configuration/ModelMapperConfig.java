package com.hexaware.assetmanagement.configuration;

import java.util.Base64;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexaware.assetmanagement.dto.AssetAllocationDTO;
import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.dto.AssetRequestDTO;
import com.hexaware.assetmanagement.dto.AuditRequestDTO;
import com.hexaware.assetmanagement.dto.ServiceRequestDTO;
import com.hexaware.assetmanagement.entities.Asset;
import com.hexaware.assetmanagement.entities.AssetAllocation;
import com.hexaware.assetmanagement.entities.AssetRequest;
import com.hexaware.assetmanagement.entities.AuditRequest;
import com.hexaware.assetmanagement.entities.ServiceRequest;

/**
 * Configuration class for setting up ModelMapper bean.
 * 
 * Provides a ModelMapper instance to be used for object mapping between DTOs
 * and entity classes throughout the Asset Management System.
 * 
 * This configuration enables easy and consistent conversion between different
 * object models, reducing boilerplate code.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// byte[] -> Base64 String (Entity -> DTO)
		Converter<byte[], String> byteArrayToBase64 = new Converter<byte[], String>() {
			public String convert(MappingContext<byte[], String> context) {
				byte[] source = context.getSource();
				return source != null ? Base64.getEncoder().encodeToString(source) : null;
			}
		};

		// Base64 String -> byte[] (DTO -> Entity)
		Converter<String, byte[]> base64ToByteArray = new Converter<String, byte[]>() {
			public byte[] convert(MappingContext<String, byte[]> context) {
				String source = context.getSource();
				return source != null ? Base64.getDecoder().decode(source) : null;
			}
		};

		modelMapper.typeMap(Asset.class, AssetDTO.class)
				.addMappings(mapper -> mapper.using(byteArrayToBase64).map(Asset::getImage, AssetDTO::setImage));

		modelMapper.typeMap(AssetDTO.class, Asset.class)
				.addMappings(mapper -> mapper.using(base64ToByteArray).map(AssetDTO::getImage, Asset::setImage));

		// AssetAllocation mapping
		modelMapper.typeMap(AssetAllocation.class, AssetAllocationDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAsset().getAssetNo(), AssetAllocationDTO::setAssetNo);
			mapper.map(src -> src.getUser().getUsersId(), AssetAllocationDTO::setUsersId);
		});

		// AuditRequest mapping
		modelMapper.typeMap(AuditRequest.class, AuditRequestDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAsset().getAssetNo(), AuditRequestDTO::setAssetNo);
			mapper.map(src -> src.getUser().getUsersId(), AuditRequestDTO::setUsersId);
		});

		// AssetRequest mapping
		modelMapper.typeMap(AssetRequest.class, AssetRequestDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAsset().getAssetNo(), AssetRequestDTO::setAssetNo);
			mapper.map(src -> src.getUser().getUsersId(), AssetRequestDTO::setUsersId);
		});

		// ServiceRequest mapping
		modelMapper.typeMap(ServiceRequest.class, ServiceRequestDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getAsset().getAssetNo(), ServiceRequestDTO::setAssetNo);
			mapper.map(src -> src.getUser().getUsersId(), ServiceRequestDTO::setUsersId);
		});

		return modelMapper;
	}
}
