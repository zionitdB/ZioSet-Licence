(function() {
	'use strict';

	angular
	.module('myApp.AllAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AllAsset/:branchId', {
					url : "/AllAsset/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/AllAsset/AllAsset.html",
							controller : "AllAssetController as vm"
						}
					}
				})
			});

})();