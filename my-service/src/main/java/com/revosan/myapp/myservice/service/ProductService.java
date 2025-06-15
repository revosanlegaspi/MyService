/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.service
 * File: ProductService.java
 *
 * Description:
 * This class implements the core business logic for Product management, extending
 * the generic MainService to inherit standard CRUD operations. It focuses on
 * specific business rules and operations unique to the Product entity, such as
 * finding a product by its name. It orchestrates calls to the ProductRepository.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the ProductService class with basic CRUD operations.
 */
package com.revosan.myapp.myservice.service;

import com.revosan.myapp.myservice.model.Product;
import com.revosan.myapp.myservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService extends MainService<Product, Long> {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		super(productRepository);
		this.productRepository = productRepository;
	}

	/**
	 * Retrieves a product by its name. This is a Product-specific business method,
	 * not part of generic CRUD.
	 * 
	 * @param name The name of the product.
	 * @return The product found, or null if not found.
	 */
	@Transactional(readOnly = true)
	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

	/**
	 * Note: findAll(), save(), deleteById(), existsById() are inherited from
	 * MainService. They no longer need to be explicitly defined or overridden here
	 * unless specify. Product-related business logic needs to be added before or
	 * after the base operation.
	 */

	/**
	 * Overrides the findById method to demonstrate adding specific logic. If no
	 * specific logic is needed, this could be omitted and the method from
	 * MainService would be used.
	 * 
	 * @param id The ID of the product to retrieve.
	 * @return An Optional containing the product if found, or empty if not.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(Long id) {
		return super.findById(id).map(existingProduct -> {
			return existingProduct;
		});
	}

	/**
	 * Saves a new product or updates an existing one. This method overrides the
	 * base 'save' to demonstrate how you might add product-specific logic before or
	 * after saving.
	 * 
	 * @param product The product object to be created or updated.
	 * @return The created or updated product.
	 */
	@Override
	@Transactional
	public Product save(Product product) {
		if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Product price must be positive.");
		}
		return super.save(product);
	}
}