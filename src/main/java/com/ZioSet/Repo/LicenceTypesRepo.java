package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.LicenceTypes;

public interface LicenceTypesRepo extends JpaRepository<LicenceTypes, Integer> {
	@Query("From LicenceTypes l where l.typeName=?1")
	Optional<LicenceTypes> getLicenceTypeByName(String type);

}
