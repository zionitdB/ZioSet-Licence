(function() {
	'use strict';

	angular
	.module('myApp.updateMac', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.updateMac', {
					url : "/updateMac",
					views : {
						"sub" : {
							templateUrl : "templates/updateMac/updateMac.html",
							controller : "UpdateMacController as vm"
						}
					}
				})
			});

})();