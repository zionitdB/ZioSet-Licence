package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.AssetTransaction;


public interface AssetTransactionRepo extends JpaRepository<AssetTransaction, Integer>{
	@Query("from AssetTransaction a where a.asset.Id=?1")
	List<AssetTransaction> getAllocationHistoryByAssetId(int id);
	//@Query("from AssetTransaction a where    a.transactionDate BETWEEN ?1 and ?2")
	//@Query(value="select * From asset_transaction_tr q where  q.transaction_date BETWEEN ?1 and ?2",nativeQuery = true)
	@Query("From AssetTransaction a where a.transactionDate between ?1 and ?2 ")

	List<AssetTransaction> getTransactionByDate(Date fromDate,Date toDate);

}
