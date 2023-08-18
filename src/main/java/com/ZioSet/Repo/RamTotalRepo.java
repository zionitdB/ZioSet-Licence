package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.RamTotal;

public interface RamTotalRepo extends JpaRepository<RamTotal, Integer> {
	@Query("from RamTotal r where r.asset.id=?1")
	Optional<RamTotal> getByAssetId(int id);
	@Query("select distinct(size) from RamTotal r")
	List<Double> getTotalRams();
	@Query("select count(*) from RamTotal r where r.size=?1")
	int getCountOFRamBySize(double parseDouble);

}
