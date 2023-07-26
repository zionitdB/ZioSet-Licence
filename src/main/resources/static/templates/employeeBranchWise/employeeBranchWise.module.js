(function() {
	'use strict';

	angular
	.module('myApp.employeeBranchWise', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.employeeBranchWise/:branchId', {
					url : "/employeeBranchWise/:branchId",
					views : {
						"sub" : {
							templateUrl : "templates/employeeBranchWise/employeeBranchWise.html",
							controller : "EmployeeBranchWiseController as vm"
						}
					}
				})
			});

})();