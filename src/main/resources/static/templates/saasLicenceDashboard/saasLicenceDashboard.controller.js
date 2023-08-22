(function() {
	'use strict';

	angular.module('myApp.saasLicenceDashboard').controller('SaasLicenceDashboardController', SaasLicenceDashboardController);

	SaasLicenceDashboardController.$inject = [ '$state', '$log', '$scope', 'toastr',
			'localStorageService', 'ApiEndpoint', 'loginFactory',
			'genericFactory', '$interval', '$location', '$http','$rootScope' ];

	/* @ngInject */
	function SaasLicenceDashboardController($state, $log, $scope, toastr, localStorageService,
			ApiEndpoint, loginFactory, genericFactory, $interval, $location,
			$http,$rootScope) {
		var dailyTransactionUrl = ApiEndpoint.url + "dailyTransaction";
		var assetLifeUrl = ApiEndpoint.url + "assetLife";
		var assetUrl = ApiEndpoint.url + "asset";
		var assetRegistationUrl = ApiEndpoint.url + "assetRegistation";
		var notificationUrl = ApiEndpoint.url + "notification";
		var dashboardUrl = ApiEndpoint.url + "dashboard";
		var notificationUrl = ApiEndpoint.url + "notification";
		var commonUrl = ApiEndpoint.url + "common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var licenceUrl = ApiEndpoint.url + "licence";
		var softwareUrl = ApiEndpoint.url + "software";
		var dashboardUrl = ApiEndpoint.url + "dashboard";

		var customerSuppliedSoftwareUrl = ApiEndpoint.url+"customerSuppliedSoftware";

		$scope.totalAsset = 0;
		var vm = angular.extend(this, {
			user : userDetail,
			branches:[],
			perPage : 10,
			total_count:100,
			pageno:1,

		});
		
			(function activate() {
				$scope.selTab="saas"
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
				loadExpiryCounts()
				loadBundleWiseCount()
				loadCategoryWiseCount()
				loadDashboardCount()
				loadBundleWiseSAASCost()
				loadCategoryWiseSAASCost()
				
				/*$scope.lastFiveDates =["09-05-2023","08-05-2023","07-05-2023","06-05-2023","05-05-2023"]
				$scope.lastFiveDatesCount =[1,12,6,8,11]
		*/
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
				$location.path('main/licence');
				//$scope.selTab="licence"
			}
			$scope.assignedLicnece=function (){
				$location.path('main/AssignedLicence');
				//$scope.selTab="licence"
			}
			
			$scope.gotoInstockLicence=function (){
				$location.path('main/InstockLicence');
				//$scope.selTab="licence"
			}
			$scope.gotoSAASLicenceExprity=function (){
				$location.path('main/saasLiceneExpiry');
				//$scope.selTab="licence"
			}
			$scope.customerSuppliedSoftware=function (){
				$location.path('main/customerSuppliedSoftware');
				//$scope.selTab="licence"
			}
			$scope.renewLicence=function (){
				$location.path('main/renewalLicence');
				//$scope.selTab="licence"
			}
			
			
			$scope.gotoBundleCost=function (){
				$location.path('main/BundleCost');
				//$scope.selTab="licence"
			}
			$scope.gotoCategoryCost=function (){
				$location.path('main/CategoryCost');
				//$scope.selTab="licence"
			}
			
			
			$scope.gotoPubliasherStock=function (){
				$location.path('main/PubliasherStock');
				//$scope.selTab="licence"
			}
			$scope.gotoSAASStock=function (){
				$location.path('main/SAASStock');
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
			
			
			function loadBundleWiseSAASCost(){
				var msg = ""
					var url = dashboardUrl + "/getBundleWiseSAASCost";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.bundleWiseCost = response.data;
						

					
						console.log("bundleWiseCost" + JSON.stringify(vm.bundleWiseCost))
						

					});
				
				
				
			}
			function loadCategoryWiseSAASCost(){
				var msg = ""
					var url = dashboardUrl + "/getCategoryWiseSAASCost";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.categoryWiseCost= response.data;
						

					
						console.log("categoryWiseCost" + JSON.stringify(vm.categoryWiseCost))
						

					});
				
				
				
			}
			function loadBundleWiseCount(){
				var msg = ""
					var url = dashboardUrl + "/getBundleWiseSAASLicence";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.bundleWiseCounts = response.data;
						

					
						console.log("bundleWiseCounts" + JSON.stringify(vm.bundleWiseCounts))
						

					});
				
				
				
			}
			function loadCategoryWiseCount(){
				
				var msg = ""
					var url = dashboardUrl + "/getCategoryWiseSAASLicence";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.categoryWiseCounts = response.data;
						

					
						console.log("categoryWiseCounts" + JSON.stringify(vm.categoryWiseCounts))
						

					});
				
				
			}
			
			
		function loadCustomerSuppliedSoftwareCount(){
			var msg = ""
				var url = customerSuppliedSoftwareUrl + "/getCustomerSuppliedSoftwareCount1";
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

				
					//console.log("productWithCounts" + JSON.stringify(vm.productWithCounts))
					

				});
			
			
			
		}
		
		
		
		//************************************************ Over view tabs***************************************//
		function getDataForAIOPune() {
			var msg = ""
			var url = dailyTransactionUrl + "/getlast7DysTranacstionCountForBranch?branchId="+1;
			//console.log("url : " + url)
			genericFactory.getAll(msg, url).then(
					function(response) {
						vm.dataAIOPunes = response.data;
						//console.log("dataAIOPunes: "	+ JSON.stringify(vm.dataAIOPunes))
								 
								$scope.labelsAIOPune =vm.dataAIOPunes.dates
								$scope.dataAIOPune =vm.dataAIOPunes.datas
					});
		}
		function loadAllProductWithCount() {
			var msg = ""
			var url = softwareUrl + "/getAllProductWithCount";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.productWithCounts = response.data;
				
			
				//console.log("productWithCounts" + JSON.stringify(vm.productWithCounts))
				

			});
		}
		function loadAllPublisherWithCount() {
			var msg = ""
			var url = softwareUrl + "/getAllPublisherWithCount";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.publisherWithCounts = response.data;
						
				//console.log("publisherWithCounts  " + JSON.stringify(vm.publisherWithCounts))
				

			});
		}
		
		function loadExpiryCounts() {
			var msg = ""
			var url = licenceUrl + "/getCounrOfExpiringLicencce";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.licencesExpCount = response.data;
				
				//console.log("licencesExpCount: " + JSON.stringify(vm.licencesExpCount))
				

			});
		}
			function loadCounts() {
				var msg = ""
				var url = licenceUrl + "/getlicencesCounts";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesCount = response.data;
					
					$scope.datasLicenceStock[0]= response.data.allocatedLicence;
					$scope.datasLicenceStock[1]= response.data.instockCount;
				
					//console.log("$scope.datasLicenceStock: " + JSON.stringify($scope.datasLicenceStock))
					

				});
				var url = licenceUrl + "/getAllEndOfLifeLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.eolCount = response.data.length;
					

				});
				var url = licenceUrl + "/getlicencesCountsByAssociate";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesAssociateGraph= response.data;
					
					
					//console.log("licencesAssociateGraph " + JSON.stringify(vm.licencesAssociateGraph))
					

				});
				var msg = ""
					var url = licenceUrl + "/getlicencesStockByPubslisher";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.licencesStockCountByPublisher = response.data;
						//console.log("licencesStockCountByPublisher: " + JSON.stringify(vm.licencesStockCountByPublisher))

						

					});
			}
		
			function loadLicenceByPublisher() {
				var msg = ""
				var url = licenceUrl + "/getlicencesByPubslisher";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesCountByPublisher = response.data;
					//console.log("licencesCountByPublisher: " + JSON.stringify(vm.licencesCountByPublisher))

					

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
