(function() {
	'use strict';

	angular.module('myApp.licenceLifeNotification').controller('LicenceLifeNotificationController', LicenceLifeNotificationController);

	LicenceLifeNotificationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceLifeNotificationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		var licenceUrl = ApiEndpoint.url+"licence";

		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addNew:addNew,
			save:save,
			cancle:cancle,
			edit:edit,
			delet:delet,
			changeStatus:changeStatus
		});

		(function activate() {
			loadLicenceLifeNoftifications()
		})();
		
		function changeStatus(liceneceNotification){
			var msg="";
			var 	url=licenceUrl+"/changeLicenceLifeStatus";
			genericFactory.add(msg,url,liceneceNotification).then(function(response) {
				loadLicenceLifeNoftifications();
								
			});
			
		}
		function addNew(){
			$scope.addNewTab=true
			loadTypes()
		}
		function loadTypes(){
			var msg="";
			var 	url=licenceUrl+"/getLicenceTypes";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.types = response.data;
				console.log("types: "+JSON.stringify(vm.types))
								
			});
			
			
			
		}
		
		function loadLicenceLifeNoftifications(){
			var msg=""
				 var url =licenceUrl+"/getAllLicenceLifeNotification";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.liceneceNotifications= response.data;
				console.log("liceneceNotifications: "+JSON.stringify(vm.liceneceNotifications))
								
			});
		}

	
		
		function cancle(){
			$scope.addNewTab=false;
			vm.assetL={}
		}
		
		function delet(liceneceNotification){
			var msg=""
				 var url =licenceUrl+"/deleteLicenceLifeNotification";
					genericFactory.add(msg,url,liceneceNotification).then(function(response) {
						
						loadLicenceLifeNoftifications();
					
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		}
		function edit(licencceLife){
			loadTypes()
			$scope.addNewTab=true
			vm.licencceLife=licencceLife
			
		}
		
		function save(licencceLife){
			
			if(licencceLife==undefined||licencceLife.type==""){
				$scope.typeErr=true;
				return;
			}else{
				$scope.typeErr=false;
			}
			
			if(licencceLife.notificationBeforeDays==undefined||licencceLife.notificationBeforeDays==""){
				$scope.notificationBeforeDaysErr=true;
				return;
			}else{
				$scope.notificationBeforeDaysErr=false;
			}
			
			licencceLife.active=1
			console.log("assetlifes: "+JSON.stringify(licencceLife))

			var msg=""
				 var url =licenceUrl+"/addLicenceLifeNotification";
					genericFactory.add(msg,url,licencceLife).then(function(response) {
						
						loadLicenceLifeNoftifications();
						$scope.addNewTab=false;
						vm.licencceLife={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		
		}
		
	}

	
})();
