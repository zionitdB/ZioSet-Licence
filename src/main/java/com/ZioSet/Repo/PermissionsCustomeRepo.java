package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Permissions;

public interface PermissionsCustomeRepo {

	List<Permissions> getPermissionsByPagination(int page_no, int item_per_page);

	List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search);

	int getPermissionsCount();

	int getPermissionsCountBySearch(String search);

}
