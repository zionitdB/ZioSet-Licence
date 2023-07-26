(function() {
	'use strict';

	angular
	.module('myApp.pullingAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.pullingAsset', {
					url : "/pullingAsset",
					views : {
						"sub" : {
							templateUrl : "templates/pullingAsset/pullingAsset.html",
							controller : "PullingAssetController as vm"
						}
					}
				})
			});

})();