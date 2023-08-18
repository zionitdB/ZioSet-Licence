package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Permissions;

public class PermissionsCustomeRepoImpl implements PermissionsCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Permissions> getPermissionsByPagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(p) FROM Permissions p").getSingleResult();
		int total_count=(int) result;
		Query q = entityManager.createQuery("from Permissions", Permissions.class);
		int firstR = total_count - (page_no * item_per_page);
		int maxR = total_count - ((page_no - 1) * item_per_page);
		

		if(firstR<0) {
			firstR=0;
		}
	
		q.setFirstResult(firstR); // modify this to adjust paging
		q.setMaxResults(maxR);
		
		List<Permissions> list = q.getResultList();
		return list;
		
	
		
	}

	@Override
	public List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search) {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(p) from Permissions p  where p.permissionsName LIKE :searchText").setParameter("searchText", "%"+search+"%").getSingleResult();

		int total_count=(int) result;
		Query q = entityManager.createQuery("from Permissions p  where p.permissionsName LIKE :searchText", Permissions.class);
		int firstR = total_count - (page_no * item_per_page);
		int maxR = total_count - ((page_no - 1) * item_per_page);
		

		if(firstR<0) {
			firstR=0;
		}
		q.setParameter("searchText", "%"+search+"%");
		q.setFirstResult(firstR); // modify this to adjust paging
		q.setMaxResults(maxR);
		
		List<Permissions> list = q.getResultList();
		return list;
		
	}

	@Override
	public int getPermissionsCount() {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(p) FROM Permissions p").getSingleResult();
		int total_count=(int) result;
		return total_count;
	}

	@Override
	public int getPermissionsCountBySearch(String search) {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery("SELECT count(p) from Permissions p  where p.permissionsName LIKE :searchText").setParameter("searchText", "%"+search+"%").getSingleResult();

		int total_count=(int) result;
		return total_count;
	}

}
