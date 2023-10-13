(function() {
	'use strict';

	angular
	.module('myApp.licenceDetialsBundle', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetialsBundle', {
					url : "/licenceDetialsBundle",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetialsBundle/licenceDetialsBundle.html",
							controller : "LicenceDetialsBundleController as vm"
						}
					}
				})
			});

})();