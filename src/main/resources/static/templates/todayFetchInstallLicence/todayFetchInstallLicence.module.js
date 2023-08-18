(function() {
	'use strict';

	angular
	.module('myApp.todayFetchInstallLicence', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.todayFetchInstallLicence', {
					url : "/todayFetchInstallLicence",
					views : {
						"sub" : {
							templateUrl : "templates/todayFetchInstallLicence/todayFetchInstallLicence.html",
							controller : "TodayFetchInstallLicenceController as vm"
						}
					}
				})
			});

})();