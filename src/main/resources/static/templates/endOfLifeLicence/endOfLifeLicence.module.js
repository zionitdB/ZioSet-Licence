(function() {
	'use strict';

	angular
	.module('myApp.endOfLifeLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.endOfLifeLicence', {
					url : "/endOfLifeLicence",
					views : {
						"sub" : {
							templateUrl : "templates/endOfLifeLicence/endOfLifeLicence.html",
							controller : "EndOfLifeLicenceController as vm"
						}
					}
				})
			});

})();