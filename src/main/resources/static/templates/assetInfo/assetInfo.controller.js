(function() {
	'use strict';

	angular.module('myApp.assetInfo').controller('AssetInfoController', AssetInfoController);

	AssetInfoController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$stateParams'];
	
	/* @ngInject */
	function AssetInfoController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$stateParams) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var commonUrl = ApiEndpoint.url+"common";
		var assetEmpMappedUrl = ApiEndpoint.url+"assetEmpMapped";
		var assetUrl = ApiEndpoint.url+"asset";
		var workerSyncUrl = ApiEndpoint.url+"workerSync";
		var licenceUrl = ApiEndpoint.url+"licence";

		var dailyTransactionUrl = ApiEndpoint.url+"dailyTransaction";
		var sId=$stateParams.id;
		console.log("State Param sId:"+sId)
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
		
		});

		(function activate() {
			loadBranch()
			getAsset();
/*			getAllocationDetials();
*/			getAllocationHistory()
		/*	getLast7Location()*/
			getAllocatedLices()
		})();
		function getAllocatedLices(){
			
			var url =licenceUrl+"/getAssetLicenceByAssetId?id="+sId;
			var msg=""
				//console.log("url: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences= response.data;
				//console.log("licences: "+JSON.stringify(vm.licences))
				if(vm.licences.length!=0){
					$scope.licencesTab=true
				}
								
			});
			
		}
		function getAsset(){
			var msg=""
				 var url =assetUrl+"/getAssetById?id="+sId;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.asset = response.data;
					vm.asset.invoiceDate= new Date(vm.asset.invoiceDate)
					getMemoryDetials(vm.asset.serialNo)
					getOSDetials(vm.asset.serialNo)
					getCPUDetials(vm.asset.serialNo)
				
				console.log("asset: "+JSON.stringify(vm.asset))
								
			});
		}
		function getOSDetials(serialNo){
			var msg=""
				 var url =workerSyncUrl+"/getOSDetialsByAssetId?serialNo="+serialNo;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.oSDetials= response.data;
					vm.oSDetials.syncUpdatedDate=new Date(vm.oSDetials.syncUpdatedDate)
				console.log("oSDetials: "+JSON.stringify(vm.oSDetials))
								
			});
		}
		function getCPUDetials(serialNo){
			var msg=""
				 var url =workerSyncUrl+"/getCPUDetialsByAssetId?serialNo="+serialNo;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.cpUDetials= response.data;
					vm.cpUDetials.syncUpdatedDate=new Date(vm.cpUDetials.syncUpdatedDate)
				console.log("cpUDetials"+JSON.stringify(vm.cpUDetials))
								
			});
			var msg=""
				 var url =workerSyncUrl+"/getMemoryDetailsDetialsByAssetId?serialNo="+serialNo;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.memoryDetials= response.data;
					vm.memoryDetials.syncUpdatedDate=new Date(vm.cpUDetials.syncUpdatedDate)
				console.log("memoryDetials"+JSON.stringify(vm.memoryDetials))
								
			});
		}
		
		function getMemoryDetials(serialNo){
			console.log("Get Momory"+JSON.stringify(url))
			var msg=""
				 var url =workerSyncUrl+"/getMemoryDetailsDetialsByAssetId?serialNo="+serialNo;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.memoryDetials= response.data;
					vm.memoryDetials.syncUpdatedDate=new Date(vm.cpUDetials.syncUpdatedDate)
				console.log("memoryDetials"+JSON.stringify(vm.memoryDetials))
								
			});
		}
		
		function getMemoryDetials(serialNo){
			var msg=""
				 var url =workerSyncUrl+"/getDiskDetailsDetialsByAssetId?serialNo="+serialNo;
			console.log("url: "+JSON.stringify(url))

				genericFactory.getAll(msg,url).then(function(response) {
					vm.diskDetials= response.data;
				console.log("diskDetials"+JSON.stringify(vm.diskDetials))
								
			});
		}
		
		function getLast7Location(){
			
			var url =dailyTransactionUrl+"/getLast7LocationByAssetId?id="+sId;
			var msg=""
				console.log("url: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				console.log("transactions: "+JSON.stringify(vm.transactions))
				if(vm.transactions.length!=0){
					$scope.noLastLocationHistory=true
				}
								
			});
			
		}
		function getAllocationDetials(){
			var msg=""
				 var url =assetEmpMappedUrl+"/getAllocationByAssetId?id="+sId;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.allocation = response.data;
				console.log("allocation: "+JSON.stringify(vm.allocation))
				if(vm.allocation==""){
				$scope.noAllocation=true	
				}else{
					$scope.noAllocation=false
				}
								
			});
		}
		function getAllocationHistory(){
			var msg=""
				 var url =assetEmpMappedUrl+"/getAllocationHistoryByAssetId?id="+sId;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.allocationHistory = response.data;
				//console.log("allocationHistory: "+JSON.stringify(vm.allocationHistory))
				if(vm.allocationHistory==""){
				$scope.noAllocationHistory=true	
				}else{
					$scope.noAllocationHistory=false
				}
								
			});
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
