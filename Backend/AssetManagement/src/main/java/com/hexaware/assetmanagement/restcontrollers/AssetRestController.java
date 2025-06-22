package com.hexaware.assetmanagement.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.exceptions.DataAlreadyExistException;
import com.hexaware.assetmanagement.exceptions.DataNotFoundException;
import com.hexaware.assetmanagement.services.IAssetManagementService;

/**
 * REST controller for performing CRUD operations on assets within the Asset
 * Management System.
 * 
 * Provides endpoints to add, update, delete, and fetch assets based on
 * different filters such as category, availability, and asset number.
 * 
 * Enables admins to manage the asset catalog and keep it up to date.
 * 
 * Common endpoints include: /add, /update, /delete, /getAll, /getByAssetNo,
 * /getByCategory.
 * 
 * All endpoints are secured using JWT and role-based access control to ensure
 * only authorized users can modify asset data.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/asset")
public class AssetRestController {

	@Autowired
	IAssetManagementService service;

	@PostMapping("/add")
	AssetDTO addAsset(@RequestBody AssetDTO asset) throws DataAlreadyExistException {
		return service.addAsset(asset);
	}

	@PutMapping("/update")
	AssetDTO updateAsset(@RequestBody AssetDTO asset) throws DataNotFoundException, DataAlreadyExistException {
		return service.updateAsset(asset);
	}

	@PutMapping("/update/availability/{assetNo}/{availability}")
	AssetDTO updateAssetAvailability(@PathVariable Long assetNo, @PathVariable String availability)
			throws DataNotFoundException {
		return service.updateAssetAvailability(assetNo, availability);
	}

	@GetMapping("/getall")
	List<AssetDTO> getAllAssets() throws DataNotFoundException {
		return service.getAllAssets();
	}

	@GetMapping("/get-by-assetno/{assetNo}")
	AssetDTO getAssetByAssetNo(@PathVariable Long assetNo) throws DataNotFoundException {
		return service.getAssetByAssetNo(assetNo);
	}

	@GetMapping("/get-by-category/category/{category}")
	List<AssetDTO> getAssetsByCategory(@PathVariable String category) throws DataNotFoundException {
		return service.getAssetsByCategory(category);
	}

	@GetMapping("/get-by-availability/{availability}")
	List<AssetDTO> getAssetsByAvailability(@PathVariable String availability) throws DataNotFoundException {
		return service.getAssetsByAvailability(availability);
	}

	@DeleteMapping("/delete/{assetNo}")
	String deleteAssetByAssetNo(@PathVariable Long assetNo) throws DataNotFoundException {
		return service.deleteAssetByAssetNo(assetNo);
	}

}
