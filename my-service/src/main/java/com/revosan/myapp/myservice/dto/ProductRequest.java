package com.revosan.myapp.myservice.dto;

/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.dto
 * File: ProductRequest.java
 *
 * Description:
 * This class represents a Data Transfer Object (DTO) for incoming product creation or update requests.
 * It encapsulates the data required from the client.
 * Lombok annotations are used to reduce boilerplate code, and Jakarta Validation annotations
 * are applied to ensure data integrity at the API layer.
 *
 * Author: Revosan A. Legaspi
 * Date: June 15, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the ProductRequest DTO.
 * Implemented with @Data, @Builder, @NoArgsConstructor, @AllArgsConstructor.
 * Added validation constraints using Jakarta Validation API.
 * V1.0.1 - Updated 'price' to BigDecimal for precision and 'quantity' to Integer to allow nullability
 * and align with DTO best practices for wrappers.
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	@NotBlank(message = "Product name cannot be empty")
	@Size(max = 255, message = "Product name cannot exceed 255 characters")
	private String name;

	@Size(max = 1000, message = "Product description cannot exceed 1000 characters")
	private String description;

	@NotNull(message = "Product price is required")
	@DecimalMin(value = "0.01", message = "Product price must be greater than 0")
	private BigDecimal price;

	@NotNull(message = "Product quantity is required")
	@Min(value = 0, message = "Product quantity cannot be negative")
	private Integer quantity;
}
