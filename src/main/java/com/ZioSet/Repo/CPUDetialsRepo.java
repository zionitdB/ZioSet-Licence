package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.CPUDetials;

public interface CPUDetialsRepo extends JpaRepository<CPUDetials, Integer> {
	@Query("From CPUDetials c where c.serialNo=?1")
	Optional<CPUDetials> getCPUDetialBySerialNo(String serialNo);
	@Query("select count(c)From CPUDetials c where c.processorName=?1")
	int getCountOFCPUDetialsByProcessorName(String name);

}
