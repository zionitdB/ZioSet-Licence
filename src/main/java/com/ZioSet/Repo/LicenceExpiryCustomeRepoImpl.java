package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.CustomerSuppliedSoftware;
import com.ZioSet.model.LicenceExpriry;

public class LicenceExpiryCustomeRepoImpl implements LicenceExpiryCustomeRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	
	@Override
	public List<LicenceExpriry> getAvailanleLicenceExpiryPagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try{
			long result = (long) entityManager.createQuery("SELECT count(c) FROM LicenceExpriry c").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery("from LicenceExpriry c ", LicenceExpriry.class);
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
			List<LicenceExpriry> list = q.getResultList();
			return list;
			} finally {
				// TODO: handle finally clause
				entityManager.close();
			}
	}

	@Override
	public List<LicenceExpriry> getAvailanleLicenceExpirySearchPagination(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			long result = (long) entityManager
					.createQuery(
							"SELECT count(l) FROM LicenceExpriry l where  l.publisherName LIKE :searchText OR  l.productName LIKE :searchText OR l.releaseName LIKE :searchText OR l.version LIKE :searchText OR l.edition LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"FROM LicenceExpriry l where  l.publisherName LIKE :searchText OR  l.productName LIKE :searchText OR l.releaseName LIKE :searchText OR l.version LIKE :searchText OR l.edition LIKE :searchText",
					LicenceExpriry.class);
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
			q.setParameter("searchText", "%" + searchText + "%");
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxResult);
			List<LicenceExpriry> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getAllCountAvailanleLicenceExpiry() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(c) FROM LicenceExpriry c").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getSearchCountAvailanleLicenceExpiry(String searchText) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(l) FROM LicenceExpriry l where  l.publisherName LIKE :searchText OR  l.productName LIKE :searchText OR l.releaseName LIKE :searchText OR l.version LIKE :searchText OR l.edition LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

}
