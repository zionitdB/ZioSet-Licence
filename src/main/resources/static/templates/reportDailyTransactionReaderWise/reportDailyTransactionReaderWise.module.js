(function() {
	'use strict';

	angular
	.module('myApp.reportDailyTransactionReaderWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportDailyTransactionReaderWise', {
					url : "/reportDailyTransactionReaderWise",
					views : {
						"sub" : {
							templateUrl : "templates/reportDailyTransactionReaderWise/reportDailyTransactionReaderWise.html",
							controller : "ReportDailyTransactionReaderWiseController as vm"
						}
					}
				})
			});

})();