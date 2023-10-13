(function() {
	'use strict';

	angular
	.module('myApp.saasLiceneExpiry', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.saasLiceneExpiry', {
					url : "/saasLiceneExpiry",
					views : {
						"sub" : {
							templateUrl : "templates/saasLiceneExpiry/saasLiceneExpiry.html",
							controller : "SaasLiceneExpiryController as vm"
						}
					}
				})
			});

})();