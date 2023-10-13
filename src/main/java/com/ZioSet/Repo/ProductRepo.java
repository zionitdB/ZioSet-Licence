package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>,ProductCustomeRepo{
	@Query("From Product p where p.productName=?1")
	Optional<Product> getProductsByName(String productName);
	@Query("From Product p where p.productName=?1")
	List<Product> getAllProductByPublisher(int publisherId);
	List<Product> getProductByPagination(int page_no, int item_per_page);
	List<Product> getProductBySearchPagination(String searchText, int pageNo, int perPage);
	int getProductCount();
	int getSearchCountProduct(String searchText);

}
