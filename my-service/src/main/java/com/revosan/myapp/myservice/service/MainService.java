/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.service
 * File: MainService.java
 *
 * Description:
 * This abstract class provides a common base implementation for the BaseService interface.
 * It handles basic CRUD operations by injecting a generic JpaRepository. This allows
 * concrete service implementations to inherit standard functionality and focus on
 * entity-specific business logic, promoting code reusability and maintainability.
 *
 * T represents the entity type, ID represents the ID type
 * 
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the abstract BaseService class.
 * V1.1.0 - Renamed to MainService and now implements the BaseService interface.
 */
package com.revosan.myapp.myservice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class MainService<T, ID> implements BaseService<T, ID> {

	private final JpaRepository<T, ID> repository;

	public MainService(JpaRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public T save(T entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(ID id) {
		return repository.existsById(id);
	}
}