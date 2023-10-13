(function() {
	'use strict';

	angular
	.module('myApp.updateExpirySAAS', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.updateExpirySAAS', {
					url : "/updateExpirySAAS",
					views : {
						"sub" : {
							templateUrl : "templates/updateExpirySAAS/updateExpirySAAS.html",
							controller : "UpdateExpirySAASController as vm"
						}
					}
				})
			});

})();