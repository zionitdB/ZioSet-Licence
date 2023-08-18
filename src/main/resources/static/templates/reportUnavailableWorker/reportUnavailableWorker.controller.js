(function() {
	'use strict';

	angular.module('myApp.reportUnavailableWorker').controller('ReportUnavailableWorkerController', ReportUnavailableWorkerController);

	ReportUnavailableWorkerController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function ReportUnavailableWorkerController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var dashboardUrl = ApiEndpoint.url+"dashboard";
	
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			getData:getData,
		});

		(function activate() {
		
		
			
		})();
		$scope.newExcel= function(){
			$rootScope.loader=true;
				 				document.getElementById('btnExport').click();
			$rootScope.loader=false;

			}
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Branch','assetType':'Asset Type','serialNo':'Serial No','make':'Make','model':'Model','allocatedToName':' Name','allocatedToNo':'Employee No'}
		
		
		
		function getData(report){
			if(report==undefined||report.fromdate==""){
				$scope.fromdateErr=true;
				return;
			}else{
				$scope.fromdateErr=false;
			}
			
			if(report.todate==""||report.todate==undefined){
				$scope.todateErr=true;
				return;
			}else{
				$scope.todateErr=false;
			}
		
			var msg=""
				 var url =dashboardUrl+"/getListWorkerInstalledListINDateRange";
				genericFactory.add(msg,url,report).then(function(response) {
				vm.unavailableWorkers = response.data;
				console.log("datas  "+JSON.stringify(vm.datas))
								
			});
			
			
		}
		
		
		/*function loadUnavailableWorker(){
			
			var msg="";
			var 	url=workerSyncUrl+"/getUnavailableWorkerForLast5Days";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.unavailableWorkers = response.data;
				console.log("unavailableWorkers: "+JSON.stringify(vm.unavailableWorkers))
								
			});
		}
	*/
		
	}

	
})();
