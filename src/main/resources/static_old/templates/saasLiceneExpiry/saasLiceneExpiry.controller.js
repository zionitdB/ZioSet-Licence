(function() {
	'use strict';

	angular.module('myApp.saasLiceneExpiry').controller('SaasLiceneExpiryController', SaasLiceneExpiryController);

	SaasLiceneExpiryController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function SaasLiceneExpiryController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
			getInstalledExpiringLicencce();
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
		function getInstalledExpiringLicencce(){
			var msg=""
				 var url =licenceUrl+"/getListOfUploadedExpiringLicencce";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.installLicenceExpires = response.data;
				console.log("installLicenceExpires: "+JSON.stringify(vm.installLicenceExpires))
				
								
			});
		}
	
		
	}

	
})();
