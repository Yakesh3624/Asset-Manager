package com.hexaware.assetmanagement.exceptions;

/**
 * Custom exception thrown when attempting to add or create data that already
 * exists.
 * 
 * This exception is commonly thrown to prevent duplicate entries in the
 * database, such as creating a user with an existing username or an asset with
 * a duplicate asset number.
 * 
 * Helps maintain data integrity by enforcing uniqueness constraints at the
 * application level.
 * 
 * Author: Yakesh
 * 
 * @version 1.0
 * @since 2025-05-28
 */
public class DataAlreadyExistException extends Exception {

	public DataAlreadyExistException(String message) {
		super(message);
	}

}
