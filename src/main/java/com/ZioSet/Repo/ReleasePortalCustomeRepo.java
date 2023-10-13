package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Licence;
import com.ZioSet.model.ReleasePortal;

public interface ReleasePortalCustomeRepo {
	List<ReleasePortal> getReleasePortalPagination(int pageNo, int perPage, int productId);

	List<ReleasePortal> getReleasePortalPaginationAndSearch(String searchText, int pageNo, int perPage, int productId);

	int getCountReleasePortal(int productId);

	int getCountReleasePortalAndSearch(String searchText, int productId);
}
