(function() {
	'use strict';

	angular.module('myApp.installLicenceDashboard').controller('InstallLicenceDashboardController', InstallLicenceDashboardController);

	InstallLicenceDashboardController.$inject = [ '$state', '$log', '$scope', 'toastr',
			'localStorageService', 'ApiEndpoint', 'loginFactory',
			'genericFactory', '$interval', '$location', '$http','$rootScope' ];

	/* @ngInject */
	function InstallLicenceDashboardController($state, $log, $scope, toastr, localStorageService,
			ApiEndpoint, loginFactory, genericFactory, $interval, $location,
			$http,$rootScope) {
		
		var dashboardUrl = ApiEndpoint.url + "dashboard";
		var softwareUrl=ApiEndpoint.url + "software";
		var licenceUrl=ApiEndpoint.url + "licence";
		
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,

		});
		
			(function activate() {
				$scope.selTab="install"
				loadInstallLicenceCountForLastDays();
				loadCounts()
				loadExpiryCounts();
				loadCountOfInstallLicence()
				loadCategoryWiseInstalledCount()
				loadBundleWiseInstalledCount()
				loadPublisherWiseInstalledtable()
				loadProductWiseInstalledtable()
				loadWorkerActivePercrntage();
				loadDashboardCount();
				// barThickness: 20
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
			$scope.allLicnece=function (){
				$location.path('main/InstallLicence');
				//$scope.selTab="licence"
			}
			$scope.gotoinstalledLiceneExpiry=function (){
				$location.path('main/installedLiceneExpiry');
				//$scope.selTab="licence"
			}
			$scope.workerInstallList=function(){
				$location.path('main/workerInstalled');
			}
			
			$scope.todayFetchCount=function(){
				$location.path('main/todayFetchInstallLicence');
			}
			
			$scope.gotoPublisherInstall=function(){
				$location.path('main/PublisherInstall');
			}
			
			$scope.gotoProductInstall=function(){
				$location.path('main/ProductInstall');
			}
			
			
			
			function loadDashboardCount() {
				var msg = ""
				var url = dashboardUrl + "/getOverviewDashboardCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.overviewDashBoardCount = response.data;

							

						});
			}
			function loadWorkerActivePercrntage(){
				var msg=""
					var url = dashboardUrl + "/getWorkerPercentage";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.workerActivePercentage= response.data;
						
					

						

					});
			}
			function loadPublisherWiseInstalledtable(){
				
				
				var msg = ""
					var url = dashboardUrl + "/getPublisherWiseInstallLicenceList";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.punlisherWiseInstallList = response.data;
						

					
						console.log("punlisherWiseInstallList" + JSON.stringify(vm.punlisherWiseInstallList))
						

					});
				
			}
function loadProductWiseInstalledtable(){
				
				
				var msg = ""
					var url = dashboardUrl + "/getProductWiseInstallList";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.productWiseInstallList = response.data;
						

					
						console.log("productWiseInstallList" + JSON.stringify(vm.productWiseInstallList))
						

					});
				
			}

			
			
			
			function loadBundleWiseInstalledCount(){
				var msg = ""
					var url = dashboardUrl + "/getBundleWiseInstallLicence";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.bundleWiseCounts = response.data;
						
						vm.bundleWiseCounts.barThickness=20;
					
						console.log("bundleWiseCounts" + JSON.stringify(vm.bundleWiseCounts))
						

					});
				
				
				
			}
			function loadCategoryWiseInstalledCount(){
				
				var msg = ""
					var url = dashboardUrl + "/getCategoryWiseInstallLicence";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.categoryWiseCounts = response.data;
						

					
						console.log("categoryWiseCounts" + JSON.stringify(vm.categoryWiseCounts))
						

					});
				
				
			}
			
			
			
		
		function loadInstallLicenceCountForLastDays(){
			
			var msg = ""
				var url = softwareUrl + "/getInstallLicenceCountForLastDays";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.lastFiveDateData = response.data;
					$scope.lastFiveDates =vm.lastFiveDateData.dates
					$scope.lastFiveDatesCount =vm.lastFiveDateData.counts

				
					console.log("productWithCounts" + JSON.stringify(vm.productWithCounts))
					

				});
			
			
			
		}
		
		
		
		//************************************************ Over view tabs***************************************//
	
	
		
		function loadExpiryCounts() {
			var msg = ""
			var url = licenceUrl + "/getCounrOfExpiringLicencce";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.licencesExpCount = response.data;
				
				console.log("licencesExpCount: " + JSON.stringify(vm.licencesExpCount))
				

			});
		}
		
		function loadCountOfInstallLicence() {
			var msg = ""
			var url = dashboardUrl + "/getCountOfInstallLicence";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.countOFInstallLicences = response.data;
				
				console.log("countOFInstallLicences: " + JSON.stringify(vm.countOFInstallLicences))
				

			});
		}
			function loadCounts() {
				var msg = ""
				var url = dashboardUrl + "/getInstalllicencesByPubslisherForGraph";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesAssociateGraph= response.data;
					console.log("licencesAssociateGraph " + JSON.stringify(vm.licencesAssociateGraph))
					

				});
				var url = dashboardUrl + "/getInstalllicencesByProductForGraph";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesProductGraph= response.data;
					console.log("licencesProductGraph " + JSON.stringify(vm.licencesProductGraph))
					

				});
				
			}
		
		
	}
})();
