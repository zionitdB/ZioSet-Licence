(function() {
	'use strict';

	angular
	.module('myApp.eol', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.eol/:branchId', {
					url : "/eol/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/eol/eol.html",
							controller : "EOLController as vm"
						}
					}
				})
			});

})();