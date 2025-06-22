package com.hexaware.assetmanagement.exceptions;

/**
 * Custom exception thrown when requested data is not found in the system.
 * 
 * This exception is typically used in service or repository layers to indicate
 * that a search or retrieval operation returned no results.
 * 
 * For example, thrown when an entity with a given ID does not exist.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
public class DataNotFoundException extends Exception {

	public DataNotFoundException(String message) {
		super(message);
	}

}
