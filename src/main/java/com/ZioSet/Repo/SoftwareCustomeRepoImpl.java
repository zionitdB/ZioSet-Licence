package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Software;

public class SoftwareCustomeRepoImpl implements SoftwareCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Software> getTodaysFetchLicenceByLimit(int page_no, int item_per_page, Date date) {
		// TODO Auto-generated method stub
	Query q = null;
		
		long result = (long) entityManager.createQuery(	"SELECT count(s) FROM Software s where  Date(s.detectedDate)=:date")
				.setParameter("date",date).getSingleResult();
		 q = entityManager.createQuery(
				"from Software s where  Date(s.detectedDate)=:date",
				Software.class);

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
			
			
			System.out.println("TOAL "+total_count);
			System.out.println("page_no "+page_no);

			System.out.println("item_per_page "+item_per_page);

			System.out.println("maxResult "+maxResult);
			q.setMaxResults(maxResult);
			
			q.setParameter("date",date);
			q.setFirstResult(firstR); // modify this to adjust paging
			q.setMaxResults(maxResult);
			List<Software> list = q.getResultList();
			return list;
		
	}

	@Override
	public int getTodayFetchInstallLicenceCount(Date date) {
		// TODO Auto-generated method stub
		long result = (long) entityManager.createQuery(	"SELECT count(s) FROM Software s where  Date(s.detectedDate)=:date")
				.setParameter("date",date).getSingleResult();
		return (int) result;
	}

}
