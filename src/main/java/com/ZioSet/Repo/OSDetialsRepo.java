package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.OSDetials;

public interface OSDetialsRepo extends JpaRepository<OSDetials, Integer>{
	@Query("from OSDetials o where o.asset.serialNo=?1")
	Optional<OSDetials> getOSDetailsBySerialNo(String serialNo);
	@Query("select count(o) from OSDetials o where o.name=?1")
	 int getCountOFOSDetialsByOSName(String name);

}
