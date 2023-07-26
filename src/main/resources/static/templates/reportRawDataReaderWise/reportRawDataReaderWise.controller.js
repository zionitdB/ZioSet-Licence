(function() {
	'use strict';

	angular.module('myApp.reportRawDataReaderWise').controller('ReportRawDataReaderWiseController', ReportRawDataReaderWiseController);

	ReportRawDataReaderWiseController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportRawDataReaderWiseController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var commonUrl = ApiEndpoint.url+"common";
		var deviceUrl = ApiEndpoint.url+"device";
		var reportUrl = ApiEndpoint.url+"report";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle
		});

		(function activate() {
			loadBranch();
		})();
		$scope.getReaderByBranch=function(branchId){
			var msg=""
				 var url =deviceUrl+"/getDevicesByBranch?branchId="+branchId;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.devices = response.data;
				console.log("devices : "+JSON.stringify(vm.devices))
								
			});
		}
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches : "+JSON.stringify(vm.branches))
								
			});
			
		}
		
		function getData(){
			console.log("report : "+JSON.stringify(vm.report))
			if(vm.report==""||vm.report==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(vm.report.device==""||vm.report.device==undefined){
				$scope.deviceErr=true;
				return;
			}else{
				$scope.deviceErr=false;
			}
			if(vm.report.fromDate==""||vm.report.fromDate==undefined){
				$scope.dateErr=true;
				return;
			}else{
				$scope.dateErr=false;
			}
			if(vm.report.toDate==""||vm.report.toDate==undefined){
				$scope.dateErr1=true;
				return;
			}else{
				$scope.dateErr1=false;
			}
			var url =reportUrl+"/getRawDataByReadetAndDate";
			var msg=""
				var obj={}
			
			obj.deviceId=vm.report.device.deviceId
			obj.fromDate=vm.report.fromDate
			obj.toDate=vm.report.toDate
			console.log("obj: "+JSON.stringify(obj))
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.transactions= response.data;
				console.log("transactions: "+JSON.stringify(vm.transactions))
				if(vm.transactions.length!=0){
					$scope.showDatatable=true
				}
								
			});
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','device.locationName':'Device Name','device.locationName': 'Location','assetEpc': 'EPC','assetId':'Asset ID','insertDateTime':'Date','insertTime':'Time','message':'Message'}
		
		
		
		$scope.newExcel= function(){
		
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
	

		
		
	}

	
})();
