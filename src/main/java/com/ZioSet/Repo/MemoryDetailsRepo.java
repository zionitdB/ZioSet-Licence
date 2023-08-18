package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.DiskDetials;
import com.ZioSet.model.MemoryDetails;

public interface MemoryDetailsRepo extends JpaRepository<MemoryDetails, Integer>{
	@Query("From MemoryDetails d where d.serialNo=?1")
	Optional<MemoryDetails> getMemoryDetailsBySerialNo(String serialNo);
	@Query("select count(d) From MemoryDetails d where d.physicalTotalInstalled=?1")
	int getCountOFMemoryDetialsByPhysicalName(String name);

}
