(function() {
	'use strict';

	angular.module('myApp.workerInstalled').controller('WorkerInstalledController', WorkerInstalledController);

	WorkerInstalledController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function WorkerInstalledController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
		var dashboardUrl = ApiEndpoint.url+"dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		console.log("State Param sId:"+JSON.stringify($stateParams.userId))
		var vm = angular.extend(this, {
			
	
			
		});

		(function activate() {
			
			loadInstallWorkers();
		
		})();
		$scope.newExcel= function(){
			$rootScope.loader=true;
				 				document.getElementById('btnExport').click();
			$rootScope.loader=false;

			}
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Branch','assetType':'Asset Type','serialNo':'Serial No','make':'Make','model':'Model','allocatedToName':'Employee Name','allocatedToNo':'Employee No','workerStatus':'WorkerStatus','workerStatusDate':'Status as per Date'}
		
		
		function loadInstallWorkers(){
			var msg=""
				
		
				var url=""
				var url=dashboardUrl+"/getListWorkerInstalledList"

					genericFactory.getAll(msg,url).then(function(response) {
						vm.workerInstalSystems = response.data;
						
										
					});
					
		}
		
	
	
		
	}

	
})();
