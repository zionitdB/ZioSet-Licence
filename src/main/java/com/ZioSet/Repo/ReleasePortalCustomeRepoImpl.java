package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Asset;
import com.ZioSet.model.Licence;
import com.ZioSet.model.ReleasePortal;

public class ReleasePortalCustomeRepoImpl  implements ReleasePortalCustomeRepo{
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<ReleasePortal> getReleasePortalPagination(int page_no, int item_per_page, int productId) {
		// TODO Auto-generated method stub
		try{
			long result = 0 ;
			Query q = null ;
			 result = (long) entityManager.createQuery("SELECT count(a) FROM ReleasePortal a where  a.product.id =:productId").setParameter("productId",productId).getSingleResult();
			 q = entityManager.createQuery("from ReleasePortal a where a.product.id =:productId ", ReleasePortal.class);

			
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
			q.setParameter("productId",productId);
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxResult);
			
			List<ReleasePortal> list = q.getResultList();
			return list;
		
		
				} finally {
					// TODO: handle finally clause
					entityManager.close();
				}
	}

	@Override
	public List<ReleasePortal> getReleasePortalPaginationAndSearch(String searchText, int pageNo, int perPage,
			int productId) {
		// TODO Auto-generated method stub
		try{
			
			Query q = null;
			
			
			long result = (long) entityManager
					.createQuery(
							"SELECT count(a) FROM ReleasePortal  a where  a.product.id =:productId and (a.edition LIKE :searchText)")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			 q = entityManager.createQuery(
					"from ReleasePortal a where a where  a.product.id =:productId and (a.edition LIKE :searchText) ",
					ReleasePortal.class);
				
				
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
				q.setParameter("productId", productId);
				q.setParameter("searchText", "%" + searchText + "%");
				q.setFirstResult(firstR); // modify this to adjust paging
				q.setMaxResults(maxResult);
				List<ReleasePortal> list = q.getResultList();
				return list;
			
			
			
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getCountReleasePortal(int productId) {
		// TODO Auto-generated method stub
		
		Query q = null ;
		long  result = (long) entityManager.createQuery("SELECT count(a) FROM ReleasePortal a where  a.product.id =:productId").setParameter("productId",productId).getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getCountReleasePortalAndSearch(String searchText, int productId) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(a) FROM ReleasePortal  a where  a.product.id =:productId and (a.edition LIKE :searchText)")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

}
