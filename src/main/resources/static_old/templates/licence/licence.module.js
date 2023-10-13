(function() {
	'use strict';

	angular
	.module('myApp.licence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licence', {
					url : "/licence",
					views : {
						"sub" : {
							templateUrl : "templates/licence/licence.html",
							controller : "LicenceController as vm"
						}
					}
				})
			});

})();