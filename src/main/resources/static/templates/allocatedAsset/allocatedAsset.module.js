(function() {
	'use strict';

	angular
	.module('myApp.allocatedAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.allocatedAsset/:branchId', {
					url : "/allocatedAsset/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/allocatedAsset/allocatedAsset.html",
							controller : "AllocatedAssetController as vm"
						}
					}
				})
			});

})();