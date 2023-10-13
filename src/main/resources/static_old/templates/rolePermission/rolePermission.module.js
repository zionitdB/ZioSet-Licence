(function () {
	'use strict';

	angular.module('myApp.rolePermission', [])
		.config(function ($stateProvider) {
			$stateProvider
				.state('main.rolePermission', {
		
					url: "/rolePermission",
					views: {
						"sub": {
							templateUrl: "templates/rolePermission/rolePermission.html",
							controller: "RolePermissionController as vm",
						}
					}
				})

		});

})();