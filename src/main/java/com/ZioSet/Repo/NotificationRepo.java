package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ZioSet.model.Notification;


public interface NotificationRepo extends JpaRepository<Notification, Integer>,NotificationCustomeRepo {
	@Query("From Notification  n where n.view_bit=0")
	List<Notification> getNotificationList();
	@Query("select count(*)  From Notification  n where n.view_bit=0")
	int getNotificationCount();
	@Query("From Notification  n where n.view_bit=0 and n.messsge LIKE  %:searchText% ")
	List<Notification> getNotificationListBySearch(@Param("searchText")String searchText);
	@Query("From Notification  n where n.view_bit=0")
	List<Notification> getAllUnSeenNotifications();
	
	@Query(value="select * From notification_tr  n where   n.messsge=?1 ", nativeQuery = true)
	Optional<Notification> getNotificationListByMessage(String messsge);
	
	@Query("From Notification  n where Date(n.notificationDatatime)=?1")
	List<Notification> getNotificationListByDate(Date date);
	
	@Query(value="select TOP (10) notification_id,messsge,notification_datatime,view_datetime,view_bit From notification_tr  n ", nativeQuery = true)
	List<Notification> getNotificationLast10();
	

}
