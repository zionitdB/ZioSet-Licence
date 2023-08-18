package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.LicenceExpriry;

public interface LicenceExpriryRepo  extends JpaRepository<LicenceExpriry, Integer>{
	@Query("from LicenceExpriry l where l.publisherName=?1 and l.productName=?2 and l.releaseName=?3")
	Optional<LicenceExpriry> getLicenceExpiryByPublisherProductAndRelease(String publisherName, String productName,
			String releaseName);
	@Query("from LicenceExpriry l where  Date(l.retirementDate)<=?1")
	List<LicenceExpriry> getExpiredLicencesByDate(Date nextDate);

}
