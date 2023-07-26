(function() {
	'use strict';

	angular.module('myApp.stockByKittingArea').controller('StockByKittingAreaController', StockByKittingAreaController);

	StockByKittingAreaController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function StockByKittingAreaController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var storageLocationUrl = ApiEndpoint.url+"storageLocation";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		
			getStock:getStock
		});

		(function activate() {
		
			
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
		
			
		function getStock(){
			var msg=""
				 var url =assetUrl+"/getAssetByKittingArea?kittingArea="+vm.transfer.kittingArea.kittingAreaName;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.stocks = response.data;
				console.log("stock : "+JSON.stringify(vm.stocks))
								
			});
			
		}
	
	}
	
	
})();
