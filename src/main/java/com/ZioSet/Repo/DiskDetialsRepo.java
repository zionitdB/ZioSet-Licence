package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.DiskDetials;

public interface DiskDetialsRepo extends JpaRepository<DiskDetials, Integer> {
	@Query("From DiskDetials d where d.serialNo=?1 and d.name=?2")
	Optional<DiskDetials> getDiskDetialsBySerialNoAndDiskName(String serialNo,String name);
	@Query("From DiskDetials d where d.serialNo=?1")
	List<DiskDetials> getDiskDetialsBySerialNo(String serialNo);

}
