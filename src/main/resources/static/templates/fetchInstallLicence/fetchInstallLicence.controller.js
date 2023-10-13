(function() {
	'use strict';

	angular.module('myApp.fetchInstallLicence').controller('FetchInstallLicenceController', FetchInstallLicenceController);

	FetchInstallLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function FetchInstallLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		var licenceUrl = ApiEndpoint.url+"licence";
		var softwareUrl = ApiEndpoint.url+"software";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			fetchData:fetchData
		});

		(function activate() {
			$scope.today=new Date()
			getFetchData()
		})();
		
		
		
		
		
		function fetchData(){
			$rootScope.loader=true;
			
			var msg="";
			var 	url=assetUrl+"/getAllAsset";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				console.log("URL "+url)
				/*$scope.noOfLicence=vm.responceObj.length
				getFetchData()*/
				var i=1;
				angular.forEach(vm.assets, function (asset) {
					console.log("systemIp"+JSON.stringify(asset.systemIp))

	             if(asset.systemIp!=null){
	            	 
	 				var 	url1="http://"+asset.systemIp+":8088/worker/runSoftwareCheck";
	 				console.log("URL "+url1)

	 				genericFactory.getAll(msg,url1).then(function(response) {
	 									
	 				});
	 				 setTimeout(function(){
	 					var 	url1="http://"+asset.systemIp+":8088/worker/runSoftwareCheck1";
		 				console.log("URL "+url1)

		 				genericFactory.getAll(msg,url1).then(function(response) {
		 									
		 				});
	 					
	 					},1000);
	 				 setTimeout(function(){
		 					var 	url1="http://"+asset.systemIp+":8088/workerSystem/findSysInfo";
			 				console.log("URL "+url1)

			 				genericFactory.getAll(msg,url1).then(function(response) {
			 									
			 				});
		 					
		 					},2000);
	 				
	 				
	             }
					
	 				console.log("I "+i)
	 				console.log("Length "+vm.assets.length)
	 				if(i==vm.assets.length){
	 					$rootScope.loader=false;
	 					getFetchData();
	 				}
	 				i++;
	            });
								
			});
			
			
			
			
		/*	var msg="";
			var 	url=softwareUrl+"/runSoftwareCheck";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.responceObj = response.data;
				$scope.noOfLicence=vm.responceObj.length
				getFetchData()
				console.log("responceObj"+JSON.stringify(vm.responceObj.length))
			
								
			});*/
			
		}
		function getFetchData(){
			var obj={}
			obj.date=new Date()
			var msg="";
			var 	url=softwareUrl+"/getInstallLiceneceByDate";
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.fetchDatas = response.data;
				$rootScope.loader=false;
				$scope.noOfLicence = response.data.length;
				console.log("fetchDatas : "+JSON.stringify(vm.fetchDatas))
				
			});
		}
	}

	
})();
