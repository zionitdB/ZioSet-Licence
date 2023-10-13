package com.ZioSet.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.Category;
import com.ZioSet.model.CategoryApplications;
import com.ZioSet.Repo.BundleApplicationsRepo;
import com.ZioSet.Repo.BundleRepo;
import com.ZioSet.Repo.CategoryApplicationsRepo;
import com.ZioSet.Repo.CategoryRepo;
@Service
public class BundleAndCategoryServiceImpl implements BundleAndCategoryService {
	
	@Autowired
	BundleRepo bundleRepo;
	
	@Autowired
	BundleApplicationsRepo bundleApplicationsRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	CategoryApplicationsRepo categoryApplicationsRepo;

	public Bundle addBundle(Bundle bundle) {
		return bundleRepo.save(bundle);
	}

	public List<Bundle> getBundlePagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return bundleRepo.getBundlePagination(page_no,item_per_page);
	}

	public List<Bundle> getBundleByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return bundleRepo.getBundleByLimitAndSearch(searchText,pageNo,perPage);
	}

	public int getBundleTotalCount() {
		// TODO Auto-generated method stub
		return bundleRepo.getBundleTotalCount();
	}

	public int getBundleCountbySearch(String searchText) {
		// TODO Auto-generated method stub
		return bundleRepo.getBundleCountbySearch(searchText);
	}

	public void addApplicationBundle(BundleApplications applicationBundle) {
		// TODO Auto-generated method stub
		bundleApplicationsRepo.save(applicationBundle);
	}

	public List<BundleApplications> getApplicationBundlePagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getApplicationBundlePagination(page_no,item_per_page);
	}

	public List<BundleApplications> getApplicationBundleByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getApplicationBundleByLimitAndSearch(searchText,pageNo,perPage);
	}

	public int getApplicationBundleTotalCount() {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getApplicationBundleTotalCount();
	}

	public int getApplicationBundleCountbySearch(String searchText) {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getApplicationBundleCountbySearch(searchText);
	}

	@Override
	public List<BundleApplications> getApplicationByBundleId(int bundleId) {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getApplicationByBundleId(bundleId);
	}

	@Override
	public List<Bundle> getAllBundles() {
		// TODO Auto-generated method stub
		return bundleRepo.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}

	@Override
	public void addCategoryApplications(CategoryApplications applications) {
		// TODO Auto-generated method stub
		categoryApplicationsRepo.save(applications);
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

	@Override
	public List<Category> getCategoryPagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryPagination(page_no,item_per_page);
	}

	@Override
	public List<Category> getCategoryByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getCategoryTotalCount() {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryTotalCount();
	}

	@Override
	public int getCategoryCountbySearch(String searchText) {
		// TODO Auto-generated method stub
		return categoryRepo.getCategoryCountbySearch (searchText);
	}

	@Override
	public List<CategoryApplications> getCategoryApplicationsByCategory(int categoryId) {
		// TODO Auto-generated method stub
		return categoryApplicationsRepo.getCategoryApplicationsByCategory(categoryId);
	}

	@Override
	public List<CategoryApplications> getCategoryApplicationsByCategoryName(String categoryName) {
		// TODO Auto-generated method stub
		return categoryApplicationsRepo.getCategoryApplicationsByCategoryName(categoryName);
	}

	@Override
	public List<BundleApplications> getbundleApplicationByBundleName(String bundleName) {
		// TODO Auto-generated method stub
		return bundleApplicationsRepo.getbundleApplicationByBundleName(bundleName);
	}

}
