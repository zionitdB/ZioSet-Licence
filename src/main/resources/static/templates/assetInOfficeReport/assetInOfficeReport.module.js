(function() {
	'use strict';

	angular
	.module('myApp.assetInOfficeReport', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetInOfficeReport/:branchId', {
					url : "/assetInOfficeReport/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/assetInOfficeReport/assetInOfficeReport.html",
							controller : "AssetInOfficeReportController as vm"
						}
					}
				})
			});

})();