package com.revosan.myapp.myservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revosan.myapp.myservice.dto.ProductRequest;
import com.revosan.myapp.myservice.dto.ProductResponse;
import com.revosan.myapp.myservice.dto.ProductUpdateRequest;
import com.revosan.myapp.myservice.model.Product;
import com.revosan.myapp.myservice.service.ProductService;

import jakarta.validation.Valid;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.controller File:
 * ProductController.java
 *
 * Description: This class serves as the REST API controller for Product-related
 * operations. It handles incoming HTTP requests, orchestrates calls to the
 * ProductService, and constructs appropriate HTTP responses. It exposes
 * endpoints for Create, Read, Update, and Delete (CRUD) operations on products,
 * adhering to RESTful principles. It uses ProductRequest DTO for input
 * validation and now uses ProductResponse DTOs for consistent output, enhancing
 * decoupling and control over the API contract.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.2
 *
 * Change Log: V1.0.0 - Initial creation of the ProductController with CRUD
 * endpoints.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * GET /api/products : Retrieves all products.
	 *
	 * @return ResponseEntity with a list of ProductResponse DTOs and HTTP status OK
	 *         (200).
	 */
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<Product> products = productService.findAll();
		List<ProductResponse> productResponses = products.stream().map(ProductResponse::fromEntity)
				.collect(Collectors.toList());
		return ResponseEntity.ok(productResponses);
	}

	/**
	 * GET /api/products/{id} : Retrieves a product by ID.
	 *
	 * @param id The ID of the product.
	 * @return ResponseEntity with the ProductResponse DTO and HTTP status OK (200)
	 *         if found, or HTTP status NOT_FOUND (404) if not found.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		return productService.findById(id).map(ProductResponse::fromEntity).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * POST /api/products : Creates a new product. Uses @Valid to trigger validation
	 * on the ProductRequest DTO.
	 *
	 * @param productRequest The product data from the request body, subject to
	 *                       validation.
	 * @return ResponseEntity with the created ProductResponse DTO and HTTP status
	 *         CREATED (201).
	 */
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
		Product productToSave = Product.builder().name(productRequest.getName())
				.description(productRequest.getDescription()).price(productRequest.getPrice())
				.quantity(productRequest.getQuantity()).build();
		Product createdProduct = productService.save(productToSave);
		ProductResponse productResponse = ProductResponse.fromEntity(createdProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
	}

	/**
	 * PUT /api/products/{id} : Updates an existing product. Uses @Valid to trigger
	 * validation on the ProductRequest DTO.
	 *
	 * @param id             The ID of the product to update.
	 * @param productRequest The updated product data from the request body, subject
	 *                       to validation.
	 * @return ResponseEntity with the updated ProductResponse DTO and HTTP status
	 *         OK (200) if found, or HTTP status NOT_FOUND (404) if the product does
	 *         not exist.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest productRequest) {
		return productService.findById(id).map(existingProduct -> {
			existingProduct.setName(productRequest.getName());
			existingProduct.setDescription(productRequest.getDescription());
			existingProduct.setPrice(productRequest.getPrice());
			existingProduct.setQuantity(productRequest.getQuantity());

			Product updatedProduct = productService.save(existingProduct);
			ProductResponse productResponse = ProductResponse.fromEntity(updatedProduct);
			return ResponseEntity.ok(productResponse);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * PATCH /api/products/{id} : Partially updates an existing product. Uses @Valid
	 * to trigger validation on the ProductUpdateRequest DTO (only for provided
	 * fields).
	 *
	 * @param id                   The ID of the product to partially update.
	 * @param productUpdateRequest The partial product data from the request body.
	 * @return ResponseEntity with the updated ProductResponse DTO and HTTP status
	 *         OK (200) if found, or HTTP status NOT_FOUND (404) if the product does
	 *         not exist.
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<ProductResponse> partialUpdateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
		return productService.findById(id).map(existingProduct -> {
			if (productUpdateRequest.getName() != null) {
				existingProduct.setName(productUpdateRequest.getName());
			}
			if (productUpdateRequest.getDescription() != null) {
				existingProduct.setDescription(productUpdateRequest.getDescription());
			}
			if (productUpdateRequest.getPrice() != null) {
				existingProduct.setPrice(productUpdateRequest.getPrice());
			}
			if (productUpdateRequest.getQuantity() != null) {
				existingProduct.setQuantity(productUpdateRequest.getQuantity());
			}

			Product updatedProduct = productService.save(existingProduct);
			ProductResponse updatedProductResponse = ProductResponse.fromEntity(updatedProduct);
			return ResponseEntity.ok(updatedProductResponse);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * DELETE /api/products/{id} : Deletes a product by ID.
	 *
	 * @param id The ID of the product to delete.
	 * @return ResponseEntity with HTTP status NO_CONTENT (204) if successful, or
	 *         HTTP status NOT_FOUND (404) if the product does not exist.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		if (productService.existsById(id)) {
			productService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
