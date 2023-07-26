(function() {
	'use strict';

	angular
	.module('myApp.reportTransaction', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportTransaction', {
					url : "/reportTransaction",
					views : {
						"sub" : {
							templateUrl : "templates/reportTransaction/reportTransaction.html",
							controller : "ReportTransactionController2 as vm"
						}
					}
				})
			});

})();