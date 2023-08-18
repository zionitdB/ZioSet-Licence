package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.BundleApplications;

public interface BundleApplicationsRepo extends JpaRepository<BundleApplications, Integer>,BundleApplicationsCustomeRepo{
	@Query("From BundleApplications b where b.bundle.bundleId=?1")
	List<BundleApplications> getApplicationByBundleId(int bundleId);


}
