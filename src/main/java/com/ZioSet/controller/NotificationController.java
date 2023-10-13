package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.NotificationService;
import com.ZioSet.dto.Status;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.EmailSenderDetials;
import com.ZioSet.model.Employee;
import com.ZioSet.model.Notification;

@RestController
@CrossOrigin("*")
@RequestMapping("notification")
public class NotificationController {
	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value = "/getNotificationByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Notification> getNotificationByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Notification> list= new  ArrayList<Notification>();
		try {	
			list=notificationService.getNotificationByLimit(page_no,item_per_page);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getNotificationByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Notification> getNotificationByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Notification> list= new  ArrayList<Notification>();
		try {	
			
			list=notificationService.getNotificationByLimitAndSearch(searchText,pageNo,perPage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getNotificationCount", method = RequestMethod.GET)
	public @ResponseBody int  getNotificationCount() {
		int  count= 0;
		try {
			count= notificationService.getNotificationCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getNotificationCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getNotificationCountAndSearch(@RequestParam("searchText") String searchText,@RequestParam("type") String type) {
		int  count= 0;
		try {
			count= notificationService.getNotificationCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getUnseenNotificationCount", method = RequestMethod.GET)
	public @ResponseBody int  getUnseenNotificationCount() {
		int  count= 0;
		try {
			count= notificationService.getUnseenNotificationCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@RequestMapping(value = "/updateNotification", method = RequestMethod.POST)
	public @ResponseBody Status updateAsSeen(@RequestBody Notification notification) {
		Status status =new Status();
		try {
			notification.setView_bit(1);
			notification.setViewDatetime(new Date());
			notificationService.saveNotification(notification);
			
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}

}
