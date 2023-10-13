(function() {
	'use strict';

	angular
	.module('myApp.duplicateLicenceAll', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.duplicateLicenceAll', {
					url : "/duplicateLicenceAll",
					views : {
						"sub" : {
							templateUrl : "templates/duplicateLicenceAll/duplicateLicenceAll.html",
							controller : "DuplicateLicenceAllController as vm"
						}
					}
				})
			});

})();