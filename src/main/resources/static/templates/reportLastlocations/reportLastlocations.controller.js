(function() {
	'use strict';

	angular.module('myApp.reportLastlocations').controller('ReportLastlocationsController', ReportLastlocationsController);

	ReportLastlocationsController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportLastlocationsController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var dailyTransactionUrl = ApiEndpoint.url+"dailyTransaction";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle
		});

		(function activate() {
			$scope.showDatatable=false
			getAllAsset()
		})();
		function getAllAsset(){
			 $rootScope.loader=true;
			var msg=""
				 var url =assetUrl+"/getAllAsseta1";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.allAssets = response.data;
					 $rootScope.loader=false;
					//console.log("allAssets  :"+JSON.stringify(vm.allAssets))
				
			});
		}
		
		function getData(){
			console.log("Asset : "+JSON.stringify(vm.asset))
			if(vm.asset==undefined||vm.asset.serialNo==undefined){
				$scope.assetErr=true;
				return;
			}else{
				$scope.assetErr=false;
			}
			var url =dailyTransactionUrl+"/getLast7LocationByAssetId?id="+vm.asset.id;
			var msg=""
				console.log("url: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
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
			vm.labels={'srNo':'Sr No','asset.assetType':'Asset Type','asset.serialNo': 'SerialNo','asset.assetId': 'Asset Id','asset.make':'Make','asset.model':'Model','device.deviceName':'Device Detected','device.locationName':'Location','idate':'Date','itime':'Time'}
		
		
		
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
