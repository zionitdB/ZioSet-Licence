(function(){
	'use strict';

	angular.module('myApp.licenceDashboard',[

		])
	.config(function($stateProvider){
		$stateProvider
		.state('main.licenceDashboard', {
            url: "/licenceDashboard",
            views: {
                "sub": {templateUrl: "templates/licenceDashboard/licenceDashboard.html",
                		controller : "LicenceDashboardController as vm"}
            }
        })
	});

})();