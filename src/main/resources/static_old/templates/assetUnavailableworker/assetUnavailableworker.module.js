(function() {
	'use strict';

	angular
	.module('myApp.assetUnavailableworker', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetUnavailableworker', {
					url : "/assetUnavailableworker",
					views : {
						"sub" : {
							templateUrl : "templates/assetUnavailableworker/assetUnavailableworker.html",
							controller : "AssetUnavailableworkerController as vm"
						}
					}
				})
			});

})();