(function() {
	'use strict';

	angular.module('myApp.assetData1').controller('AssetDataController', AssetDataController);

	AssetDataController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function AssetDataController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetUrl = ApiEndpoint.url+"asset";
		
		var vm = angular.extend(this, {
			
		});

		(function activate() {
			
			loadAssets();
			
		})();
		
		
		
		
		
		function loadAssets(){
			$rootScope.loader=true;
			var msg=""
				 var url =assetUrl+"/getAllAsseta1";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				$rootScope.loader=false;
				console.log("assets: "+JSON.stringify(vm.assets))
								
			});
		}
		
		
		
	
	

	
	}

	
})();
