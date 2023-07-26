(function() {
	'use strict';

	angular.module('myApp.reportAssetNoDetection').controller('ReportAssetNoDetectionController', ReportAssetNoDetectionController);

	ReportAssetNoDetectionController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$filter'];
	
	/* @ngInject */
	function ReportAssetNoDetectionController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$filter) {
		var reportUrl  = ApiEndpoint.url+"report";
		var commonUrl = ApiEndpoint.url+"common";
		var vm = angular.extend(this, {
			generateReport:generateReport
		});

		(function activate() {
			$scope.comNoOfDay=3
			getDataForDasboard($scope.comNoOfDay)
			loadBranch()
		})();
		
		
		$scope.changeNoOfDay=function(noOfDay){
			console.log("noOfDay: "+noOfDay)
			if(noOfDay>5){
				$scope.noOfDayMax1Err=true
				console.log("Max")
			//	$scope.comNoOfDay=5;
			return;
			}else{
				$scope.noOfDayMax1Err=false
				console.log("OK")
			}
			getDataForDasboard(noOfDay)
			console.log("noOfDay1: "+$scope.comNoOfDay)
		}
		function getDataForDasboard(comNoOfDay){
			var report={}
			report.noOfDay=comNoOfDay
			report.date=new Date();
			report.dataFor="All"
			var msg=""
			 var url =reportUrl+"/getRecordForUndetectAssets";
			console.log("report: "+JSON.stringify(report))
			genericFactory.add(msg,url,report).then(function(response) {
				vm.allData=response.data
			$scope.AlldatasCount = response.data.length;
			console.log("AlldatasCount : "+JSON.stringify($scope.AlldatasCount))
								
		});
			var report1={}
			report1.noOfDay=comNoOfDay
			report1.date=new Date();
			report1.dataFor="BranchWise"
				report1.branchId=1
			 var url =reportUrl+"/getRecordForUndetectAssets";
				console.log("report: "+JSON.stringify(report))
				genericFactory.add(msg,url,report1).then(function(response) {
					vm.puneData=response.data
				$scope.puneDatasCount = response.data.length;
				console.log("puneDatasCount: "+JSON.stringify($scope.puneDatasCount))
									
			});
				var report2={}
				report2.noOfDay=comNoOfDay
				report2.date=new Date();
				report2.dataFor="BranchWise"
					report2.branchId=2
				var url =reportUrl+"/getRecordForUndetectAssets";
				console.log("report: "+JSON.stringify(report))
				genericFactory.add(msg,url,report2).then(function(response) {
				$scope.bengaluruDatasCount = response.data.length;
				vm.bengaluruData=response.data
				console.log("bengaluruDatasCount : "+JSON.stringify($scope.bengaluruDatasCount))
									
			});
			
		}
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','asset.branch.branchName': 'Location','asset.assetType':'Asset Type','asset.serialNo':'Serial No','asset.assetId':'Asset Id','asset.make':'Make','asset.model':'Model','employee.employeeNo':'Employee No','employee.username':'User No','empName':'Employee Name','fromDate':'From Date','toDate':'To Date'}

		$scope.newExcel= function(){
			
			
			
			$rootScope.loader=true;
	    	
			
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
	    	  //document.getElementById('btnExport').click();
			
			}
		
		function generateReport(report){
			
			if(report==undefined||report.dataFor==""||report.dataFor==null){
				$scope.dataForErr=true
				return;
			}else{
				$scope.dataForErr=false
				if(report.dataFor=="BranchWise"){
					if(report.branchId==""||report.branchId==undefined){
						$scope.branchErr=true;
						return;
					}else{
						$scope.branchErr=false;
					}
				}
			}
			
			if(report.noOfDay==""||report.noOfDay==0||report.noOfDay==undefined){
				$scope.noOfDayErr=true
				return;
			}else{
				$scope.noOfDayErr=false
				
				if(report.noOfDay>5){
					$scope.noOfDayMaxErr=true
					return;
				}else{
					$scope.noOfDayMaxErr=false
				}
			}
			$rootScope.loader=true;
			report.date=new Date();
			var msg=""
			 var url =reportUrl+"/getRecordForUndetectAssets";
			console.log("report: "+JSON.stringify(report))
			genericFactory.add(msg,url,report).then(function(response) {
			vm.datas = response.data;
			$rootScope.loader=false;
			console.log("data: "+JSON.stringify(vm.datas))
					
		});
	}
		$scope.allAsset=function(){
			vm.datas=vm.allData
		}
		$scope.puneAsset=function(){
			vm.datas=vm.puneData
		}
		$scope.bengaluruAsset=function(){
			vm.datas=vm.bengaluruData
		}
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
	
	}
})();
