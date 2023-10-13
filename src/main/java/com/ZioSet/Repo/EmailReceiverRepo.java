package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.EmailReceiver;


public interface EmailReceiverRepo  extends JpaRepository<EmailReceiver, Integer>{
	@Query("select count (*) from EmailReceiver e where e.active=1")
	int getActiveRciverCount();
	
	@Query("select count (*) from EmailReceiver e ")
	int getAllEmailReceiverCount();
	
	@Query("from EmailReceiver e where e.active=1")
	List<EmailReceiver> getActiveAllEmailReceiver();
	
	@Query("from EmailReceiver e where e.branch.branchId=1")
	List<EmailReceiver> getPuneAllEmailReceiver();
	
	@Query("from EmailReceiver e where e.branch.branchId=2")
	List<EmailReceiver> getBengaluruAllEmailReceiver();

	
	@Query("select count (*) from EmailReceiver e where e.branch.branchId=1")
	int getPuneAllEmailReceiverCount();
	
	@Query("select count (*) from EmailReceiver e where e.branch.branchId=2")
	int getBengaluruAllEmailReceiverCount();

}
