(function(){
	'use strict';

	angular.module('myApp.saasLicenceDashboard',[

		])
	.config(function($stateProvider){
		$stateProvider
		.state('main.saasLicenceDashboard', {
            url: "/saasLicenceDashboard",
            views: {
                "sub": {templateUrl: "templates/saasLicenceDashboard/saasLicenceDashboard.html",
                		controller : "SaasLicenceDashboardController as vm"}
            }
        })
	});

})();