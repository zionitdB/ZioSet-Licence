(function() {
	'use strict';

	angular
	.module('myApp.pushingAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.pushingAsset', {
					url : "/pushingAsset",
					views : {
						"sub" : {
							templateUrl : "templates/pushingAsset/pushingAsset.html",
							controller : "PushingAssetController as vm"
						}
					}
				})
			});

})();