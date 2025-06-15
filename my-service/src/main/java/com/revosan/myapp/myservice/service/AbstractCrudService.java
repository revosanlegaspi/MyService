package com.revosan.myapp.myservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.service File:
 * AbstractCrudService.java
 *
 * Description: This abstract class provides a common base implementation for
 * the BaseService interface. It handles basic CRUD operations by injecting a
 * generic JpaRepository. This allows concrete service implementations to
 * inherit standard functionality and focus on entity-specific business logic,
 * promoting code reusability and maintainability. Logging is integrated to
 * provide visibility into generic CRUD operations.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.0
 * 
 * Change Log: V1.0.0 - Initial creation of the abstract BaseService class.
 * 
 * @param <T>  The type of the entity.
 * @param <ID> The type of the entity's primary key.
 */

@Transactional
public abstract class AbstractCrudService<T, ID> implements BaseService<T, ID> {

	protected final Logger log = LoggerFactory.getLogger(AbstractCrudService.class);

	protected final JpaRepository<T, ID> repository;

	private final Class<T> entityClass;

	public AbstractCrudService(JpaRepository<T, ID> repository, Class<T> entityClass) {
		this.repository = repository;
		this.entityClass = entityClass;
		log.info("AbstractCrudService initialized for entity type: {}", entityClass.getSimpleName());
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		log.info("Retrieving all entities of type {}.", entityClass.getSimpleName());
		List<T> entities = repository.findAll();
		log.info("Found {} entities of type {}.", entities.size(), entityClass.getSimpleName());
		return entities;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<T> findById(ID id) {
		log.info("Attempting to find entity of type {} with ID: {}.", entityClass.getSimpleName(), id);
		Optional<T> entity = repository.findById(id);
		if (entity.isPresent()) {
			log.info("Entity of type {} with ID: {} found.", entityClass.getSimpleName(), id);
		} else {
			log.warn("Entity of type {} with ID: {} not found.", entityClass.getSimpleName(), id);
		}
		return entity;
	}

	@Override
	@Transactional
	public T save(T entity) {
		log.info("Saving entity of type {}.", entityClass.getSimpleName());
		log.debug("Entity details: {}", entity);
		T savedEntity = repository.save(entity);
		log.info("Entity of type {} saved successfully. ID: {}", entityClass.getSimpleName(), savedEntity);
		return savedEntity;
	}

	@Override
	@Transactional
	public void deleteById(ID id) {
		log.info("Attempting to delete entity of type {} with ID: {}.", entityClass.getSimpleName(), id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			log.info("Entity of type {} with ID: {} deleted successfully.", entityClass.getSimpleName(), id);
		} else {
			log.warn("Entity of type {} with ID: {} not found for deletion. No action taken.",
					entityClass.getSimpleName(), id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsById(ID id) {
		log.debug("Checking existence of entity of type {} with ID: {}.", entityClass.getSimpleName(), id);
		boolean exists = repository.existsById(id);
		if (exists) {
			log.debug("Entity of type {} with ID: {} exists.", entityClass.getSimpleName(), id);
		} else {
			log.debug("Entity of type {} with ID: {} does not exist.", entityClass.getSimpleName(), id);
		}
		return exists;
	}
}
