(function() {
	'use strict';

	angular
	.module('myApp.bundleAppication', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.bundleAppication', {
					url : "/bundleAppication",
					views : {
						"sub" : {
							templateUrl : "templates/bundleAppication/bundleAppication.html",
							controller : "BundleAppicationController as vm"
						}
					}
				})
			});

})();