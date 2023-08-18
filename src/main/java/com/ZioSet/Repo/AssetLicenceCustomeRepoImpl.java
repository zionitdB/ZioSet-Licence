package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Licence;

public class AssetLicenceCustomeRepoImpl implements AssetLicenceCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<AssetLicence> getAssetLicenceLincenceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try{
		long result = (long) entityManager.createQuery("SELECT count(a) FROM AssetLicence a").getSingleResult();
		int total_count = (int) result;
		Query q = entityManager.createQuery("from AssetLicence a ", AssetLicence.class);
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
		
		System.out.println("Max "+maxResult);
		List<AssetLicence> list = q.getResultList();
		return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public List<AssetLicence> getAssetLicenceLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			long result = (long) entityManager
					.createQuery(
							"SELECT count(l) FROM AssetLicence l where  l.category LIKE :searchText OR  l.type LIKE :searchText OR l.name LIKE :searchText OR l.version LIKE :searchText OR l.brand LIKE :searchText OR l.supplier LIKE :searchText OR l.period LIKE :searchText OR l.status LIKE :searchText OR l.cost LIKE :searchText OR l.licenceKey LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"from AssetLicence l where    l.category LIKE :searchText OR  l.type LIKE :searchText OR l.name LIKE :searchText OR l.version LIKE :searchText OR l.brand LIKE :searchText OR l.supplier LIKE :searchText OR l.period LIKE :searchText OR l.status LIKE :searchText OR l.cost LIKE :searchText OR l.licenceKey LIKE :searchText",
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
			List<AssetLicence> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getAssetLicenceLicenceCount() {
		// TODO Auto-generated method stub

		long result = (long) entityManager.createQuery("SELECT count(a) FROM AssetLicence a").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getAssetLicenceLicenceCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return 0;
	}

}
