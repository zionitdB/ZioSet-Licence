(function() {
	'use strict';

	angular
	.module('myApp.InstallLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.InstallLicence', {
					url : "/InstallLicence",
					views : {
						"sub" : {
							templateUrl : "templates/InstallLicence/InstallLicence.html",
							controller : "InstallLicenceController as vm"
						}
					}
				})
			});

})();