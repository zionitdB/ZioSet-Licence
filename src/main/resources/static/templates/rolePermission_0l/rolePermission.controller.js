(function() {
	'use strict';

	angular.module('myApp.rolePermission').controller('RolePermissionController', RolePermissionController);

	RolePermissionController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function RolePermissionController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
		var assetEmpMappedUrl = ApiEndpoint.url+"assetEmpMapped";
		var employeeUrl = ApiEndpoint.url+"employee";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
		})();
		
		
		
		
		
		
	}

	
})();
