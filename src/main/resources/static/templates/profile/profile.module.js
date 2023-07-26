(function() {
	'use strict';

	angular
	.module('myApp.profile', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.profile', {
					url : "/profile",
					views : {
						"sub" : {
							templateUrl : "templates/profile/profile.html",
							controller : "ProfileController as vm"
						}
					}
				})
			});

})();