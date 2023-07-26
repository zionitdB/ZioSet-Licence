(function() {
	'use strict';

	angular.module('myApp.assetInOfficeReport2').controller('AssetInOfficeReportController2', AssetInOfficeReportController2);

	AssetInOfficeReportController2.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function AssetInOfficeReportController2($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var dailyTransactionUrl = ApiEndpoint.url+"dailyTransaction";
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle
		});

		(function activate() {
			$scope.showDatatable=false
		})();
		
		
		function getData(){
			console.log("$scope.transDate : "+JSON.stringify(vm.transDate))
			if(vm.transDate==""||vm.transDate==undefined){
				$scope.dateErr=true;
				return;
			}else{
				$scope.dateErr=false;
			}
			var url =dailyTransactionUrl+"/getAllAssetsinOfficeByDate";
			var msg=""
				var obj={}
			obj.date=vm.transDate
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.aasetInOffices= response.data;
				console.log("assetInOfficeReport: "+JSON.stringify(vm.aasetInOffices))
				if(vm.aasetInOffices.length!=0){
					$scope.showDatatable=true
				}
								
			});
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
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
