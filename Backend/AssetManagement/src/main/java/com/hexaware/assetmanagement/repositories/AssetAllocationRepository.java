package com.hexaware.assetmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.assetmanagement.entities.AssetAllocation;

public interface AssetAllocationRepository extends JpaRepository<AssetAllocation, Long> {

	Optional<AssetAllocation> findByAsset_AssetNo(Long assetNo);

	Optional<List<AssetAllocation>> findByUser_UsersId(Long usersId);

	boolean existsByUser_UsersIdAndAsset_AssetNo(Long usersId, Long assetNo);

	void deleteByAsset_AssetNo(Long assetNo);

	void deleteByUser_UsersId(Long usersId);

}
