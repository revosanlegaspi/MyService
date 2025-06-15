package com.revosan.myapp.myservice.service;

import com.revosan.myapp.myservice.dto.ProductUpdateRequest;
import com.revosan.myapp.myservice.model.Product;

import java.util.Optional;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.service File:
 * ProductService.java
 *
 * Description: This interface defines the contract for Product-specific
 * business operations. It extends BaseService to inherit common CRUD methods
 * and adds specialized methods unique to Product management, such as partial
 * updates.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.0
 *
 * Change Log: V1.0.0 - Initial creation, extending BaseService and adding
 * Product-specific methods.
 */

public interface ProductService extends BaseService<Product, Long> {

	/**
	 * Partially updates an existing product based on provided fields in the DTO.
	 * Only non-null fields in the updateRequest will be applied.
	 *
	 * @param id            The ID of the product to partially update.
	 * @param updateRequest The DTO containing the fields to update.
	 * @return An Optional containing the updated Product if found and modified, or
	 *         an empty Optional if not found.
	 */
	Optional<Product> partialUpdate(Long id, ProductUpdateRequest updateRequest);

}
