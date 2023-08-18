package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.LicenceLifeNotification;

public interface LicenceLifeNotificationRepo extends JpaRepository<LicenceLifeNotification, Integer>{
	@Query("From LicenceLifeNotification l where l.type=?1 and l.active=1")
	Optional<LicenceLifeNotification> getActiveLicenceLifeNotificationByType(String type);
	@Query("From LicenceLifeNotification l where l.type=?1")
	List<LicenceLifeNotification> getAllLicenceLifeNotificationByType(String type);

}
