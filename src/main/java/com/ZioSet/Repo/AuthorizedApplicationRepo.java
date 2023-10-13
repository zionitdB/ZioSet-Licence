package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.AuthorizedApplication;


public interface AuthorizedApplicationRepo extends JpaRepository<AuthorizedApplication, Integer> {
	@Query("From AuthorizedApplication a where a.product.id=?1")
	Optional<AuthorizedApplication> findByProductId(int id);
	@Query("From AuthorizedApplication a where a.product.productName=?1")
	Optional<AuthorizedApplication> findByname(String applicationName);

}
