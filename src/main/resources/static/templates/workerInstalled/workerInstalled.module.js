(function() {
	'use strict';

	angular
	.module('myApp.workerInstalled', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.workerInstalled', {
					url : "/workerInstalled",
					views : {
						"sub" : {
							templateUrl : "templates/workerInstalled/workerInstalled.html",
							controller : "WorkerInstalledController as vm"
						}
					}
				})
			});

})();