package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Bundle;

public interface BundleCustomeRepo {
	int getBundleCountbySearch(String searchText);

	int getBundleTotalCount();

	List<Bundle> getBundleByLimitAndSearch(String searchText, int pageNo, int perPage);

	List<Bundle> getBundlePagination(int page_no, int item_per_page);
}
