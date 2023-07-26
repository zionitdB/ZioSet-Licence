(function() {
	'use strict';

	angular.module('myApp.transferStoreLocation').controller('TransferStoreLocationController', TransferStoreLocationController);

	TransferStoreLocationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function TransferStoreLocationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var storageLocationUrl = ApiEndpoint.url+"storageLocation";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			Selassets:[],
			save:save
			
		});

		(function activate() {
			$scope.selectedCount=0
			loadBranch()
			
		})();
		$scope.selectAsset=function(asset){
			console.log("asset: "+JSON.stringify(asset.check))
			if(asset.check){
				vm.Selassets.push(asset)
				$scope.selectedCount++;
				$scope.noneSelErr=false;
			}else{
				vm.Selassets.splice(asset)
				$scope.selectedCount--;
			}
		}
			$scope.lodStoreLocationByBranch=function (branchId){
				var msg=""
					 var url =storageLocationUrl+"/getStoreLocationsByBranchId?branchId="+branchId;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.storeLocations = response.data;
					//console.log("branches: "+JSON.stringify(vm.branches))
									
				});
	        }
			$scope.lodAssetByStoreLocation=function (storageLocation){
				console.log("storageLocation: "+JSON.stringify(storageLocation))
				var msg=""
					 var url =assetUrl+"/getAssetsByStoreLocation?storeLocationName="+storageLocation.storeRoomName;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.assets = response.data;
					console.log("assets: "+JSON.stringify(vm.assets))
									
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
			
			
			function save(){
				console.log("vm.transfer: "+JSON.stringify(vm.transfer))
				
				if(vm.transfer==undefined||vm.transfer.branch==undefined){
					$scope.branchErr=true;
					return;
				}else{
					$scope.branchErr=false;
				}
				if(vm.transfer.fromLocation==undefined){
					$scope.fromStoreLocationErr=true;
					return;
				}else{
					$scope.fromStoreLocationErr=false;
				}
				if(vm.transfer.toLocation==undefined){
					$scope.toStoreLocationErr=true;
					return;
				}else{
					$scope.toStoreLocationErr=false;
				}
				
				if(vm.transfer.fromLocation.storageLocationId==vm.transfer.toLocation.storageLocationId){
					$scope.sameLocationErr=true;
					return;
				}else{
					$scope.sameLocationErr=false;
				}
			
				if($scope.selectedCount==0){
					$scope.noneSelErr=true;
					return;
				}else{
					$scope.noneSelErr=false;
				}
				
				var obj={};
				obj.fromStorageLocation=vm.transfer.fromLocation.storeRoomName
				obj.toStorageLocation=vm.transfer.toLocation.storeRoomName
				obj.transferedAsset=vm.Selassets;	
				obj.transferBy=	userDetail.firstName+" "+userDetail.lastName;
				console.log("vm.transfer: "+JSON.stringify(obj))
				var msg=""
					 var url =storageLocationUrl+"/transferLocation";
					genericFactory.add(msg,url,obj).then(function(response) {
					vm.branches = response.data;
					vm.transfer={}
					vm.Selassets=[]
					//console.log("branches: "+JSON.stringify(vm.branches))
									
				});
			}
	
	}
	
	
})();
