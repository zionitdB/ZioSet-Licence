(function() {
	'use strict';

	angular
	.module('myApp.licenceDetialsAssetWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetialsAssetWise/:id', {
					url : "/licenceDetialsAssetWise/:id",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetialsAssetWise/licenceDetialsAssetWise.html",
							controller : "LicenceDetialsAssetWiseController as vm"
						}
					}
				})
			});

})();