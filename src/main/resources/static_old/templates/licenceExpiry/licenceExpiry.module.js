(function() {
	'use strict';

	angular
	.module('myApp.licenceExpiry', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceExpiry', {
					url : "/licenceExpiry",
					views : {
						"sub" : {
							templateUrl : "templates/licenceExpiry/licenceExpiry.html",
							controller : "LicenceExpiryController as vm"
						}
					}
				})
			});

})();