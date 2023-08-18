(function() {
	'use strict';

	angular.module('myApp.systemLicence').controller('SystemLicenceController', SystemLicenceController);

	SystemLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function SystemLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadLicence:loadLicence,
			
		});

		(function activate() {
			
			loadLicence();
			loadAssocistes();	
			loadProducts();
		})();
	
		
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadLicence();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadLicence();
			
		}
		
		function loadLicence(){
			if(vm.perPage>=1000){
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=licenceUrl+"/getSystemLincencceByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getSystemLicenceCount"
			}else{
				url=licenceUrl+"/getSystemLicenceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=licenceUrl+"/getSystemLicenceCountAndSearch?searchText="+vm.serachText
			}
			console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				
				console.log("licencees: "+JSON.stringify(vm.licences ))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("Licence  Count: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
		
  //***********************Pagination End *****************************//
		function loadAssocistes(){
			var msg=""
				 var url =licenceUrl+"/getAssociates";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.associates = response.data;
				console.log("associates: "+JSON.stringify(vm.associates))
								
			});
		}
		//********************Pagination End *****************************//
		function loadProducts(){
			var msg=""
				 var url =licenceUrl+"/getProducts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.products = response.data;
				console.log("products: "+JSON.stringify(vm.products))
								
			});
		}
		
		
		
		
		
		
		
	
		
	}

	
})();
