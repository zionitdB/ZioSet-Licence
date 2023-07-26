(function() {
	'use strict';

	angular.module('myApp.assetReport').controller('AssetReportController', AssetReportController);

	AssetReportController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function AssetReportController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			getAllAssets()
			$rootScope.loader=true;
		})();
		
		
		
		
		
		
		$scope.filename="Machines"
			//vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','assetType':'Asset Type','serialNo':'Serial No','assetId':'Asset Id','tagCode':'EPC','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
		vm.labels={'srNo': 'Sr No','serialNo': 'Serial Number','hostName':'HostName','locationName':'Location Name','assetType':'Asset Type','make':'Make','model':'Model', 'purchaseOrder':'Purchase Order','invoiceNo':'Invoice Number','invoiceDate':'Invoice Date','ageOfAsset':'Age of Assets','empCode':'Emp Code','ntid':'Emp NTID','employeeName':'Emp Name','departmentName':'Department','reportingManager':'Reporting Manager','employeeType':'Employee Type','assetStatus':'Asset Status'}

	  
		$scope.newExcel= function(){
			
			
			
			$rootScope.loader=true;
	    	//  getAllAssets();
				 $rootScope.loader=false;
				 setTimeout(function(){
					 
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
					  $rootScope.$digest();
					},1000);
	    	  //document.getElementById('btnExport').click();
			
			}
		
		function getAllAssets(){
			var msg=""
				 var url =assetUrl+"/getAllAssetReport";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allAssets = response.data;
				$rootScope.loader=false;
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
	

	
		
	}

	
})();
