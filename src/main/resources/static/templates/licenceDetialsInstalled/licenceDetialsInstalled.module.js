(function() {
	'use strict';

	angular
	.module('myApp.licenceDetialsInstalled', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetialsInstalled', {
					url : "/licenceDetialsInstalled",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetialsInstalled/licenceDetialsInstalled.html",
							controller : "LicenceDetialsInstalledController as vm"
						}
					}
				})
			});

})();