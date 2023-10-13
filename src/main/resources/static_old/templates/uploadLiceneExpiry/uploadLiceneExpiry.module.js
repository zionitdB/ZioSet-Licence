(function() {
	'use strict';

	angular
	.module('myApp.uploadLiceneExpiry', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.uploadLiceneExpiry', {
					url : "/uploadLiceneExpiry",
					views : {
						"sub" : {
							templateUrl : "templates/uploadLiceneExpiry/uploadLiceneExpiry.html",
							controller : "UploadLiceneExpiryExpiryController as vm"
						}
					}
				})
			});

})();