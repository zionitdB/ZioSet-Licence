package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Software;

public interface SoftwareRepo extends JpaRepository<Software, Integer>,SoftwareCustomeRepo{
	@Query("select MAX(detectedDate) from Software s")
	Date getLatestDate();
	@Query("select MAX(detectedDate) from Software s where Date(s.detectedDate)<?1")
	Date getSecondDate(Date latestDate);
	@Query("select count(*) from Software s where Date(s.detectedDate)=?1")
	int getFetchCountByDate(Date latestDate);
	@Query("select count (distinct s.asset.Id) from Software s where Date(s.detectedDate)=?1")
	int getFetchUniqueCountByDate(Date latestDate);
	@Query("from Software s where s.asset.Id=?1 and s.applicationName=?2 and s.version=?3 and Date(s.detectedDate)=?4")

	Optional<Software> checkSoftwareIsAvailable(int id, String applicationName, String version, Date detectedDate);
	@Query(" from Software s where Date(s.detectedDate)=?1")
	List<Software> getInstallLiceneceByDate(Date date);
	@Query("from Software s where s.serialNo=?1 and Date(s.detectedDate)<=?2 and Date(s.detectedDate)>=?3")

	List<Software> getAllSoftwareBySerialNoAndBeetweenDate(String serialNo, Date today, Date nextDate);
	@Query("from Software s where s.asset.Id=?1 and Date(s.detectedDate)=?2")

	List<Software> chekAssetFetchOnLastCycle(Integer id, Date latestDate);
	
	@Query("from Software s where  s.asset.Id=?1 and Date(s.detectedDate)<=?3 and Date(s.detectedDate)>=?2")
	List<Software> chekAssetFetchOnINDateRange(Integer id, Date fromdate, Date todate);
	

}
