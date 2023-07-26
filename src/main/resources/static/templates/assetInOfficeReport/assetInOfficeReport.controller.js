(function() {
	'use strict';

	angular.module('myApp.assetInOfficeReport').controller('AssetInOfficeReportController', AssetInOfficeReportController);

	AssetInOfficeReportController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function AssetInOfficeReportController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var dailyTransactionUrl = ApiEndpoint.url+"dailyTransaction";
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		var vm = angular.extend(this, {
			perPage:10,
		});

		(function activate() {
			loadAllAssetsInOffice()
		})();
		
		
		
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','asset.assetType':'Asset Type','asset.serialNo': 'SerialNo','asset.assetId': 'Asset Id','asset.make':'Make','asset.model':'Model','device.deviceName':'In Device','device.locationName':'In Location','device2.deviceName':'Out Device','device2.locationName':'Out Location','idate':'In Date','itime':'In Time','odate':'Out Date','otime':'Out Time'}
		
		
		
		$scope.newExcel= function(){
		
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
		function loadAllAssetsInOffice(){
			var msg=""
				 var url;
				if(sId==1){
					url =dailyTransactionUrl+"/getAllAssetsinOffice?branchName=Pune";
				}
			if(sId==2){
				url =dailyTransactionUrl+"/getAllAssetsinOffice?branchName=Bengaluru";
			}
			console.log("url: "+url)

				genericFactory.getAll(msg,url).then(function(response) {
				vm.aasetInOffices= response.data;
				console.log("assetInOfficeReport: "+JSON.stringify(vm.aasetInOffices))
								
			});
		}

		
		
	}

	
})();
