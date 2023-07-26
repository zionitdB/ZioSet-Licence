(function() {
	'use strict';

	angular.module('myApp.fetchInstallLicence').controller('FetchInstallLicenceController', FetchInstallLicenceController);

	FetchInstallLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function FetchInstallLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		var licenceUrl = ApiEndpoint.url+"licence";
		var softwareUrl = ApiEndpoint.url+"software";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			fetchData:fetchData
		});

		(function activate() {
			$scope.today=new Date()
		})();
		
		
		
		
		
		function fetchData(){
			$rootScope.loader=true;
			var msg="";
			var 	url=softwareUrl+"/runSoftwareCheck";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.responceObj = response.data;
				$scope.noOfLicence=vm.responceObj.length
				getFetchData()
				console.log("responceObj"+JSON.stringify(vm.responceObj.length))
			
								
			});
			
		}
		function getFetchData(){
			var obj={}
			obj.iDate=new Date()
			var msg="";
			var 	url=softwareUrl+"/getInstallApplicationByInstallDate";
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.fetchDatas = response.data;
				$rootScope.loader=false;
				console.log("fetchDatas : "+JSON.stringify(vm.fetchDatas))
				
			});
		}
	}

	
})();
