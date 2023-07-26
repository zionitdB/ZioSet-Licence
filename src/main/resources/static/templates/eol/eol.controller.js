(function() {
	'use strict';

	angular.module('myApp.eol').controller('EOLController', EOLController);

	EOLController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function EOLController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		var vm = angular.extend(this, {
			perPage:10,
		});

		(function activate() {
			loadAllEOLAssets()
		})();
		
		
		
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','branch.branchName':'Location','assetType': 'Asset Type','serialNo': 'Serial No', 'assetId': 'Asset Id','purchaseOrderNo': 'Purchase Order','invoiceNo': 'Invoice No','incDate':'Invoice Date','make':'Make','model':'Model','assignedStatus':'Status','eol':'End Of Life','currentAge':'Current Age'}
		
		
		
		$scope.newExcel= function(){
		
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
		function loadAllEOLAssets(){
			var msg=""
				 var url;
				if(sId==1){
					url =assetLifeUrl+"/getAllEOLAssets2?branchName=Pune";
				}
			if(sId==2){
				url =assetLifeUrl+"/getAllEOLAssets2?branchName=Bengaluru";
			}
				
				genericFactory.getAll(msg,url).then(function(response) {
				vm.eolAssets= response.data;
				console.log("loadAllEOLAssets: "+JSON.stringify(vm.eolAssets))
								
			});
		}

		
		
	}

	
})();
