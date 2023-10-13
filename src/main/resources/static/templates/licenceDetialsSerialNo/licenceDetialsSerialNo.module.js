(function() {
	'use strict';

	angular
	.module('myApp.licenceDetialsSerialNo', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.licenceDetialsSerialNo', {
					url : "/licenceDetialsSerialNo",
					views : {
						"sub" : {
							templateUrl : "templates/licenceDetialsSerialNo/licenceDetialsSerialNo.html",
							controller : "LicenceDetialsSerialNoController as vm"
						}
					}
				})
			});

})();