(function() {
	'use strict';

	angular
	.module('myApp.fetchInstallLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.fetchInstallLicence', {
					url : "/fetchInstallLicence",
					views : {
						"sub" : {
							templateUrl : "templates/fetchInstallLicence/fetchInstallLicence.html",
							controller : "FetchInstallLicenceController as vm"
						}
					}
				})
			});

})();