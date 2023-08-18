package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Licence;

public interface LincencceCustomeRepo {
	List<Licence> getLincencceByLimit(int page_no, int item_per_page);

	List<Licence> getLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getLicenceCount();

	int getLicenceCountAndSearch(String searchText);

}
