(function() {
	'use strict';

	angular
	.module('myApp.reportExpiryLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportExpiryLicence', {
					url : "/reportExpiryLicence",
					views : {
						"sub" : {
							templateUrl : "templates/reportExpiryLicence/reportExpiryLicence.html",
							controller : "ReportExpiryLicenceController as vm"
						}
					}
				})
			});

})();