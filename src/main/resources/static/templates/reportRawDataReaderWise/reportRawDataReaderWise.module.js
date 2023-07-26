(function() {
	'use strict';

	angular
	.module('myApp.reportRawDataReaderWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportRawDataReaderWise', {
					url : "/reportRawDataReaderWise",
					views : {
						"sub" : {
							templateUrl : "templates/reportRawDataReaderWise/reportRawDataReaderWise.html",
							controller : "ReportRawDataReaderWiseController as vm"
						}
					}
				})
			});

})();