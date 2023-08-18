package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.UserInfo;

public interface UserCustomeRepo {
	List<UserInfo> getUserByLimit(int page_no, int item_per_page);

	List<UserInfo> getUserByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getUserCountAndSearch(String searchText);

	int getUserCount();
}
