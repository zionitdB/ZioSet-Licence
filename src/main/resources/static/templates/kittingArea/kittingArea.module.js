(function() {
	'use strict';

	angular
	.module('myApp.kittingArea', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.kittingArea', {
					url : "/kittingArea",
					views : {
						"sub" : {
							templateUrl : "templates/kittingArea/kittingArea.html",
							controller : "KittingAreaController as vm"
						}
					}
				})
			});

})();