(function() {
	'use strict';

	angular.module('myApp.unavailableworkerforLast5Days').controller('UnavailableworkerforLast5DaysController', UnavailableworkerforLast5DaysController);

	UnavailableworkerforLast5DaysController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function UnavailableworkerforLast5DaysController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var workerSyncUrl = ApiEndpoint.url+"workerSync";
	
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
		
			loadUnavailableWorker();
		
			
		})();
		$scope.newExcel= function(){
			$rootScope.loader=true;
				 				document.getElementById('btnExport').click();
			$rootScope.loader=false;

			}
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Branch','assetType':'Asset Type','serialNo':'Serial No','make':'Make','model':'Model','employee.firstName':'First Name	','employee.lastName':'Last Name','employee.employeeNo':'Employee No'}
		
		function loadUnavailableWorker(){
			
			var msg="";
			var 	url=workerSyncUrl+"/getUnavailableWorkerForLast5Days";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.unavailableWorkers = response.data;
				console.log("unavailableWorkers: "+JSON.stringify(vm.unavailableWorkers))
								
			});
		}
	
		
	}

	
})();
