package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.LicenceExpriry;

public interface LicenceExpiryCustomeRepo {
	List<LicenceExpriry> getAvailanleLicenceExpiryPagination(int page_no, int item_per_page);
	List<LicenceExpriry> getAvailanleLicenceExpirySearchPagination(String searchText, int pageNo, int perPage);
	int getAllCountAvailanleLicenceExpiry();
	int getSearchCountAvailanleLicenceExpiry(String searchText);


}
