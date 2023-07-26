(function() {
	'use strict';

	angular
	.module('myApp.categoryGrouping', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.categoryGrouping', {
					url : "/categoryGrouping",
					views : {
						"sub" : {
							templateUrl : "templates/categoryGrouping/categoryGrouping.html",
							controller : "CategoryGroupingController as vm"
						}
					}
				})
			});

})();