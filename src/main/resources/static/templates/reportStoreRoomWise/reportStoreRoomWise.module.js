(function() {
	'use strict';

	angular
	.module('myApp.reportStoreRoomWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportStoreRoomWise', {
					url : "/reportStoreRoomWise",
					views : {
						"sub" : {
							templateUrl : "templates/reportStoreRoomWise/reportStoreRoomWise.html",
							controller : "ReportStoreRoomWiseController as vm"
						}
					}
				})
			});

})();