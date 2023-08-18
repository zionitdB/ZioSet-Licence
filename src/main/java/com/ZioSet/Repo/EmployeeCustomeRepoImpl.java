package com.ZioSet.Repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ZioSet.model.Employee;

public class EmployeeCustomeRepoImpl implements EmployeeCustomeRepo {
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public List<Employee> getEmployeeByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub

		try {
		
			
				long result = (long) entityManager.createQuery("SELECT count(e) FROM Employee e").getSingleResult();
				int total_count = (int) result;
				Query q = entityManager.createQuery("from Employee e ", Employee.class);
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
				List<Employee> list = q.getResultList();
				return list;
			
			
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	@Override
	public List<Employee> getEmployeeByLimitAndSearch(String searchText, int pageNo, int perPage) {
		try {
		
			long result = (long) entityManager
					.createQuery(
							"SELECT count(e) FROM Employee e where  e.employeeName LIKE :searchText OR  e.employeeNo LIKE :searchText OR e.email LIKE :searchText OR e.mobileNo LIKE :searchText OR e.uhfCode LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			int total_count = (int) result;
			Query q = entityManager.createQuery(
					"from Employee e where    e.employeeName LIKE :searchText OR  e.employeeNo LIKE :searchText OR e.email LIKE :searchText OR e.mobileNo LIKE :searchText OR e.uhfCode LIKE :searchText",
					Employee.class);
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
			List<Employee> list = q.getResultList();
			return list;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	
	@Override
	public int getEmployeeCountAndSearch(String searchText) {
		try {
			// TODO Auto-generated method stub
			
			long result = (long) entityManager
					.createQuery(
							"SELECT count(e) FROM Employee e where  e.employeeName LIKE :searchText OR  e.employeeNo LIKE :searchText OR e.email LIKE :searchText OR e.mobileNo LIKE :searchText OR e.uhfCode LIKE :searchText")
					.setParameter("searchText", "%" + searchText + "%").getSingleResult();
			return (int) result;
		} finally {
			// TODO: handle finally clause
			
			entityManager.close();
		}
	}

	@Override
	public int getEmployeeCount() {
		// TODO Auto-generated method stub
try {
			
			long result = (long) entityManager.createQuery("SELECT count(e) FROM Employee e").getSingleResult();
			return (int) result;
		} finally {
			// TODO: handle finally clause
			entityManager.close();
		}
	}

	
}
