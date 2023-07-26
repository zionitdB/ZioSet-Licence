(function() {
	'use strict';

	angular.module('myApp.reportStoreRoomWise').controller('ReportStoreRoomWiseController', ReportStoreRoomWiseController);

	ReportStoreRoomWiseController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportStoreRoomWiseController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var reportUrl = ApiEndpoint.url+"report";
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var storageLocationsUrl = ApiEndpoint.url+"storageLocation";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			getStorageLocationsByBranch:getStorageLocationsByBranch,
			getData:getData,
			
		});

		(function activate() {
			$scope.showDatatable=false
			loadBranch();
		})();
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		function getStorageLocationsByBranch(branchId){
			var msg=""
				 var url =storageLocationsUrl+"/getStoreLocationsByBranchId?branchId="+branchId;
		console.log("URL "+url);
			genericFactory.getAll(msg,url).then(function(response) {
				vm.storageLocations= response.data;
				console.log("storageLocations: "+JSON.stringify(vm.storageLocations))
				
								
			});
		}
		
		function getData(report){
			console.log("report : "+JSON.stringify(report))
		if(report==undefined||report.storeRoomName==undefined){
			$scope.branchErr=true;
			return;
		}else{
			$scope.branchErr=false;
			
		}
			var url =assetUrl+"/getAssetsByLocationAndBranch?locationName="+report.storeRoomName+"&branchId="+report.branchId;
			var msg=""
				var obj={}
		console.log("URL "+url);
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets= response.data;
				console.log("assets: "+JSON.stringify(vm.assets))
				
								
			});
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','assetType':'Asset Type','serialNo': 'SerialNo','assetId': 'Asset Id','make':'Make','model':'Model','storeLocation':'Store Location','invoiceNo':'Invoice No','incDate':'Invoice Date','currentAge':'Age'}
		
		
		
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
