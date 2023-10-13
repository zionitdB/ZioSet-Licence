package com.ZioSet.Service;

import java.util.List;

import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.Category;
import com.ZioSet.model.CategoryApplications;

public interface BundleAndCategoryService {

	Bundle addBundle(Bundle bundle);

	List<Bundle> getBundlePagination(int page_no, int item_per_page);

	List<Bundle> getBundleByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getBundleTotalCount();

	int getBundleCountbySearch(String searchText);

	void addApplicationBundle(BundleApplications applicationBundle);

	List<BundleApplications> getApplicationBundlePagination(int page_no, int item_per_page);

	List<BundleApplications> getApplicationBundleByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getApplicationBundleTotalCount();

	int getApplicationBundleCountbySearch(String searchText);

	List<BundleApplications> getApplicationByBundleId(int bundleId);

	List<Bundle> getAllBundles();

	Category addCategory(Category category);

	void addCategoryApplications(CategoryApplications applications);

	List<Category> getAllCategory();

	List<Category> getCategoryPagination(int page_no, int item_per_page);

	List<Category> getCategoryByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getCategoryTotalCount();

	int getCategoryCountbySearch(String searchText);

	List<CategoryApplications> getCategoryApplicationsByCategory(int categoryId);

	List<CategoryApplications> getCategoryApplicationsByCategoryName(String categoryName);

	List<BundleApplications> getbundleApplicationByBundleName(String bundleName);

}
