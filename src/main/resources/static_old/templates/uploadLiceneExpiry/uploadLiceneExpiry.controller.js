(function() {
	'use strict';

	angular.module('myApp.uploadLiceneExpiry').controller('UploadLiceneExpiryExpiryController', UploadLiceneExpiryExpiryController);

	UploadLiceneExpiryExpiryController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function UploadLiceneExpiryExpiryController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
			getUploadedExpiringLicencce();
		})();
		$scope.file="Employees"
			vm.labels={'srNo':'Sr No','licenceType':'Licence Type','associate.associateName': 'Publisher', 'product.productName': 'Product','licenceVersion': 'Version','projectName': 'Project Name','purchaseDate':'Purchase Date','insDate':'Install Date','expDate':'Expiry Date'}
		
		$scope.newExcel= function(){
			 $rootScope.loader=true;
			 			setTimeout(function(){
				 
				 //
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);					
			
		}
		function getUploadedExpiringLicencce(){
			var msg=""
				 var url =licenceUrl+"/getListOfUploadedExpiringLicencce";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.uploadedLicenceExpires = response.data;
				console.log("uploadedLicenceExpires: "+JSON.stringify(vm.uploadedLicenceExpires))
				
								
			});
		}
	
		
	}

	
})();
