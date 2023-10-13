package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa
.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.ProductPortal;

public interface ProductPortalRepo  extends JpaRepository<ProductPortal, Integer>{
	@Query("From ProductPortal p where p.publisher.id=?1")
	List<ProductPortal> getProductsByPublisher(int publisherId);
	@Query("From ProductPortal p where p.productName=?1")
	Optional<ProductPortal> getByName(String productName);

}
