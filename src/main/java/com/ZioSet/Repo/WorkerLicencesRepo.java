package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.WorkerLicences;

public interface WorkerLicencesRepo extends JpaRepository<WorkerLicences, Integer>,WorkerLicencesCustomeRepo{
	@Query("select MAX(w.serialKey) From WorkerLicences w ")
	String  getLastInserted();
	@Query("From WorkerLicences w where w.licenceKey=?1")
	Optional<WorkerLicences> getWorkerLicencesByKey(String licenceKey);
	@Query("select count(*) From WorkerLicences w where w.serialNo!='' OR w.serialNo!=null ")
	int getRegisterWorkerLicencesCount();

	
}
