(function() {
	'use strict';

	angular.module('myApp.pushingAsset').controller('PushingAssetController', PushingAssetController);

	PushingAssetController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function PushingAssetController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
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
			
			loadKittingArea()
			
		})();
		function loadKittingArea(){
			var msg=""
				 var url =storageLocationUrl+"/getAllKittingAreas";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.kittingAreas = response.data;
				console.log("kittingAreas : "+JSON.stringify(vm.kittingAreas))
								
			});
			
		}
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
			
			$scope.getAssetByKittingArea=function (kittingArea){
				console.log("kittingArea: "+JSON.stringify(kittingArea))
				var msg=""
					 var url =assetUrl+"/getAssetByKittingArea?kittingArea="+kittingArea.kittingAreaName;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.assets = response.data;
					console.log("assets: "+JSON.stringify(vm.assets))
									
				});
	        }
	  	
			
			
			
			function save(){
				console.log("vm.transfer: "+JSON.stringify(vm.transfer))
				
				if(vm.transfer==undefined||vm.transfer.kittingArea==undefined){
					$scope.toStoreLocationErr=true;
					return;
				}else{
					$scope.toStoreLocationErr=false;
				}
				
				
				
			
				if($scope.selectedCount==0){
					$scope.noneSelErr=true;
					return;
				}else{
					$scope.noneSelErr=false;
				}
				
				
				//obj.transferedAsset=vm.Selassets;	
				console.log("vm.Selassets: "+JSON.stringify(vm.Selassets))
				var msg=""
					 var url =storageLocationUrl+"/pushingAsset";
					genericFactory.add(msg,url,vm.Selassets).then(function(response) {
					vm.branches = response.data;
					vm.transfer={}
					vm.Selassets=[]
					//
									
				});
			}
	
	}
	
	
})();
