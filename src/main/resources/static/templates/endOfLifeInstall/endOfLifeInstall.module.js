(function() {
	'use strict';

	angular
	.module('myApp.endOfLifeInstall', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.endOfLifeInstall', {
					url : "/endOfLifeInstall",
					views : {
						"sub" : {
							templateUrl : "templates/endOfLifeInstall/endOfLifeInstall.html",
							controller : "EndOfLifeInstallController as vm"
						}
					}
				})
			});

})();