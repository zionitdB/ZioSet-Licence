(function() {
	'use strict';

	angular.module('myApp.systemDashboard').controller('SystemDashboardController', SystemDashboardController);

	SystemDashboardController.$inject = [ '$state', '$log', '$scope', 'toastr',
			'localStorageService', 'ApiEndpoint', 'loginFactory',
			'genericFactory', '$interval', '$location', '$http','$rootScope' ];

	/* @ngInject */
	function SystemDashboardController($state, $log, $scope, toastr, localStorageService,ApiEndpoint, loginFactory, genericFactory, $interval, $location,$http,$rootScope) {
		
		var dashboardUrl = ApiEndpoint.url + "dashboard";

		$scope.totalAsset = 0;
		var vm = angular.extend(this, {
			

		});
		
			(function activate() {
				$scope.selTab="system"
					loadODDetails();
				loadCPUDetails();
				loadMemoryDetails();
				loadDashboardCount()
		
		})();
			
			$scope.goToOverview=function (){
				$location.path('main/home');
				//$scope.selTab="home"
			}
			$scope.goToSAAS=function (){
				$location.path('main/saasLicenceDashboard');
				//$scope.selTab="licence"
			}
			$scope.goToInstalled=function (){
				$location.path('main/installLicenceDashboard');
				//$scope.selTab="home"
			}
			$scope.goToSystem=function (){
				$location.path('main/systemDashboard');
				//$scope.selTab="licence"
			}
			
			function loadDashboardCount() {
				var msg = ""
				var url = dashboardUrl + "/getOverviewDashboardCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.overviewDashBoardCount = response.data;

							

						});
			}
			
			$scope.systemDetials=function (){
				$location.path('main/systemDetails');
				//$scope.selTab="home"
			}
			
			
			function loadODDetails(){
				var msg="";
				var 	url=dashboardUrl+"/getCPUWiseCount";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.cpuCounts = response.data;
					$scope.cpuNames=vm.cpuCounts.names
					$scope.cpuCounts=vm.cpuCounts.counts
					console.log("cpuCounts: "+JSON.stringify(vm.cpuCounts))
									
				});
			}
		
			function loadCPUDetails(){
				var msg="";
				var 	url=dashboardUrl+"/getOSWiseCount";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.oscounts = response.data;
					$scope.osNames=vm.oscounts.names
					$scope.osCounts=vm.oscounts.counts
					console.log("oscounts: "+JSON.stringify(vm.oscounts))
									
				});
			}
		
			function loadMemoryDetails(){
				var msg="";
				var 	url=dashboardUrl+"/getMemoryWiseCount";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.memorycounts = response.data;
					$scope.memoryNames=vm.memorycounts.names
					$scope.memoryCounts=vm.memorycounts.counts
					console.log("memorycounts: "+JSON.stringify(vm.memorycounts))
									
				});
			}
		
		

	

		
		
	}
})();
