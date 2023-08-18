package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.BundleApplications;

public interface BundleApplicationsCustomeRepo {

	List<BundleApplications> getApplicationBundlePagination(int page_no, int item_per_page);

	List<BundleApplications> getApplicationBundleByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getApplicationBundleTotalCount();

	int getApplicationBundleCountbySearch(String searchText);
}
