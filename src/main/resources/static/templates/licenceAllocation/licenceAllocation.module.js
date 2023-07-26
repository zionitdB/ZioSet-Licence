(function() {
	'use strict';

	angular
	.module('myApp.licenceAllocation', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceAllocation', {
					url : "/licenceAllocation",
					views : {
						"sub" : {
							templateUrl : "templates/licenceAllocation/licenceAllocation.html",
							controller : "LicenceAllocationController as vm"
						}
					}
				})
			});

})();