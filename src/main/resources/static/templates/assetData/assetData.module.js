(function() {
	'use strict';

	angular
	.module('myApp.assetData1', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetData1', {
					url : "/assetData1",
					views : {
						"sub" : {
							templateUrl : "templates/assetData/assetData.html",
							controller : "AssetDataController as vm"
						}
					}
				})
			});

})();