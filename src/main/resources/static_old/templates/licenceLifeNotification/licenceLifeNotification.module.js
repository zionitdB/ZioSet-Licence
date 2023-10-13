(function() {
	'use strict';

	angular
	.module('myApp.licenceLifeNotification', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceLifeNotification', {
					url : "/licenceLifeNotification",
					views : {
						"sub" : {
							templateUrl : "templates/licenceLifeNotification/licenceLifeNotification.html",
							controller : "LicenceLifeNotificationController as vm"
						}
					}
				})
			});

})();