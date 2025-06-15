package com.revosan.myapp.myservice.service.impl;

import com.revosan.myapp.myservice.dto.ProductUpdateRequest;
import com.revosan.myapp.myservice.model.Product;
import com.revosan.myapp.myservice.repository.ProductRepository;
import com.revosan.myapp.myservice.service.AbstractCrudService;
import com.revosan.myapp.myservice.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.service.impl File:
 * ProductServiceImpl.java
 *
 * Description: This class provides the concrete implementation for the
 * ProductService interface. It extends the AbstractCrudService abstract class
 * to inherit common CRUD functionalities, thereby reducing boilerplate. It
 * implements Product-specific business logic, such as partial updates, and
 * interacts with the ProductRepository. Transactional annotations are managed
 * by the AbstractCrudService or overridden as needed. Logging is integrated to
 * provide visibility into business operations.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.1
 *
 * Change Log: V1.0.0 - Initial creation implementing ProductService and
 * extending AbstractCrudService. Contains Product-specific business logic.
 * V1.0.1 - Implemented best practice logging for ProductService-specific
 * methods (partialUpdate).
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractCrudService<Product, Long> implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl(ProductRepository productRepository) {
		super(productRepository, Product.class);
	}

	/**
	 * Partially updates an existing product based on provided fields in the DTO.
	 * This method fetches the entity, applies conditional updates, and saves it. It
	 * operates within the transactional context provided by the
	 * class-level @Transactional.
	 *
	 * @param id            The ID of the product to partially update.
	 * @param updateRequest The DTO containing the fields to update.
	 * @return An Optional containing the updated Product if found and modified, or
	 *         an empty Optional if not found.
	 */
	@Override
	public Optional<Product> partialUpdate(Long id, ProductUpdateRequest updateRequest) {
		log.info("Attempting to partially update product with ID: {}", id);

		return findById(id).map(existingProduct -> {
			log.debug("Product with ID: {} found for partial update. Applying changes.", id);

			if (updateRequest.getName() != null) {
				existingProduct.setName(updateRequest.getName());
				log.debug("Updated name for product {}.", id);
			}
			if (updateRequest.getDescription() != null) {
				existingProduct.setDescription(updateRequest.getDescription());
				log.debug("Updated description for product {}.", id);
			}
			if (updateRequest.getPrice() != null) {
				existingProduct.setPrice(updateRequest.getPrice());
				log.debug("Updated price for product {}.", id);
			}
			if (updateRequest.getQuantity() != null) {
				existingProduct.setQuantity(updateRequest.getQuantity());
				log.debug("Updated quantity for product {}.", id);
			}

			Product updatedProduct = save(existingProduct);
			log.info("Product with ID: {} successfully partially updated.", updatedProduct.getId());
			return updatedProduct;
		}).or(() -> {
			log.warn("Product with ID: {} not found for partial update. No update performed.", id);
			return Optional.empty();
		});
	}

}