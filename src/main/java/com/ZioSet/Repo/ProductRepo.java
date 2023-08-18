package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	@Query("From Product p where p.productName=?1")
	Optional<Product> getProductsByName(String productName);

}
