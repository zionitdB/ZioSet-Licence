(function() {
	'use strict';

	angular
	.module('myApp.reportInstallLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportInstallLicence', {
					url : "/reportInstallLicence",
					views : {
						"sub" : {
							templateUrl : "templates/reportInstallLicence/reportInstallLicence.html",
							controller : "ReportInstallLicenceController as vm"
						}
					}
				})
			});

})();