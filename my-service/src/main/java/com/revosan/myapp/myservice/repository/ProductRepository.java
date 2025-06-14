/**
 * Project: MyService
 * Package: com.revosan.myapp.myservice.repository
 * File: ProductRepository.java
 *
 * Description:
 * This interface serves as the data access layer for the Product entity.
 * By extending JpaRepository, it automatically inherits standard CRUD operations
 * and provides a robust foundation for database interactions, simplifying
 * persistence logic significantly. Custom query methods can also be defined here.
 *
 * Author: Revosan A. Legaspi
 * Date: June 14, 2025
 * Version: 1.0.0
 *
 * Change Log:
 * V1.0.0 - Initial creation of the ProductRepository interface.
 */
package com.revosan.myapp.myservice.repository;

import com.revosan.myapp.myservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByName(String name);
}