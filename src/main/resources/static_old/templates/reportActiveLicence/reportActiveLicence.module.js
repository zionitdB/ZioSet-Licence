(function() {
	'use strict';

	angular
	.module('myApp.reportActiveLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportActiveLicence', {
					url : "/reportActiveLicence",
					views : {
						"sub" : {
							templateUrl : "templates/reportActiveLicence/reportActiveLicence.html",
							controller : "ReportActiveLicenceController as vm"
						}
					}
				})
			});

})();