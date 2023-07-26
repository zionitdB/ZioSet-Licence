(function() {
	'use strict';

	angular
	.module('myApp.AllLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AllLicence', {
					url : "/AllLicence",
					views : {
						"sub" : {
							templateUrl : "templates/AllLicence/AllLicence.html",
							controller : "AllLicenceController as vm"
						}
					}
				})
			});

})();