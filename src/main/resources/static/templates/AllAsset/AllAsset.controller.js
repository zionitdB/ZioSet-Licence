(function() {
	'use strict';

	angular.module('myApp.AllAsset').controller('AllAssetController', AllAssetController);

	AllAssetController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function AllAssetController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		console.log("State Param sId:"+JSON.stringify($stateParams.userId))
		var vm = angular.extend(this, {
			
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			allAssets:[]
			
		});

		(function activate() {
			
			loadAssets();
		
		})();
		
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','assetType':'Asset Type','serialNo':'Serial No','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
		
		$scope.newExcel= function(){
			$rootScope.loader=true;
	    	  getAllAssets();
				 $rootScope.loader=true;
	    	  //document.getElementById('btnExport').click();
			
			}
		
		
		function getAllAssets(){
			var msg=""
				
			var dataReq;
			if(sId==1){
				dataReq="Pune"
			}else{
				dataReq="Bengaluru"
			}

			 var url =assetUrl+"/getAllAssetaByBranch?branchName="+dataReq;
				console.log("assetUrl: "+url)

				genericFactory.getAll(msg,url).then(function(response) {
				vm.allAssets1 = response.data;
				angular.forEach(vm.allAssets1, function(value, key) {
					var emp;
					var srNo=key+1;
					emp=value
					emp.srNo=srNo=key+1;
					vm.allAssets.push(emp);
					});
				setTimeout(function(){
					 
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
					 
					  $rootScope.$digest();
					},1000);		
				
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadAssets();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAssets();
			
		}
		
		function loadAssets(){
			if(vm.perPage>=1000){
				console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
		
			var dataReq=""
				console.log("MORE THAN 100")
			if(sId==1){
				dataReq="Pune"
			}else{
				dataReq="Bengaluru"
			}
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=assetUrl+"/getAllAssetsByLimit/"+vm.pageno+"/"+vm.perPage+"/"+dataReq;
				urlCount=assetUrl+"/getAllAssetCount/"+dataReq
			}else{
				url=assetUrl+"/getAllAssetsByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&dataReq="+dataReq;
				urlCount=assetUrl+"/getAllAssetCountAndSearch?searchText="+vm.serachText+"&dataReq="+dataReq
			}
			
			
			console.log("urlCount :: "+urlCount)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				
				console.log("assets: "+JSON.stringify(vm.assets))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
	
		
	}

	
})();