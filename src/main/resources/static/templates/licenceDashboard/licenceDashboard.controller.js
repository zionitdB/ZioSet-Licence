(function() {
	'use strict';

	angular.module('myApp.licenceDashboard').controller('LicenceDashboardController', LicenceDashboardController);

	LicenceDashboardController.$inject = [ '$state', '$log', '$scope', 'toastr',
			'localStorageService', 'ApiEndpoint', 'loginFactory',
			'genericFactory', '$interval', '$location', '$http','$rootScope' ];

	/* @ngInject */
	function LicenceDashboardController($state, $log, $scope, toastr, localStorageService,
			ApiEndpoint, loginFactory, genericFactory, $interval, $location,
			$http,$rootScope) {
		var dailyTransactionUrl = ApiEndpoint.url + "dailyTransaction";
		var assetLifeUrl = ApiEndpoint.url + "assetLife";
		var assetUrl = ApiEndpoint.url + "asset";
		var assetRegistationUrl = ApiEndpoint.url + "assetRegistation";
		var notificationUrl = ApiEndpoint.url + "notification";
		var dashboardUrl = ApiEndpoint.url + "dasshboard";
		var notificationUrl = ApiEndpoint.url + "notification";
		var commonUrl = ApiEndpoint.url + "common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var licenceUrl = ApiEndpoint.url + "licence";
		var softwareUrl = ApiEndpoint.url + "software";
		var customerSuppliedSoftwareUrl = ApiEndpoint.url+"customerSuppliedSoftware";

		$scope.totalAsset = 0;
		var vm = angular.extend(this, {
			user : userDetail,
			branches:[]

		});
		
			(function activate() {
				$scope.selTab="licence"
				$rootScope.branchId=0
				loadCounts()
				$scope.datasLicenceStock=[]
				$scope.datasLicenceStockLabel=[]
				$scope.datasLicenceStockLabel[0]= "Assinged";
				$scope.datasLicenceStockLabel[1]= "Instock";
				$scope.datasLicenceStockColors = [ '#90EE90', '#FF6600' ];
				loadLicenceByPublisher();
				loadLicenceByPublisherGraph()
				loadAllProductWithCount()
				loadAllPublisherWithCount()
				loadInstallLicenceCountForLastDays()
				loadCustomerSuppliedSoftwareCount()
				/*$scope.lastFiveDates =["09-05-2023","08-05-2023","07-05-2023","06-05-2023","05-05-2023"]
				$scope.lastFiveDatesCount =[1,12,6,8,11]
		*/
		})();
			$scope.customerSuppliedSoftware=function (){
				$location.path('main/customerSuppliedSoftware');
				//$scope.selTab="home"
			}
		$scope.goToAssetDashboard=function (){
			$location.path('main/home');
			//$scope.selTab="home"
		}
		$scope.goToLicenceDashboard=function (){
			$location.path('main/licenceDashboard');
			//$scope.selTab="licence"
		}
		$scope.allLicnece=function(){
			$location.path('main/AllLicence');
		}
		$scope.assignedLicnece=function(){
			$location.path('main/AssignedLicence');
		}
		$scope.instockLicence=function(){
			$location.path('main/InstockLicence');
		}
		$scope.endOfLife=function(){
			$location.path('main/endOfLifeLicence');
		}
		function loadCustomerSuppliedSoftwareCount(){
			var msg = ""
				var url = customerSuppliedSoftwareUrl + "/getCustomerSuppliedSoftwareCount";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.customerSuppliedSoftwareCount = response.data;
					

				
					console.log("customerSuppliedSoftwareCount" + JSON.stringify(vm.customerSuppliedSoftwareCount))
					

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
		function getDataForAIOPune() {
			var msg = ""
			var url = dailyTransactionUrl + "/getlast7DysTranacstionCountForBranch?branchId="+1;
			console.log("url : " + url)
			genericFactory.getAll(msg, url).then(
					function(response) {
						vm.dataAIOPunes = response.data;
						console.log("dataAIOPunes: "
								+ JSON.stringify(vm.dataAIOPunes))
								 
								$scope.labelsAIOPune =vm.dataAIOPunes.dates
								$scope.dataAIOPune =vm.dataAIOPunes.datas
					});
		}
		function loadAllProductWithCount() {
			var msg = ""
			var url = softwareUrl + "/getAllProductWithCount";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.productWithCounts = response.data;
				
			
				console.log("productWithCounts" + JSON.stringify(vm.productWithCounts))
				

			});
		}
		function loadAllPublisherWithCount() {
			var msg = ""
			var url = softwareUrl + "/getAllPublisherWithCount";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.publisherWithCounts = response.data;
						
				console.log("publisherWithCounts  " + JSON.stringify(vm.publisherWithCounts))
				

			});
		}
		
			function loadCounts() {
				var msg = ""
				var url = licenceUrl + "/getlicencesCounts";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesCount = response.data;
					
					$scope.datasLicenceStock[0]= response.data.allocatedLicence;
					$scope.datasLicenceStock[1]= response.data.instockCount;
				
					console.log("$scope.datasLicenceStock: " + JSON.stringify($scope.datasLicenceStock))
					

				});
				var url = licenceUrl + "/getAllEndOfLifeLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.eolCount = response.data.length;
					

				});
				var url = licenceUrl + "/getlicencesCountsByAssociate";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesAssociateGraph= response.data;
					
					
					console.log("licencesAssociateGraph " + JSON.stringify(vm.licencesAssociateGraph))
					

				});
				var msg = ""
					var url = licenceUrl + "/getlicencesStockByPubslisher";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.licencesStockCountByPublisher = response.data;
						console.log("licencesStockCountByPublisher: " + JSON.stringify(vm.licencesStockCountByPublisher))

						

					});
			}
		
			function loadLicenceByPublisher() {
				var msg = ""
				var url = licenceUrl + "/getlicencesByPubslisher";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesCountByPublisher = response.data;
					console.log("licencesCountByPublisher: " + JSON.stringify(vm.licencesCountByPublisher))

					

				});
			}
			
			function loadLicenceByPublisherGraph(){
				var msg = ""
					var url = licenceUrl + "/getlicencesByPubslisherForGraph";
					genericFactory.getAll(msg, url).then(
							function(response) {
								$scope.licenceByPubliaserData=response.data.data;
								$scope.licenceByPubliaserLabels=response.data.types;

							});
			}
		
		//***********************************************//


		

		
		
		function doLogout() {
			loginFactory.ClearCredentials();
			$state.go('login');
			localStorageService.remove(ApiEndpoint.userKey);
		}

		
		

	

		
		
	}
})();
