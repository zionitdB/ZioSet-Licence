(function(){
	'use strict';

	angular.module('myApp.installLicenceDashboard',[

		])
	.config(function($stateProvider){
		$stateProvider
		.state('main.installLicenceDashboard', {
            url: "/installLicenceDashboard",
            views: {
                "sub": {templateUrl: "templates/installLicenceDashboard/installLicenceDashboard.html",
                		controller : "InstallLicenceDashboardController as vm"}
            }
        })
	});

})();