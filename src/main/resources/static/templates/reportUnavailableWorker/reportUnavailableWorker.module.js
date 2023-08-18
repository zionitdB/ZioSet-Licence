(function() {
	'use strict';

	angular
	.module('myApp.reportUnavailableWorker', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportUnavailableWorker', {
					url : "/reportUnavailableWorker",
					views : {
						"sub" : {
							templateUrl : "templates/reportUnavailableWorker/reportUnavailableWorker.html",
							controller : "ReportUnavailableWorkerController as vm"
						}
					}
				})
			});

})();