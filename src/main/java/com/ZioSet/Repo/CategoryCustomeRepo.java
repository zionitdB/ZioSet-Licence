package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Category;

public interface CategoryCustomeRepo {
	List<Category> getCategoryPagination(int page_no, int item_per_page);

	List<Category> getCategoryByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getCategoryTotalCount();

	int getCategoryCountbySearch(String searchText);

}
