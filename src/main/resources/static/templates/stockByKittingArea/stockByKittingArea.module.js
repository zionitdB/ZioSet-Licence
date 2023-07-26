(function() {
	'use strict';

	angular
	.module('myApp.stockByKittingArea', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.stockByKittingArea', {
					url : "/stockByKittingArea",
					views : {
						"sub" : {
							templateUrl : "templates/stockByKittingArea/stockByKittingArea.html",
							controller : "StockByKittingAreaController as vm"
						}
					}
				})
			});

})();