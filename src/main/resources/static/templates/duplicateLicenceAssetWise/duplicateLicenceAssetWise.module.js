(function() {
	'use strict';

	angular
	.module('myApp.duplicateLicenceAssetWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.duplicateLicenceAssetWise', {
					url : "/duplicateLicenceAssetWise",
					views : {
						"sub" : {
							templateUrl : "templates/duplicateLicenceAssetWise/duplicateLicenceAssetWise.html",
							controller : "DuplicateLicenceAssetWiseController as vm"
						}
					}
				})
			});

})();