package com.ZioSet.Service;

import java.util.Date;
import java.util.List;

import com.ZioSet.model.Software;

public interface SoftwareService {

	Date getLatestDate();

	Date getSecondDate(Date latestDate);

	int getFetchCountByDate(Date latestDate);

	int getFetchUniqueCountByDate(Date latestDate);

	void addSoftwareOnj(Software softwares);

	List<Software> getInstallLiceneceByDate(Date date);

}
