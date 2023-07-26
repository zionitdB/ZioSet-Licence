(function() {
	'use strict';

	angular.module('myApp.unAllocatedAssetDetectByReader').controller('UnAllocatedAssetDetectByReaderController', UnAllocatedAssetDetectByReaderController);

	UnAllocatedAssetDetectByReaderController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function UnAllocatedAssetDetectByReaderController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
			var vm = angular.extend(this, {
			
			perPage : 10,
			total_count:10,
			pageno:1,
			serachText:"",
			allAssets:[],
			get:get,
			cancle:cancle
			
		});

		(function activate() {
			
			getAllAssets();
		
		})();
		function get(report){
			console.log("report: "+JSON.stringify(report))
			if(report==undefined||report.fromdate==""){
				console.log("ddd")
				$scope.fromDateErr=true
				return;
			}else{
				$scope.fromDateErr=false
			}
			
			if(report.todate==undefined||report.todate ==""){
				$scope.toDateErr=true
				return;
			}else{
				$scope.toDateErr=false
				var date1=report.fromdate
				var date2=report.todate
			
					if (date1 > date2) {
						console.log("Date 1 is more");
					  $scope.toDateVal=true
					  return;
					  }else{
						  console.log("Date 2 is more");
						  $scope.toDateVal=false
					  }
				
				
			}	$rootScope.loader=true;
			var msg=""
			 var url=assetUrl+"/getUnAllocatedAssetDetectByReader"
				genericFactory.add(msg,url,report).then(function(response) {
				vm.allAssets = response.data;
			
				$rootScope.loader=false;
				
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		function cancle(){
			$scope.report={}
			vm.allAssets=[]
		}
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','assetType':'Asset Type','serialNo':'Serial No','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','locationName':'Location Name','deviceName':'Device Name','detectedDate':'Detected Date','detectedTime':'Detected Time'}
		
		$scope.newExcel= function(){
			$rootScope.loader=true;
				 
	    	 document.getElementById('btnExport').click();
	    	 $rootScope.loader=false;
			
			}
		
		
		function getAllAssets(){
			var msg=""
				
		
			 var url=assetUrl+"/getUnAllocatedAssetByBranch?dataReq="
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allAssets = response.data;
			
					
				
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		
	//***********************Pagination Start*****************************//
		/*$scope.searchByPagination=function (search){
			loadAssets();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAssets();
			
		}*/
	
		
	
	}

	
})();
