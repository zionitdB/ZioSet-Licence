(function() {
	'use strict';

	angular.module('myApp.storageLocation').controller('StorageLocationController', StorageLocationController);

	StorageLocationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function StorageLocationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var storageLocationUrl = ApiEndpoint.url+"storageLocation";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			
			addNew:addNew,
			cancle:cancle,
			save:save,
			edit:edit,
			
		});

		(function activate() {
		
			loadStorageLocations();
		})();
		
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','deviceGrouping': 'Device Grouping','deskLocation': 'Location','assetType':'Asset Type','serialNo':'Serial No','assetId':'Asset Id','tagCode':'EPC','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
	
		
		$scope.newExcel= function(){
			
			
			
			
			}
	$scope.checkStoreRoom=function(){
		console.log("vm.storageLocation : "+JSON.stringify(vm.storageLocation))
		
		var msg=""
			 var url =storageLocationUrl+"/checkStorageLocation";
			genericFactory.add(msg,url,vm.storageLocation).then(function(response) {
			vm.resStoreName = response.data;
			console.log("resStoreName: "+JSON.stringify(vm.resStoreName))
							
		});
	}
		
		function addNew(){
			vm.storageLocation={}
			$scope.addNew=true;
		
			loadBranch()
			
		}
	
		function cancle(){
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.viewQRTab=false;
			vm.asset={};
		
		}
		
		function edit(storageLocation){
			vm.storageLocation=storageLocation
			$scope.addNew=true;
			loadBranch()
		}
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadStorageLocations();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadStorageLocations();
			
		}
		
		function loadStorageLocations(){
			
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=storageLocationUrl+"/getStorageLocationByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=storageLocationUrl+"/getStorageLocationCount"
			}else{
				url=storageLocationUrl+"/getStorageLocationByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=storageLocationUrl+"/getStorageLocationCountAndSearch?searchText="+vm.serachText;
			}
			
			
			//console.log("$scope.type :: "+$scope.type)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.storeLocations = response.data;
				
				console.log("storeLocations: "+JSON.stringify(vm.storeLocations))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
			
				vm.total_count= response.data;
			//	console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
	
		
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				//console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		
		
		function save(asset){
			if(vm.storageLocation==undefined||vm.storageLocation.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(vm.storageLocation.floor==undefined||vm.storageLocation.floor==""){
				$scope.floorErr=true;	
				return;
			}else{
				$scope.floorErr=false;		
			}
			if(vm.storageLocation.wing==undefined||vm.storageLocation.wing==""){
				$scope.wingErr=true;
				return;
			}else{
				$scope.wingErr=false;		
			}
			if(vm.storageLocation.storeRoomName==undefined||vm.storageLocation.storeRoomName==""){
				$scope.storeNameErr=true;
				return;
			}else{
				$scope.storeNameErr=false;		
			}
			
			console.log("resStoreName: "+JSON.stringify(vm.resStoreName))
			if(vm.resStoreName.code==500){
				$scope.storeNameValidationoErr=true;
				return;
			}else{
				$scope.storeNameValidationoErr=false;
			}
			
			
			var msg=""
				 var url =storageLocationUrl+"/addStorageLocation";
				genericFactory.add(msg,url,vm.storageLocation).then(function(response) {
				vm.resData = response.data;
				if(vm.resData.code==200){
					toastr.success(vm.resData.message);
					loadStorageLocations();
					vm.storageLocation={}
					$scope.addNew=false;
				}else{
					toastr.error(vm.resData.message);
				}
				
				console.log("resData: "+JSON.stringify(vm.resData))
								
			});
		}
	
		
	
		

	
	}

	
})();
