(function() {
	'use strict';

	angular
	.module('myApp.storageLocation', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.storageLocation', {
					url : "/storageLocation",
					views : {
						"sub" : {
							templateUrl : "templates/storageLocation/storageLocation.html",
							controller : "StorageLocationController as vm"
						}
					}
				})
			});

})();