(function() {
	'use strict';

	angular
	.module('myApp.installedLiceneExpiry', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.installedLiceneExpiry', {
					url : "/installedLiceneExpiry",
					views : {
						"sub" : {
							templateUrl : "templates/installedLiceneExpiry/installedLiceneExpiry.html",
							controller : "InstalledLiceneExpiryController as vm"
						}
					}
				})
			});

})();