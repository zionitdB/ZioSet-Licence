(function() {
	'use strict';

	angular
	.module('myApp.licenceDetialsCategory', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetialsCategory', {
					url : "/licenceDetialsCategory",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetialsCategory/licenceDetialsCategory.html",
							controller : "LicenceDetialsCategoryController as vm"
						}
					}
				})
			});

})();