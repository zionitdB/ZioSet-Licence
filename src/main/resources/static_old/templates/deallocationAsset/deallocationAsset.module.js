(function() {
	'use strict';

	angular
	.module('myApp.deallocationAsset', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.deallocationAsset', {
					url : "/deallocationAsset",
					views : {
						"sub" : {
							templateUrl : "templates/deallocationAsset/deallocationAsset.html",
							controller : "DeallocationAssetController as vm"
						}
					}
				})
			});

})();