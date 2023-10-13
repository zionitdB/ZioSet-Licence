package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.WorkerLicences;

public interface WorkerLicencesCustomeRepo {
	List<WorkerLicences> getWorkerLicencesByLimit(int page_no, int item_per_page);

	List<WorkerLicences> getWorkerLicencesByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getWorkerLicencesCount();

	int getWorkerLicencesCountAndSearch(String searchText);

}
