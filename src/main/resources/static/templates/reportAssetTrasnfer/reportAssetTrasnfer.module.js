(function() {
	'use strict';

	angular
	.module('myApp.reportAssetTrasnfer', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportAssetTrasnfer', {
					url : "/reportAssetTrasnfer",
					views : {
						"sub" : {
							templateUrl : "templates/reportAssetTrasnfer/reportAssetTrasnfer.html",
							controller : "ReportAssetTrasnferController as vm"
						}
					}
				})
			});

})();