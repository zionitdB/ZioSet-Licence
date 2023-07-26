(function() {
	'use strict';

	angular
	.module('myApp.UnRegisterAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.UnRegisterAsset/:branchId', {
					url : "/UnRegisterAsset/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/UnRegisterAsset/UnRegisterAsset.html",
							controller : "UnRegisterAssetController as vm"
						}
					}
				})
			});

})();