(function() {
	'use strict';

	angular
	.module('myApp.expirySAAS', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.expirySAAS', {
					url : "/expirySAAS",
					views : {
						"sub" : {
							templateUrl : "templates/expirySAAS/expirySAAS.html",
							controller : "ExpirySAASController as vm"
						}
					}
				})
			});

})();