(function() {
	'use strict';

	angular.module('myApp.kittingArea').controller('KittingAreaController', KittingAreaController);

	KittingAreaController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function KittingAreaController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var userUrl = ApiEndpoint.url+"user";
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
		
			loadKittingAreas();
		})();
		
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','deviceGrouping': 'Device Grouping','deskLocation': 'Location','assetType':'Asset Type','serialNo':'Serial No','assetId':'Asset Id','tagCode':'EPC','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
	
		
		$scope.newExcel= function(){
			
			
			
			
			}
	$scope.checkKittingArea=function(){
		console.log("vm.kittingArea : "+JSON.stringify(vm.kittingArea))
		
		var msg=""
			 var url =storageLocationUrl+"/checkKittingArea";
			genericFactory.add(msg,url,vm.kittingArea).then(function(response) {
			vm.resStoreName = response.data;
			console.log("resStoreName: "+JSON.stringify(vm.resStoreName))
							
		});
	}
		
		function addNew(){
			vm.storageLocation={}
			$scope.addNew=true;
		
			loadBranch()
			loadUser()
			
		}
	
		function cancle(){
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.viewQRTab=false;
			vm.asset={};
		
		}
		
		function edit(kittingArea){
			vm.kittingArea=kittingArea
			$scope.addNew=true;
			loadBranch()
			loadUser()
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
			loadKittingAreas();
			
		}
		
		function loadKittingAreas(){
			
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=storageLocationUrl+"/getKittingAreaByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=storageLocationUrl+"/getKittingAreaCount"
			}else{
				url=storageLocationUrl+"/getKittingAreaByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=storageLocationUrl+"/getKittingAreaCountAndSearch?searchText="+vm.serachText;
			}
			
			
			//console.log("$scope.type :: "+$scope.type)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.kittingAreaList = response.data;
				
				console.log("kittingAreaList: "+JSON.stringify(vm.kittingAreaList))
								
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
		
		function loadUser(){
			var msg=""
				 var url =userUrl+"/getAllUsers";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.users = response.data;
				console.log("users: "+JSON.stringify(vm.users))
								
			});
		}
		
		function save(){
			if(vm.kittingArea==undefined||vm.kittingArea.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(vm.kittingArea.user==undefined||vm.kittingArea.user==""){
				$scope.userErr=true;	
				return;
			}else{
				$scope.userErr=false;		
			}
		
			if(vm.kittingArea.kittingAreaName==undefined||vm.storageLocation.kittingAreaName==""){
				$scope.kittingAreaNameErr=true;
				return;
			}else{
				$scope.kittingAreaNameErr=false;		
			}
			
			console.log("resStoreName: "+JSON.stringify(vm.resStoreName))
			if(vm.resStoreName.code==500){
				$scope.kittingAreaNameValidationoErr=true;
				return;
			}else{
				$scope.kittingAreaNameValidationoErr=false;
			}
			
			
			var msg=""
				 var url =storageLocationUrl+"/addKittingArea";
				genericFactory.add(msg,url,vm.kittingArea).then(function(response) {
				vm.resData = response.data;
				if(vm.resData.code==200){
					toastr.success(vm.resData.message);
					loadKittingAreas();
					vm.kittingArea={}
					$scope.addNew=false;
				}else{
					toastr.error(vm.resData.message);
				}
				
				console.log("resData: "+JSON.stringify(vm.resData))
								
			});
		}
	
		
	
		

	
	}

	
})();
