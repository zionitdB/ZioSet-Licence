(function() {
	'use strict';

	angular
	.module('myApp.systemLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.systemLicence', {
					url : "/systemLicence",
					views : {
						"sub" : {
							templateUrl : "templates/systemLicence/systemLicence.html",
							controller : "SystemLicenceController as vm"
						}
					}
				})
			});

})();