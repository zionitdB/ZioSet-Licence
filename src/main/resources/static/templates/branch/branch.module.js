(function() {
	'use strict';

	angular
	.module('myApp.branch', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.branch', {
					url : "/branch",
					views : {
						"sub" : {
							templateUrl : "templates/branch/branch.html",
							controller : "BranchController as vm"
						}
					}
				})
			});

})();