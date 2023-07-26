(function() {
	'use strict';

	angular
	.module('myApp.unAllocatedAssetDetectByReader', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.unAllocatedAssetDetectByReader', {
					url : "/unAllocatedAssetDetectByReader",
					views : {
						"sub" : {
							templateUrl : "templates/unAllocatedAssetDetectByReader/unAllocatedAssetDetectByReader.html",
							controller : "UnAllocatedAssetDetectByReaderController as vm"
						}
					}
				})
			});

})();