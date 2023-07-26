(function() {
	'use strict';

	angular
	.module('myApp.endOfLifeReport', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.endOfLifeReport/:branchId', {
					url : "/endOfLifeReport/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/endOfLifeReport/endOfLifeReport.html",
							controller : "EndOfLifeReportController as vm"
						}
					}
				})
			});

})();