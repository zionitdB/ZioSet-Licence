package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Associate;

public interface AssociateRepo extends JpaRepository<Associate, Integer> {
	@Query("From Associate a where a.associateName=?1")
	Optional<Associate> getAssociateByName(String associateName);

}
