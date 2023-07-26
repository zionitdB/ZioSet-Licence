(function() {
	'use strict';

	angular
	.module('myApp.rawDataReport', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.rawDataReport', {
					url : "/rawDataReport",
					views : {
						"sub" : {
							templateUrl : "templates/rawDataReport/rawDataReport.html",
							controller : "RawDataReportController as vm"
						}
					}
				})
			});

})();