package com.revosan.myapp.myservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.dto File:
 * ProductUpdateRequest.java
 *
 * Description: This DTO is specifically designed for handling partial updates
 * (HTTP PATCH requests) for a Product. All fields are optional, allowing
 * clients to send only the fields they intend to modify. Validation constraints
 * are still applied where applicable to the provided values.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.0
 *
 * Change Log: V1.0.0 - Initial creation of ProductUpdateRequest DTO for PATCH
 * operations. Fields are optional (no @NotNull), but value-based validations
 * (e.g., @DecimalMin) remain.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

	@Size(max = 255, message = "Product name cannot exceed 255 characters")
	private String name;

	@Size(max = 1000, message = "Product description cannot exceed 1000 characters")
	private String description;

	@DecimalMin(value = "0.01", message = "Product price must be greater than 0")
	private BigDecimal price;

	@Min(value = 0, message = "Product quantity cannot be negative")
	private Integer quantity;

}
