package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.WorkerLicences;

public interface WorkerLicencesServices {

	List<WorkerLicences> getWorkerLicencesByLimit(int page_no, int item_per_page);

	List<WorkerLicences> getWorkerLicencesByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getWorkerLicencesCount();

	int getWorkerLicencesCountAndSearch(String searchText);

	void save(WorkerLicences workerLicences);

	String getLastInserted();

	Optional<WorkerLicences> getWorkerLicencesByKey(String licenceKey);

}
