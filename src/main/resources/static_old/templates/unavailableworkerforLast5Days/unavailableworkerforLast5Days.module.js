(function() {
	'use strict';

	angular
	.module('myApp.unavailableworkerforLast5Days', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.unavailableworkerforLast5Days', {
					url : "/unavailableworkerforLast5Days",
					views : {
						"sub" : {
							templateUrl : "templates/unavailableworkerforLast5Days/unavailableworkerforLast5Days.html",
							controller : "UnavailableworkerforLast5DaysController as vm"
						}
					}
				})
			});

})();