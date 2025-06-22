package com.hexaware.assetmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entities.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	List<Asset> findAllByCategory(String category);

	List<Asset> findAllByAvailability(String availability);

	Asset findByAssetName(String assetName);

}
