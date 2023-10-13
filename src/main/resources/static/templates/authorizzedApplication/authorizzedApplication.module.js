(function() {
	'use strict';

	angular
	.module('myApp.authorizzedApplication', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.authorizzedApplication', {
					url : "/authorizzedApplication",
					views : {
						"sub" : {
							templateUrl : "templates/authorizzedApplication/authorizzedApplication.html",
							controller : "AuthorizzedApplicationController as vm"
						}
					}
				})
			});

})();