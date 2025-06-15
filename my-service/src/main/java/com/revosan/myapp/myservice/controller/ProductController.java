package com.revosan.myapp.myservice.controller;

import com.revosan.myapp.myservice.dto.ProductRequest;
import com.revosan.myapp.myservice.dto.ProductResponse;
import com.revosan.myapp.myservice.dto.ProductUpdateRequest;
import com.revosan.myapp.myservice.model.Product;
import com.revosan.myapp.myservice.service.ProductService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Project: MyService Package: com.revosan.myapp.myservice.controller File:
 * ProductController.java
 *
 * Description: This class serves as the REST API controller for Product-related
 * operations. It handles incoming HTTP requests, orchestrates calls to the
 * ProductService, and constructs appropriate HTTP responses. It exposes
 * endpoints for Create, Read, Update (Full/Partial), and Delete (CRUD)
 * operations on products, adhering to RESTful principles. It uses
 * ProductRequest DTO for input validation and ProductResponse DTOs for
 * consistent output, enhancing decoupling and control over the API contract.
 * Logging is integrated to provide visibility into API interactions.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.4
 *
 * Change Log: V1.0.0 - Initial creation of the ProductController with CRUD
 * endpoints. V1.1.0 - Refactored to extend MainService for generic CRUD
 * functionality (Note: This controller doesn't extend now). V1.2.0 - Updated to
 * use ProductRequest DTO for input validation with @Valid. V1.0.1 - Optimized:
 * Removed redundant @Autowired, used explicit Product mapping, refined
 * ResponseEntity responses, and adjusted deleteProduct return type. V1.0.2 -
 * Further Optimized: Introduced ProductResponse DTO for all outgoing product
 * representations, ensuring decoupling and consistent API contracts. Applied
 * mapping from Entity to Response DTO. V1.0.3 - Added PATCH endpoint for
 * partial updates using ProductUpdateRequest DTO. V1.0.4 - Implemented best
 * practice logging for all controller endpoints.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

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
		log.info("Received request to retrieve all products.");
		List<Product> products = productService.findAll();
		List<ProductResponse> productResponses = products.stream().map(ProductResponse::fromEntity)
				.collect(Collectors.toList());
		log.info("Successfully retrieved {} products.", productResponses.size());
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
		log.info("Received request to retrieve product with ID: {}", id);
		return productService.findById(id).map(product -> {
			log.info("Product with ID: {} found.", id);
			return ResponseEntity.ok(ProductResponse.fromEntity(product));
		}).orElseGet(() -> {
			log.warn("Product with ID: {} not found.", id);
			return ResponseEntity.notFound().build();
		});
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
		log.info("Received request to create product: {}", productRequest.getName());

		Product productToSave = Product.builder().name(productRequest.getName())
				.description(productRequest.getDescription()).price(productRequest.getPrice())
				.quantity(productRequest.getQuantity()).build();

		Product createdProduct = productService.save(productToSave);
		ProductResponse productResponse = ProductResponse.fromEntity(createdProduct);

		log.info("Product created successfully with ID: {}", createdProduct.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
	}

	/**
	 * PUT /api/products/{id} : Updates an existing product (full replacement).
	 * Uses @Valid to trigger validation on the ProductRequest DTO.
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
		log.info("Received request to fully update product with ID: {}", id);

		return productService.findById(id).map(existingProduct -> {
			existingProduct.setName(productRequest.getName());
			existingProduct.setDescription(productRequest.getDescription());
			existingProduct.setPrice(productRequest.getPrice());
			existingProduct.setQuantity(productRequest.getQuantity());

			Product updatedProduct = productService.save(existingProduct);
			ProductResponse productResponse = ProductResponse.fromEntity(updatedProduct);
			log.info("Product with ID: {} updated successfully (full replacement).", updatedProduct.getId());
			return ResponseEntity.ok(productResponse);
		}).orElseGet(() -> {
			log.warn("Product with ID: {} not found for full update.", id);
			return ResponseEntity.notFound().build();
		});
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
		log.info("Received request to partially update product with ID: {}", id);

		return productService.partialUpdate(id, productUpdateRequest).map(product -> {
			log.info("Product with ID: {} partially updated successfully.", product.getId());
			return ResponseEntity.ok(ProductResponse.fromEntity(product));
		}).orElseGet(() -> {
			log.warn("Product with ID: {} not found for partial update.", id);
			return ResponseEntity.notFound().build();
		});
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
		log.info("Received request to delete product with ID: {}", id);
		if (productService.existsById(id)) {
			productService.deleteById(id);
			log.info("Product with ID: {} deleted successfully.", id);
			return ResponseEntity.noContent().build();
		} else {
			log.warn("Product with ID: {} not found for deletion.", id);
			return ResponseEntity.notFound().build();
		}
	}
}
