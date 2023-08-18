package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.RamDetials;

public interface RamDetialsRepo extends JpaRepository<RamDetials, Integer> {
	@Query("from RamDetials r where r.systemSerialNo=?1 and r.size=?2 and r.serialNo=?3 and r.partNo=?4 and r.manufacture=?5")
	Optional<RamDetials> getRamDetialsBySerialNoAndOther(String systemSerialNo, double size, String serialNo,
			String partNo, String manufacture);

}
