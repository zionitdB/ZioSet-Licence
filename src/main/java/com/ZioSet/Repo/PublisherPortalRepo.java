package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.PublisherPortal;

public interface PublisherPortalRepo extends JpaRepository<PublisherPortal, Integer> {
	@Query("from PublisherPortal p where p.publisherName=?1")
	Optional<PublisherPortal> getPublisherByName(String publisherName);

}
