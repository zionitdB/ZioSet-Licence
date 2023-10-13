(function() {
	'use strict';

	angular
	.module('myApp.renewalLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.renewalLicence', {
					url : "/renewalLicence",
					views : {
						"sub" : {
							templateUrl : "templates/renewalLicence/renewalLicence.html",
							controller : "RenewalLicenceController as vm"
						}
					}
				})
			});

})();