(function() {
	'use strict';

	angular
	.module('myApp.assetReport', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.assetReport', {
					url : "/assetReport",
					views : {
						"sub" : {
							templateUrl : "templates/assetReport/assetReport.html",
							controller : "AssetReportController as vm"
						}
					}
				})
			});

})();