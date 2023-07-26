(function() {
	'use strict';

	angular
	.module('myApp.licenceDetials', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetials', {
					url : "/licenceDetials",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetials/licenceDetials.html",
							controller : "LicenceDetialsController as vm"
						}
					}
				})
			});

})();