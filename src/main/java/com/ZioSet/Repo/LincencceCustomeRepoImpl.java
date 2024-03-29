package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Licence;

public class LincencceCustomeRepoImpl implements LincencceCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<Licence> getLincencceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try {
			long result = (long) entityManager.createQuery("SELECT count(l) FROM Licence l").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery("from Licence l ", Licence.class);
			int firstR = total_count - (page_no * item_per_page);
			int maxR = total_count - ((page_no - 1) * item_per_page);
			if (firstR < 0) {
				firstR = 0;
			}
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxR);
			List<Licence> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public List<Licence> getLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			long result = (long) entityManager
					.createQuery(
							"SELECT count(l) FROM Licence l where  l.licenceType LIKE :searchText ")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"from Licence l where  l.category LIKE :searchText ",
					Licence.class);
			int firstR = total_count - (pageNo * perPage);
			int maxR = total_count - ((pageNo - 1) * perPage);
			if (firstR < 0) {
				firstR = 0;
			}
			q.setParameter("searchText", "%" + searchText + "%");
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxR);
			List<Licence> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getLicenceCount() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(l) FROM Licence l").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getLicenceCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(l) FROM Licence l where  l.licenceType LIKE :searchText ")
		
			.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public List<Licence> getExpiringLicencesSAASPagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try {
			Date today = new Date();
			long result = (long) entityManager.createQuery("SELECT count(l) FROM Licence l where l.licenceExpiryDate<:today").setParameter("today", today).getSingleResult();
			
			int total_count = (int) result;
			Query q = entityManager.createQuery("FROM Licence l where l.licenceExpiryDate<:today", Licence.class);
			q.setParameter("today",today);

			int firstR = total_count - (page_no * item_per_page);
			int maxR = total_count - ((page_no - 1) * item_per_page);
			if (firstR < 0) {
				firstR = 0;
			}
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxR);
			List<Licence> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public List<Licence> getExpiringLicencesSAASSearchPagination(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			// TODO Auto-generated method stub
			Date today = new Date();
			long result = (long) entityManager.createQuery("SELECT count(l) FROM Licence l where l.licenceExpiryDate<:today and (l.licenceType LIKE :searchText OR l.projectName LIKE :searchText OR l.licenceVersion LIKE :searchText OR l.associate.associateName LIKE :searchText OR l.product.productName LIKE :searchText)").setParameter("today", today).setParameter("searchText", "%" + searchText + "%").getSingleResult();
				
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"from FROM Licence l where l.licenceExpiryDate<:today and (l.licenceType LIKE :searchText OR l.projectName LIKE :searchText OR l.licenceVersion LIKE :searchText OR l.associate.associateName LIKE :searchText OR l.product.productName LIKE :searchText)",Licence.class);
			int firstR = total_count - (pageNo * perPage);
			int maxR = total_count - ((pageNo - 1) * perPage);
			if (firstR < 0) {
				firstR = 0;
			}
			q.setParameter("today", today);
			q.setParameter("searchText", "%" + searchText + "%");
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxR);
			List<Licence> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getAllCountExpiringLicencesSAAS() {
		// TODO Auto-generated method stub
		Date today = new Date();
		long result = (long) entityManager.createQuery("SELECT count(l) FROM Licence l where l.licenceExpiryDate<:today").setParameter("today", today).getSingleResult();
			int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getSearchCountExpiringLicencesSAAS(String searchText) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(l) FROM Licence l where  l.licenceType LIKE :searchText ")
		
			.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

}
