(function() {
	'use strict';

	angular
	.module('myApp.InstockLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.InstockLicence', {
					url : "/InstockLicence",
					views : {
						"sub" : {
							templateUrl : "templates/InstockLicence/InstockLicence.html",
							controller : "InstockLicenceController as vm"
						}
					}
				})
			});

})();