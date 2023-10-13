package com.ZioSet.Service;

import java.util.List;

import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.DiskDetials;
import com.ZioSet.model.Notification;
import com.ZioSet.model.OSDetials;
import com.ZioSet.model.RamDetials;

public interface NotificationService {

	void sendNotificationForCPUChange(CPUDetials cpuDetials, CPUDetials cPU);

	void sendNotificationForOS(String string, OSDetials osDetials, OSDetials osDetials2);

	void sendRamNotification(String changeString,RamDetials ramDetials, RamDetials ramDetials2);

	void sendDiskUtilizationNotification(DiskDetials cpuDetials, double per);

	List<Notification> getNotificationByLimit(int page_no, int item_per_page);

	List<Notification> getNotificationByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getNotificationCount();

	int getNotificationCountAndSearch(String searchText);

	int getUnseenNotificationCount();

	void saveNotification(Notification notification);

}
