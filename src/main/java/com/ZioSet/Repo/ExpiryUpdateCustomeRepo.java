package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.ExpiryUpdate;

public interface ExpiryUpdateCustomeRepo {
	List<ExpiryUpdate> getExpiryUpdateByLimit(int page_no, int item_per_page);

	List<ExpiryUpdate> getExpiryUpdateByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getExpiryUpdateCount();

	int getExpiryUpdateCountAndSearch(String searchText);

}
