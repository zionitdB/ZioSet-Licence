package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.WorkerLicencesRepo;
import com.ZioSet.model.WorkerLicences;

@Service
public class WorkerLicencesServicesImpl implements WorkerLicencesServices {
	@Autowired
	WorkerLicencesRepo workerLicencesRepo;

	@Override
	public List<WorkerLicences> getWorkerLicencesByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getWorkerLicencesByLimit(page_no,item_per_page);
	}

	@Override
	public List<WorkerLicences> getWorkerLicencesByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getWorkerLicencesByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getWorkerLicencesCount() {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getWorkerLicencesCount();
	}

	@Override
	public int getWorkerLicencesCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getWorkerLicencesCountAndSearch(searchText);
	}

	@Override
	public void save(WorkerLicences workerLicences) {
		// TODO Auto-generated method stub
		workerLicencesRepo.save(workerLicences);
	}

	@Override
	public String getLastInserted() {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getLastInserted();
	}

	@Override
	public Optional<WorkerLicences> getWorkerLicencesByKey(String licenceKey) {
		// TODO Auto-generated method stub
		return workerLicencesRepo.getWorkerLicencesByKey(licenceKey);
	}

}
