/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.model
 * File: Product.java
 *
 * Description:
 * This class represents the Product entity, mapping to the 'products' table in the database.
 * It encapsulates product-related data and uses JPA annotations for ORM.
 * Lombok annotations are used to reduce boilerplate code for getters, setters,
 * and constructors, ensuring a clean and concise model.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the Product entity.
 */
package com.revosan.myapp.myservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;
	private double price;
	private int quantity;
}