(function(){
	'use strict';

	angular.module('myApp.help',[

		])
	.config(function($stateProvider){
		$stateProvider
		.state('main.help', {
            url: "/help",
            views: {
                "sub": {templateUrl: "templates/help/help.html",
                		controller : "helpController as vm"}
            }
        })
        .state('main.guide', {
            url: "/guide",
            views: {
                "sub": {templateUrl: "templates/help/employee.html",
                		controller : "helpController as vm"}
            }
        })
        
        .state('main.uploadEmployee', {
            url: "/uploadEmployee",
            views: {
                "sub": {templateUrl: "templates/help/uploadEmployeeList.html",
                		controller : "helpController as vm"}
            }
        })
        
        .state('main.uploadTag', {
            url: "/uploadTag",
            views: {
                "sub": {templateUrl: "templates/help/uploadTagList.html",
                		controller : "helpController as vm"}
            }
        })
        
         .state('main.uploadasset', {
            url: "/uploadasset",
            views: {
                "sub": {templateUrl: "templates/help/uploadasset.html",
                		controller : "helpController as vm"}
            }
        })
        
         .state('main.checkListadp', {
            url: "/checkListadp",
            views: {
                "sub": {templateUrl: "templates/help/checkListadp.html",
                		controller : "helpController as vm"}
            }
        })
        
         .state('main.dashboard', {
            url: "/dashboard",
            views: {
                "sub": {templateUrl: "templates/help/dashboard.html",
                		controller : "helpController as vm"}
            }
        })
        
        .state('main.master', {
            url: "/master",
            views: {
                "sub": {templateUrl: "templates/help/master.html",
                		controller : "helpController as vm"}
            }
        })
        
         .state('main.transcation', {
            url: "/transcation",
            views: {
                "sub": {templateUrl: "templates/help/transcation.html",
                		controller : "helpController as vm"}
            }
        })
        
         .state('main.report', {
            url: "/report",
            views: {
                "sub": {templateUrl: "templates/help/report.html",
                		controller : "helpController as vm"}
            }
        })
	});

})();