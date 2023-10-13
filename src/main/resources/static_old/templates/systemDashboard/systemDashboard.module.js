(function(){
	'use strict';

	angular.module('myApp.systemDashboard',[

		])
	.config(function($stateProvider){
		$stateProvider
		.state('main.systemDashboard', {
            url: "/systemDashboard",
            views: {
                "sub": {templateUrl: "templates/systemDashboard/systemDashboard.html",
                		controller : "SystemDashboardController as vm"}
            }
        })
	});

})();