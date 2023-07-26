(function() {
	'use strict';

	angular
	.module('myApp.unAllocatedAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.unAllocatedAsset/:branchId', {
					url : "/unAllocatedAsset/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/unAllocatedAsset/unAllocatedAsset.html",
							controller : "UnAllocatedAssetController as vm"
						}
					}
				})
			});

})();