(function() {
	'use strict';

	angular.module('myApp.reportActiveLicence').controller('ReportActiveLicenceController', ReportActiveLicenceController);

	ReportActiveLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportActiveLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var assetUrl = ApiEndpoint.url+"asset";
		var licenceUrl = ApiEndpoint.url+"licence";

		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle,
			getDataAssetSerialNo:getDataAssetSerialNo,
			getDataAssetSerialNoAndAssociate:getDataAssetSerialNoAndAssociate,
			getDataAssetSerialNoAndAssociateAndProduct:getDataAssetSerialNoAndAssociateAndProduct
			
		});

		(function activate() {
			$scope.showDatatable=false
			getData();
			getAssets();
			getAssociate();
			getProduct();
		})();
		
		
		function getData(){
		
			var url =licenceUrl+"/getAllInstallLicenceStocks";
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				//console.log("transactions: "+JSON.stringify(vm.transactions))
				if(vm.transactions.length!=0){
					$scope.showDatatable=true
				}
								
			});
			
		}
		
		function getAssets(){
		
			var url =assetUrl+"/getAllAsset1";
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets= response.data;
				//console.log("assets: "+JSON.stringify(vm.assets))
				
								
			});
			
		}
		
		function getAssociate(){
		
			var url =licenceUrl+"/getAssociates";
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.associates= response.data;
			//	console.log("associates: "+JSON.stringify(vm.associates))
				
								
			});
			
		}
		
		function getProduct(){
		
			var url =licenceUrl+"/getProducts";
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.products= response.data;
				//console.log("products: "+JSON.stringify(vm.products))
							
			});
			
		}
		
		function getDataAssetSerialNo(serialNo){
			var url =licenceUrl+"/getAllInstallLicenceStocksBySerialNo?serialNo="+serialNo;
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				//console.log("products: "+JSON.stringify(vm.products))
							
			});
			
			
		}
		function getDataAssetSerialNoAndAssociate(serialNo,associateName){
			var url =licenceUrl+"/getAllInstallLicenceStocksBySerialNoAnsAssociate?serialNo="+serialNo+"&associateName="+associateName
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				//console.log("products: "+JSON.stringify(vm.products))
							
			});
			
			
		}
		function getDataAssetSerialNoAndAssociateAndProduct(serialNo,associateName,productName){
			var url =licenceUrl+"/getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct?serialNo="+serialNo+"&associateName="+associateName+"&productName="+productName;
			var msg=""
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.transactions= response.data;
				//console.log("products: "+JSON.stringify(vm.products))
							
			});
			
			
		}	
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','associate.associateName':'Associate','product.productName': 'Product','productVersion': 'Version','asset.make':'Make','asset.model':'Model','asset.serialNo':'Serial No','asset.assetId':'Asset Id','computerName':'Computer Name','asset.employeeNo':'Employee No','asset.employeeName':'Employee Name','insDate':'Install Date'}
		
		
		
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
