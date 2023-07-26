(function() {
	'use strict';

	angular
	.module('myApp.emailTemplate', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.emailTemplate', {
					url : "/emailTemplate",
					views : {
						"sub" : {
							templateUrl : "templates/emailTemplate/emailTemplate.html",
							controller : "EmailTemplateController as vm"
						}
					}
				})
			});

})();