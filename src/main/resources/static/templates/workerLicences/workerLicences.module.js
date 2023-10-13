(function() {
	'use strict';

	angular
	.module('myApp.workerLicences', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.workerLicences', {
					url : "/workerLicences",
					views : {
						"sub" : {
							templateUrl : "templates/workerLicences/workerLicences.html",
							controller : "WorkerLicencesController as vm"
						}
					}
				})
			});

})();