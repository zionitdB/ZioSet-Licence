package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Notification;

public interface NotificationCustomeRepo {
	List<Notification> getNotificationByLimit(int page_no, int item_per_page);
	List<Notification> getNotificationByLimitAndSearch(String searchText, int pageNo, int perPage);
	int getNotificationCountAndSearch(String searchText);
	int getUnseenNotificationCount();

}
