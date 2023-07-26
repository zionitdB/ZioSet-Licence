(function() {
	'use strict';

	angular.module('myApp.assetMovementReport').controller('AssetMovementReportController', AssetMovementReportController);

	AssetMovementReportController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$filter'];
	
	/* @ngInject */
	function AssetMovementReportController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$filter) {
		var assetRegistationUrl  = ApiEndpoint.url+"assetRegistation";
		var reportUrl  = ApiEndpoint.url+"report";
		var commonUrl = ApiEndpoint.url+"common";
		var vm = angular.extend(this, {
			reports:[],
			generateReport:generateReport,
			clear:clear,
			
		});
	

		(function activate() {
			//loadAssets();
			loadBranch()
			$scope.showExport=false
			$scope.showButton=true
		})();
		
		/*$scope.file="AssetMovementReport"
			vm.labels={'emoloyeeCode': 'Employee Code ', 'fName': 'First Name','lName': 'Last Name','uhfCode':'Card No','contactNo':'contactNo','emailId':'EmailId','addedBy':'AddedBy','addedDate':'AddedDate'}*/
		$scope.file="AssetMovementReport"
			vm.labels={'asset.assetType': 'Asset Type','asset.serialNo': 'Serial No', 'asset.assetId': 'Asset Id','asset.make': 'Make','asset.model':'Model','device.locationName':'Movement Location','reportDate':'Movement date'}
		
		$scope.newExcel= function(){
			
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
	
		
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		
		
			function generateReport(report){
				console.log("report: "+JSON.stringify(report))
				$rootScope.loader=true;
				if(report==undefined||report.branch==""){
					$scope.branchErr=true;
						return;
					}else{
						$scope.branchErr=false;
					}
				if(report.fromDate==undefined||report.fromDate==""){
					$scope.fromDateErr=true;
						return;
					}else{
						$scope.fromDateErr=false;
					}
				if(report.toDate==undefined||report.toDate==""){
					$scope.toDateErr=true;
						return;
					}else{
						$scope.toDateErr=false;
					}
				/*if(report.movementDate==""){
				$scope.movementDateErr=true;
					return;
				}else{
					$scope.assetErr=false;
				}
				*/
				report.branchId=report.branch.branchId
				report.fromdate=report.fromDate
				report.todate=report.toDate
				var msg=""
					console.log("report: "+JSON.stringify(report))
					 var url =reportUrl+"/getAssetMovementBydateAndBranch";
					genericFactory.add(msg,url,report).then(function(response) {
					vm.reports = response.data;
					console.log("report: "+JSON.stringify(vm.reports.length==0))
					if(vm.reports.length==0){
						toastr.error("No Record Found");
					}
					$rootScope.loader=false;
				});
				
			}
		
			function clear(){
				$scope.report={}
				vm.reports=[]
			}

	
	}
})();
