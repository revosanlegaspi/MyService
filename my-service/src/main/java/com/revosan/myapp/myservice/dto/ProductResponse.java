package com.revosan.myapp.myservice.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;
import com.revosan.myapp.myservice.model.Product;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.dto File:
 * ProductResponse.java
 *
 * Description: This class represents a Data Transfer Object (DTO) for outgoing
 * product responses. It's used to define the structure of product data sent
 * back to clients for read operations. It ensures a clear contract separate
 * from the internal Product entity structure, and controls which fields are
 * exposed.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.0
 *
 * Change Log: V1.0.0 - Initial creation of ProductResponse DTO for read
 * operations. Utilizes @Value for immutability and @Builder for easy
 * construction from Product entities.
 */

@Value
@Builder
@Jacksonized
public class ProductResponse {

	private final Long id;
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final Integer quantity;
	private final Instant updatedAt;
	private final Instant createdAt;

	public static ProductResponse fromEntity(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).quantity(product.getQuantity())
				.createdAt(product.getCreatedAt()).updatedAt(product.getUpdatedAt()).build();
	}
}
