(function() {
	'use strict';

	angular.module('myApp.reportAssetTrasnfer').controller('ReportAssetTrasnferController', ReportAssetTrasnferController);

	ReportAssetTrasnferController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportAssetTrasnferController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var reportUrl = ApiEndpoint.url+"report";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
			//getData:getData,
			
		});

		(function activate() {
			$scope.showDatatable=false
			getData();
		})();
		
		
		function getData(){
		
			var url =reportUrl+"/getAssetlocationTransferHistoryReport";
			var msg=""
				var obj={}
		console.log("URL "+url);
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				console.log("transactions: "+JSON.stringify(vm.transactions))
				
								
			});
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','asset.assetType':'Asset Type','asset.serialNo': 'SerialNo','asset.assetId': 'Asset Id','asset.make':'Make','asset.model':'Model','fromLocation.storeRoomName':'From Location','toLocation.storeRoomName':'To Location','transferBy':'Transfer By','tDate':'Transaction Date'}
		
		
		
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
