/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.service
 * File: BaseService.java
 *
 * Description:
 * This interface defines a generic contract for common CRUD (Create, Read, Update, Delete)
 * operations that can be implemented by various service classes.
 * It uses generics to allow flexibility for different entity types (T) and
 * their corresponding ID types (ID). This promotes code reusability and
 * consistency across multiple services that handle similar data operations.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the generic BaseService interface.
 */
package com.revosan.myapp.myservice.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {

	/**
	 * Retrieves all entities of type T.
	 * 
	 * @return A list of all entities.
	 */
	List<T> findAll();

	/**
	 * Retrieves an entity by its ID.
	 * 
	 * @param id The ID of the entity.
	 * @return An Optional containing the entity if found, or empty if not.
	 */
	Optional<T> findById(ID id);

	/**
	 * Saves a new entity or updates an existing one. If the ID is null or not
	 * found, it creates a new entity. Otherwise, it updates the existing entity.
	 * 
	 * @param entity The entity to be saved or updated.
	 * @return The saved or updated entity.
	 */
	T save(T entity);

	/**
	 * Deletes an entity by its ID.
	 * 
	 * @param id The ID of the entity to delete.
	 */
	void deleteById(ID id);

	/**
	 * Checks if an entity with the given ID exists.
	 * 
	 * @param id The ID of the entity.
	 * @return true if the entity exists, false otherwise.
	 */
	boolean existsById(ID id);
}