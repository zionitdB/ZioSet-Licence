package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.CategoryApplications;

public interface CategoryApplicationsRepo extends JpaRepository<CategoryApplications, Integer>{
	@Query("From CategoryApplications c where c.category.categorId=?1")
	List<CategoryApplications> getCategoryApplicationsByCategory(int categoryId);
	@Query("From CategoryApplications c where c.category.categoryName=?1")

	List<CategoryApplications> getCategoryApplicationsByCategoryName(String categoryName);

}
