(function() {
	'use strict';

	angular
	.module('myApp.AssignedLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AssignedLicence', {
					url : "/AssignedLicence",
					views : {
						"sub" : {
							templateUrl : "templates/AssignedLicence/AssignedLicence.html",
							controller : "AssignedLicenceController as vm"
						}
					}
				})
			});

})();