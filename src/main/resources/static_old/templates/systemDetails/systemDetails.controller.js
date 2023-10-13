(function() {
	'use strict';

	angular.module('myApp.systemDetails').controller('SystemDetailsController', SystemDetailsController);

	SystemDetailsController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function SystemDetailsController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
		var licenceUrl = ApiEndpoint.url+"licence";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			
		});

		(function activate() {
			
			loadAssets();
		
		})();
	
		
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
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=assetUrl+"/getAssetsWithSystemDetialsByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=assetUrl+"/getAssetCount/all";
			}else{
				url=assetUrl+"/getAssetsWithSystemDetialsByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=assetUrl+"/getAssetCountAndSearch?searchText="+vm.serachText+"&type=all"
			}
			
			
			console.log("urlCount:: "+urlCount)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				
			//	console.log("assets: "+JSON.stringify(vm.assets))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
								
			});
			
			
			
			
		}		
		
 
		
		
		
		
		
		
	
		
	}

	
})();
