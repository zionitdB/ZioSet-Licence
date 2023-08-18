package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Asset;
import com.ZioSet.model.UserInfo;

public class UserCustomeRepoImpl implements UserCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<UserInfo> getUserByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		try{
			long result = 0 ;
			Query q = null ;
			 result = (long) entityManager.createQuery("SELECT count(u) FROM UserInfo u").getSingleResult();
			 q = entityManager.createQuery("from UserInfo  ", UserInfo.class);

			
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
			
			List<UserInfo> list = q.getResultList();
			return list;
		
		
				} finally {
					// TODO: handle finally clause
					entityManager.close();
				}
	}

	@Override
	public List<UserInfo> getUserByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		try{
			
			Query q = null;
			
			
			long result = (long) entityManager
					.createQuery(
							"SELECT count(u) FROM UserInfo u where  u.username LIKE :searchText OR  u.firstName LIKE :searchText OR u.lastName LIKE :searchText ")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			 q = entityManager.createQuery("from UserInfo u where  u.username LIKE :searchText OR  u.firstName LIKE :searchText OR u.lastName LIKE :searchText ",
					UserInfo.class);
				
				
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
				List<UserInfo> list = q.getResultList();
				return list;
			
			
			
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public int getUserCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		long result = (long) entityManager
				.createQuery(
						"SELECT count(u) FROM UserInfo u where  u.username LIKE :searchText OR  u.firstName LIKE :searchText OR u.lastName LIKE :searchText ")
				.setParameter("searchText", "%" + searchText + "%").getSingleResult();
		int total_count = (int) result;
		return total_count;
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		long 	result = (long) entityManager.createQuery("SELECT count(u) FROM UserInfo u").getSingleResult();

		int total_count = (int) result;
		return total_count;
	}

}
