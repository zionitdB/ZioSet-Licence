(function() {
	'use strict';

	angular
	.module('myApp.reportAssetNoDetection', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportAssetNoDetection', {
					url : "/reportAssetNoDetection",
					views : {
						"sub" : {
							templateUrl : "templates/reportAssetNoDetection/reportAssetNoDetection.html",
							controller : "ReportAssetNoDetectionController as vm"
						}
					}
				})
			});

})();