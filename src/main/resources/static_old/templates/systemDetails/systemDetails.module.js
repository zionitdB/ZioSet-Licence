(function() {
	'use strict';

	angular
	.module('myApp.systemDetails', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.systemDetails', {
					url : "/systemDetails",
					views : {
						"sub" : {
							templateUrl : "templates/systemDetails/systemDetails.html",
							controller : "SystemDetailsController as vm"
						}
					}
				})
			});

})();