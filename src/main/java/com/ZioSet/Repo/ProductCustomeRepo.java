package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Product;

public interface ProductCustomeRepo {
	List<Product> getProductByPagination(int page_no, int item_per_page);
	List<Product> getProductBySearchPagination(String searchText, int pageNo, int perPage);
	int getProductCount();
	int getSearchCountProduct(String searchText);


}
