(function() {
	'use strict';

	angular.module('myApp.endOfLifeSAAS').controller('EndOfLifeSAASController', EndOfLifeSAASController);

	EndOfLifeSAASController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function EndOfLifeSAASController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
			getEOLSAAS();
		})();
		$scope.file="Employees"
			vm.labels={'srNo':'Sr No','associate.associateName': 'Publisher', 'product.productName': 'Product','productVersion': 'Version','computerName': 'Computer Name','asset.serialNo':'Serial No','insDate':'Install Date','expiryDate':'Expiry Date'}
		
		$scope.newExcel= function(){
			 $rootScope.loader=true;
			 			setTimeout(function(){
				 
				 //
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);					
			
		}
		function getEOLSAAS(){
			var msg=""
				 var url =licenceUrl+"/getEOLSAAS";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.eolSAAS = response.data;
				console.log("eolSAAS: "+JSON.stringify(vm.eolSAAS))
				
								
			});
		}
	
		
	}

	
})();
