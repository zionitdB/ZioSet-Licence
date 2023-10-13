(function() {
	'use strict';

	angular.module('myApp.notification').controller('NotificationController', NotificationController);

	NotificationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope'];
	
	/* @ngInject */
	function NotificationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope) {
		var notificationUrl  = ApiEndpoint.url+"notification";
		var vm = angular.extend(this, {
			read:read,
			loadNotificationsBySearch:loadNotificationsBySearch,
			serachText:"",
			perPage : 10,
			total_count:0,
			pageno:1,
			loadNotifications:loadNotifications
		});

		(function activate() {
			
			loadNotifications()
		
		})();
		
		
		$scope.searchByPagination=function (search){
			loadNotifications();
			
		}
		

		// current page
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadNotifications();
			
		}
		
		
		
			function loadNotifications(){
				var url="";
				var urlCount=""
				if(vm.serachText==""||vm.serachText==undefined){
					url=notificationUrl+"/getNotificationByLimit/"+vm.pageno+"/"+vm.perPage;
					urlCount=notificationUrl+"/getNotificationCount"
				}else{
					url=notificationUrl+"/getNotificationByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage
					urlCount=notificationUrl+"/getNotificationCountAndSearch?searchText="+vm.serachText;
				}
				
						var msg=""
						genericFactory.getAll(msg,url).then(function(response) {
						vm.notifications = response.data;
						console.log("notification: "+JSON.stringify(vm.notifications))
						
						
					});
						genericFactory.getAll(msg,urlCount).then(function(response) {
							
							vm.total_count= response.data;
							console.log("total_count: "+JSON.stringify(vm.total_count))
											
						});
			}
			function loadNotificationsBySearch(){
				
				var msg=""
				 var url =notificationUrl+"/getNotificationListBySearch?serachText="+vm.serachText;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.notifications = response.data;
				console.log("notification: "+JSON.stringify(vm.notifications))
				
				
			});
	}
		function read(notification){
						notification.view_bit=1
					 var msg="Notification updated"
					 var url =notificationUrl+"/updateNotification";
					genericFactory.add(msg,url,notification).then(function(response) {
						loadNotificationCount();
						loadNotifications()
						
					
				});
		}		
	
	function loadNotificationCount(){
		var msg=""
			 var url =notificationUrl+"/getNotificationCount";
			genericFactory.getAll(msg,url).then(function(response) {
				$rootScope.notificationCount = response.data;
			console.log("notification Count: "+JSON.stringify($rootScope.notificationCount))
			
			
		});
	}

	
	}
})();
