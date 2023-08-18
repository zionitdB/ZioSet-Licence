package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.CustomerSuppliedSoftware;

public interface CustomerSuppliedSoftwareRepo extends JpaRepository<CustomerSuppliedSoftware, Integer> , CustomerSuppliedSoftwareCustomeRepo{
	@Query("From CustomerSuppliedSoftware c  where c.formSrNo=?1")
	Optional<CustomerSuppliedSoftware> getCustomerSuppliedSoftware(String formSrNo);
	@Query("select sum(c.licenceCount) From CustomerSuppliedSoftware c")
	int getSuppliedSoftwareCount();


}
