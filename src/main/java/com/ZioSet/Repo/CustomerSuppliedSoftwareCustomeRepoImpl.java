package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.CustomerSuppliedSoftware;

public class CustomerSuppliedSoftwareCustomeRepoImpl implements CustomerSuppliedSoftwareCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimit(int page_no, int item_per_page) {
		try{
			long result = (long) entityManager.createQuery("SELECT count(c) FROM CustomerSuppliedSoftware c").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery("from CustomerSuppliedSoftware c ", CustomerSuppliedSoftware.class);
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
			List<CustomerSuppliedSoftware> list = q.getResultList();
			return list;
			} finally {
				// TODO: handle finally clause
				entityManager.close();
			}
	}

	@Override
	public List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimitAndSearch(String searchText, int pageNo,
			int perPage) {
		try {
			// TODO Auto-generated method stub
			long result = (long) entityManager
					.createQuery(
							"SELECT count(c) FROM CustomerSuppliedSoftware c where  c.formSrNo LIKE :searchText OR  l.assetTagNo LIKE :searchText OR l.title LIKE :searchText OR l.version LIKE :searchText OR l.language LIKE :searchText OR l.remark LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"FROM CustomerSuppliedSoftware c where  c.formSrNo LIKE :searchText OR  l.assetTagNo LIKE :searchText OR l.title LIKE :searchText OR l.version LIKE :searchText OR l.language LIKE :searchText OR l.remark LIKE :searchText",
					AssetLicence.class);
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
			List<CustomerSuppliedSoftware> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCustomerSuppliedSoftwareCount() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(c) FROM CustomerSuppliedSoftware c").getSingleResult();
		int total_count = (int) result;
		
		System.out.println("TOTOAL ................."+total_count);
		return total_count;
	}
	@Override
	public int getCustomerSuppliedSoftwareTotalCount() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT sum(c.licenceCount) FROM CustomerSuppliedSoftware c").getSingleResult();
		int total_count = (int) result;
		
		System.out.println("TOTOAL ................."+total_count);
		return total_count;
	}

	@Override
	public int getCustomerSuppliedSoftwareCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(c) FROM CustomerSuppliedSoftware c where  c.formSrNo LIKE :searchText OR  l.assetTagNo LIKE :searchText OR l.title LIKE :searchText OR l.version LIKE :searchText OR l.language LIKE :searchText OR l.remark LIKE :searchText")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

}
