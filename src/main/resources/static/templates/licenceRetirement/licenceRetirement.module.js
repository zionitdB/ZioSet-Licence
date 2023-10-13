(function() {
	'use strict';

	angular
	.module('myApp.licenceRetirement', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceRetirement', {
					url : "/licenceRetirement",
					views : {
						"sub" : {
							templateUrl : "templates/licenceRetirement/licenceRetirement.html",
							controller : "LicenceRetirementController as vm"
						}
					}
				})
			});

})();