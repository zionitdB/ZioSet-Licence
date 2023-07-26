(function() {
	'use strict';

	angular.module('myApp.assetLife').controller('AssetLifeController', AssetLifeController);

	AssetLifeController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function AssetLifeController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addNew:addNew,
			save:save,
			cancle:cancle,
			edit:edit,
			delet:delet,
		});

		(function activate() {
			loadAssetLifes()
		})();
		function addNew(){
			$scope.addNewTab=true
			loadAssetType()
		}
		
		function loadAssetLifes(){
			var msg=""
				 var url =assetLifeUrl+"/getAllAssetLife";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assetlifes= response.data;
				console.log("assetlifes: "+JSON.stringify(vm.assetlifes))
								
			});
		}

		function loadAssetType(){
			var msg=""
				 var url =assetLifeUrl+"/getAllAssetTypes";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assetTypes= response.data;
				console.log("assetTypes: "+JSON.stringify(vm.assetTypes))
								
			});
		}
		
		function cancle(){
			$scope.addNewTab=false;
			vm.assetL={}
		}
		
		function delet(assetL){
			var msg=""
				 var url =assetLifeUrl+"/deletAssetLife";
					genericFactory.add(msg,url,assetL).then(function(response) {
						
						loadAssetLifes();
					
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		}
		function edit(assetL){
			loadAssetType()
			$scope.addNewTab=true
			vm.assetL=assetL
			vm.assetL.assetType=assetL.assetType
			
		}
		
		function save(assetL){
			
			if(assetL==undefined||assetLife.assetType==""){
				$scope.assetTypeErr=true;
				return;
			}else{
				$scope.assetTypeErr=false;
			}
			
			if(assetL.assetLife==undefined||assetL.assetLife==""){
				$scope.assetTypeErr=true;
				return;
			}else{
				$scope.assetTypeErr=false;
			}
			if(assetL.assetLife==undefined||assetL.assetLife==""){
				$scope.assetTypeErr=true;
				return;
			}else{
				$scope.assetTypeErr=false;
			}
			assetL.active=1
			assetL.addedBy=userDetail.firstName+' '+userDetail.lastName
			
			var msg=""
				 var url =assetLifeUrl+"/addNewAssetLife";
					genericFactory.add(msg,url,assetL).then(function(response) {
						
						loadAssetLifes();
						$scope.addNewTab=false;
						vm.assetL={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		
		}
		
	}

	
})();
