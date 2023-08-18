package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;

import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Software;

public interface SoftwareCustomeRepo {
	
	List<Software> getTodaysFetchLicenceByLimit(int page_no, int item_per_page, Date date);
	int getTodayFetchInstallLicenceCount(Date date);

}
