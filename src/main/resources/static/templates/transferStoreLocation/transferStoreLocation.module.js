(function() {
	'use strict';

	angular
	.module('myApp.transferStoreLocation', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.transferStoreLocation', {
					url : "/transferStoreLocation",
					views : {
						"sub" : {
							templateUrl : "templates/transferStoreLocation/transferStoreLocation.html",
							controller : "TransferStoreLocationController as vm"
						}
					}
				})
			});

})();