(function() {
	'use strict';

	angular
	.module('myApp.bundleWiseDetials', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.bundleWiseDetials', {
					url : "/bundleWiseDetials",
					views : {
						"sub" : {
							templateUrl : "templates/bundleWiseDetials/bundleWiseDetials.html",
							controller : "BundleWiseDetialsController as vm"
						}
					}
				})
			});

})();