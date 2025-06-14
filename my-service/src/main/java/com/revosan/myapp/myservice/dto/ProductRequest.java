/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.dto
 * File: ProductRequest.java
 *
 * Description:
 * This DTO represents the data structure for incoming Product requests (e.g., for creation or update).
 * It uses JSR 380 Bean Validation annotations to ensure that client-provided data
 * adheres to specific constraints before being processed by the service layer.
 * This enhances data integrity and API robustness.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the ProductRequest DTO with validation annotations.
 */
package com.revosan.myapp.myservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	@NotBlank(message = "Product name cannot be empty")
	@Size(max = 255, message = "Product name cannot exceed 255 characters")
	private String name;

	@Size(max = 1000, message = "Product description cannot exceed 1000 characters")
	private String description;

	@DecimalMin(value = "0.01", message = "Product price must be greater than 0")
	private double price;

	@Min(value = 0, message = "Product quantity cannot be negative")
	private int quantity;
}