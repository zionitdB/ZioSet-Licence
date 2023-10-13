(function() {
	'use strict';

	angular
	.module('myApp.endOfLifeSAAS', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.endOfLifeSAAS', {
					url : "/endOfLifeSAAS",
					views : {
						"sub" : {
							templateUrl : "templates/endOfLifeSAAS/endOfLifeSAAS.html",
							controller : "EndOfLifeSAASController as vm"
						}
					}
				})
			});

})();