(function() {
	'use strict';

	angular
	.module('myApp.assetLife', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetLife', {
					url : "/assetLife",
					views : {
						"sub" : {
							templateUrl : "templates/assetLife/assetLife.html",
							controller : "AssetLifeController as vm"
						}
					}
				})
			});

})();