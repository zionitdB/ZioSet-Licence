(function() {
	'use strict';

	angular
	.module('myApp.customerSuppliedSoftware', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.customerSuppliedSoftware', {
					url : "/customerSuppliedSoftware",
					views : {
						"sub" : {
							templateUrl : "templates/customerSuppliedSoftware/customerSuppliedSoftware.html",
							controller : "CustomerSuppliedSoftwareController as vm"
						}
					}
				})
			});

})();