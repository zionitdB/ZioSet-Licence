(function() {
	'use strict';

	angular
	.module('myApp.reportLastlocations', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportLastlocations', {
					url : "/reportLastlocations",
					views : {
						"sub" : {
							templateUrl : "templates/reportLastlocations/reportLastlocations.html",
							controller : "ReportLastlocationsController as vm"
						}
					}
				})
			});

})();