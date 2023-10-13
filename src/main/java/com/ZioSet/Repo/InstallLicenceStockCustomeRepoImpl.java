package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Asset;
import com.ZioSet.model.InstallLicenceStock;

public class InstallLicenceStockCustomeRepoImpl implements InstallLicenceStockCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<InstallLicenceStock> getSystemLincencceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try{
			long result = 0 ;
			Query q = null ;
			 result = (long) entityManager.createQuery("SELECT count(i) FROM InstallLicenceStock i").getSingleResult();
			 q = entityManager.createQuery("from InstallLicenceStock i ", InstallLicenceStock.class);

			
			int total_count = (int) result;
			int firstR = total_count - (page_no * item_per_page);
			int maxR = total_count - ((page_no - 1) * item_per_page);
			if (firstR < 0) {
				firstR = 0;
			}
			int maxResult;
			if(maxR<10){
				maxResult=maxR;
			}else{
				maxResult=item_per_page;
			}
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxResult);
			
			List<InstallLicenceStock> list = q.getResultList();
			return list;
		
		
				} finally {
					// TODO: handle finally clause
					entityManager.close();
				}
	}

	@Override
	public List<InstallLicenceStock> getSystemLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try{
		
		Query q = null;
		
		
		long result = (long) entityManager
				.createQuery(
						"SELECT count(i) FROM InstallLicenceStock i where  i.computerName LIKE :searchText OR  i.product.productName LIKE :searchText OR i.associate.associateName LIKE :searchText OR i.asset.serialNo LIKE :searchText OR i.asset.assetId LIKE :searchText OR i.asset.make LIKE :searchText OR i.asset.model LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		 q = entityManager.createQuery(
				"from InstallLicenceStock i where  i.computerName LIKE :searchText OR  i.product.productName LIKE :searchText OR i.associate.associateName LIKE :searchText OR i.asset.serialNo LIKE :searchText OR i.asset.assetId LIKE :searchText OR i.asset.make LIKE :searchText OR i.asset.model LIKE :searchText",
				InstallLicenceStock.class);
			
			
			int total_count = (int) result;
			
			int firstR = total_count - (pageNo * perPage);
			int maxR = total_count - ((pageNo - 1) * perPage);
			if (firstR < 0) {
				firstR = 0;
			}
			int maxResult;
			if(maxR<10){
				maxResult=maxR;
			}else{
				maxResult=perPage;
			}
			q.setMaxResults(maxResult);
			
			q.setParameter("searchText", "%" + searchText + "%");
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxResult);
			List<InstallLicenceStock> list = q.getResultList();
			return list;
		
		
		
	} finally {
		// TODO: handle finally clause
		entityManager.close();
	}
	}

	@Override
	public int getSystemLicenceCount() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(i) FROM InstallLicenceStock i").getSingleResult();

		return (int) result;
	}

	@Override
	public int getSystemLicenceCount(String searchText) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(i) FROM InstallLicenceStock i where  i.computerName LIKE :searchText OR  i.product.productName LIKE :searchText OR i.associate.associateName LIKE :searchText OR i.asset.serialNo LIKE :searchText OR i.asset.assetId LIKE :searchText OR i.asset.make LIKE :searchText OR i.asset.model LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		return (int) result;
	}

	@Override
	public List<InstallLicenceStock> getListOfLicencesByProductName(String string) {
		// TODO Auto-generated method stub
		Query q = null;
		q = entityManager.createQuery(
					"from InstallLicenceStock i where  i.product.productName LIKE :searchText",
					InstallLicenceStock.class);
		
		q.setParameter("searchText", "%" + string + "%");
	
		List<InstallLicenceStock> list = q.getResultList();
		return list;	}

	@Override
	public int getCountByEditionAndProductName(String name, String string) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(i) FROM InstallLicenceStock i where    i.product.productName LIKE :searchText  AND i.edition =:edition")
				.setParameter("searchText", "%" + string + "%").setParameter("edition",name).getSingleResult();
		return (int) result;
	}

}
