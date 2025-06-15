package com.revosan.myapp.myservice.model;

/**
 * Project: MyService Package: com.revosan.myapp.myservice.model File:
 * Product.java
 *
 * Description: This class represents the Product entity, mapping to the
 * 'products' table in the database. It encapsulates product-related data and
 * uses JPA annotations for ORM. Lombok annotations are used selectively to
 * reduce boilerplate code for getters, setters, and constructors, adhering to
 * best practices for JPA entities. Includes audit fields for creation and
 * update timestamps.
 *
 * Author: Revosan A. Legaspi Date: June 15, 2025 Version: 1.0.0
 *
 * Change Log: V1.0.0 - Initial creation of the Product entity. V1.0.1 -
 * Optimized Lombok usage for JPA entities.
 */

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "id" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, unique = true, length = 100)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "updated_at")
	private Instant updatedAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = Instant.now();
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
	}

}