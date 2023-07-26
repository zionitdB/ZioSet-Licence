(function() {
	'use strict';

	angular
	.module('myApp.updateTagRegistration', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.updateTagRegistration', {
					url : "/updateTagRegistration",
					views : {
						"sub" : {
							templateUrl : "templates/updateTagRegistration/updateTagRegistration.html",
							controller : "UpdateTagRegistrationController as vm"
						}
					}
				})
			});

})();