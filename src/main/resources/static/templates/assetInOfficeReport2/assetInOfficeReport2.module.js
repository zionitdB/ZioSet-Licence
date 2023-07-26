(function() {
	'use strict';

	angular
	.module('myApp.assetInOfficeReport2', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetInOfficeReport2', {
					url : "/assetInOfficeReport2",
					views : {
						"sub" : {
							templateUrl : "templates/assetInOfficeReport2/assetInOfficeReport2.html",
							controller : "AssetInOfficeReportController2 as vm"
						}
					}
				})
			});

})();