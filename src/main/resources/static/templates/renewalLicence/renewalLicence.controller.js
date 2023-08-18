(function() {
	'use strict';

	angular.module('myApp.renewalLicence').controller('RenewalLicenceController', RenewalLicenceController);

	RenewalLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function RenewalLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
		var dashboardUrl = ApiEndpoint.url+"dashboard";
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
			
		})();
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName':'Branch', 'associate.associateName':'Publisher', 'product.productName':'Product','licenceVersion':'Version', 'licenceType':'Licence Type','projectName':'Project Name','purDate':'Purchase Date','period':'Period'}
	
	
	$scope.newExcel=function(){
			setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				 
				  $rootScope.$digest();
				},1000);
	}
		
  //***********************Pagination End *****************************//
		function loadLicence(){
			var msg=""
				 var url =dashboardUrl+"/getRenewalLicenceList";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				console.log("licences: "+JSON.stringify(vm.licences))
								
			});
		}
	
		
		
		
		
		
	
		
	}

	
})();
