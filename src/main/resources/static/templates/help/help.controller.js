(function() {
	'use strict';

	angular.module('myApp.help').controller('helpController', helpController);

	helpController.$inject = [ '$state','$compile',
		'$log', '$scope', 'toastr', 'localStorageService', '$timeout','ApiEndpoint','genericFactory','$rootScope','$window','$filter','$http','$location'];

	/* @ngInject */
	function helpController($state, $compile, $log,$scope, toastr, localStorageService, $timeout, ApiEndpoint , genericFactory,$rootScope,$window,$filter,$http,$location) {
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			user:userDetail,
			add:add,
			adduploademployee:adduploademployee,
			adduploadTag:adduploadTag,
			adduploadAsset:adduploadAsset,
			addChecklistAdp:addChecklistAdp,
			addDashboard:addDashboard,
			addMaster:addMaster,
			addTransaction:addTransaction,
			addreport:addreport,
			addhelp:addhelp,
		});
		
		
		function add(){
			console.log("HOOOOOOOO")
			$location.path('main/guide');
			$scope.addemployee=true;
			}
		
		function adduploademployee(){
			console.log("HOOOOOOOO")
			$location.path('main/uploadEmployee');
			
			}
		
		function adduploadTag(){
			console.log("HOOOOOOOO")
			$location.path('main/uploadTag');
			
			}
		
		function adduploadAsset(){
			console.log("HOOOOOOOO")
			$location.path('main/uploadasset');
			
			}
		
		function addChecklistAdp(){
			console.log("HOOOOOOOO")
			$location.path('main/checkListadp');
			
			}
		
		function addDashboard(){
			console.log("HOOOOOOOO")
			$location.path('main/dashboard');
			
			}
		
		function addMaster(){
			console.log("HOOOOOOOO")
			$location.path('main/master');
			
			}
		

		function addTransaction(){
			console.log("HOOOOOOOO")
			$location.path('main/transcation');
			
			}
		

		function addreport(){
			console.log("HOOOOOOOO")
			$location.path('main/report');
			
			}
		
		function addhelp(){
			console.log("HOOOOOOOO")
			$location.path('main/help');
			
			}
	}
})();