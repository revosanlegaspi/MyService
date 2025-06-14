/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.controller
 * File: ProductController.java
 *
 * Description:
 * This class serves as the REST API controller for Product-related operations.
 * It handles incoming HTTP requests, orchestrates calls to the ProductService,
 * and constructs appropriate HTTP responses. It exposes endpoints for
 * Create, Read, Update, and Delete (CRUD) operations on products, adhering
 * to RESTful principles. It now uses ProductRequest DTO for input validation
 * and relies on a global exception handler for error management.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the ProductController with CRUD endpoints.
 * V1.1.0 - Refactored to extend MainService for generic CRUD functionality.
 * V1.2.0 - Updated to use ProductRequest DTO for input validation with @Valid.
 */
package com.revosan.myapp.myservice.controller;

import com.revosan.myapp.myservice.dto.ProductRequest;
import com.revosan.myapp.myservice.model.Product;
import com.revosan.myapp.myservice.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * GET /api/products : Retrieves all products.
	 * 
	 * @return ResponseEntity with a list of products and HTTP status OK.
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.findAll();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	/**
	 * GET /api/products/{id} : Retrieves a product by ID.
	 * 
	 * @param id The ID of the product.
	 * @return ResponseEntity with the product and HTTP status OK if found, or HTTP
	 *         status NOT_FOUND if not found.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productService.findById(id).map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * POST /api/products : Creates a new product. Uses @Valid to trigger validation
	 * on the ProductRequest DTO.
	 * 
	 * @param productRequest The product data from the request body, subject to
	 *                       validation.
	 * @return ResponseEntity with the created product and HTTP status CREATED.
	 */
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
		// Map DTO to Entity before passing to service layer
		Product productToCreate = new Product(null, productRequest.getName(), productRequest.getDescription(),
				productRequest.getPrice(), productRequest.getQuantity());
		Product createdProduct = productService.save(productToCreate);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	/**
	 * PUT /api/products/{id} : Updates an existing product. Uses @Valid to trigger
	 * validation on the ProductRequest DTO.
	 * 
	 * @param id             The ID of the product to update.
	 * @param productRequest The updated product data from the request body, subject
	 *                       to validation.
	 * @return An Optional containing the updated product if found, or empty if not.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest productRequest) {
		return productService.findById(id).map(existingProduct -> {
			existingProduct.setName(productRequest.getName());
			existingProduct.setDescription(productRequest.getDescription());
			existingProduct.setPrice(productRequest.getPrice());
			existingProduct.setQuantity(productRequest.getQuantity());
			Product updatedProduct = productService.save(existingProduct);
			return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /api/products/{id} : Deletes a product by ID.
	 * 
	 * @param id The ID of the product to delete.
	 * @return ResponseEntity with HTTP status NO_CONTENT if successful, or HTTP
	 *         status NOT_FOUND if the product does not exist.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
		if (productService.existsById(id)) {
			productService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}