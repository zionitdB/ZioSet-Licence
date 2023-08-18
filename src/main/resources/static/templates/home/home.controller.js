(function() {
	'use strict';

	angular.module('myApp.home').controller('HomeController', HomeController);

	HomeController.$inject = [ '$state', '$log', '$scope', 'toastr',
			'localStorageService', 'ApiEndpoint', 'loginFactory',
			'genericFactory', '$interval', '$location', '$http','$rootScope' ];

	/* @ngInject */
	function HomeController($state, $log, $scope, toastr, localStorageService,
			ApiEndpoint, loginFactory, genericFactory, $interval, $location,
			$http,$rootScope) {
		var dailyTransactionUrl = ApiEndpoint.url + "dailyTransaction";
		var dashboardUrl = ApiEndpoint.url + "dashboard";
		var licenceUrl = ApiEndpoint.url + "licence";

		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		$scope.totalAsset = 0;
		var vm = angular.extend(this, {
			user : userDetail,
			branches:[],
			bundleWiseSAASPieName:[],
			bundleWiseSAASPieCount:[],
			totalBundleSaas:0,
			bundleWiseInstallPieName:[],
			bundleWiseIntsallPieCount:[],
			totalBundleIntsall:0,
			
			
			cateWiseInstallPieName:[],
			cateWiseIntsallPieCount:[],
			totalcateIntsall:0,
			
			cateWiseSAASPieName:[],
			cateWiseSAASPieCount:[],
			totalcateSaas:0,
		});
		
			(function activate() {
				$scope.selTab="home"
				$rootScope.branchId=0
				loadDashboardCount();
				loadAllAssetStatus();
				loadAllAssetTypes();
				loadBundleWiseInstallLicence();
				loadCategoryWiseInstallLicence();
				loadBundleWiseSAASLicence();
				loadCategoryWiseSAASLicence();
				
				loadBundleWiseComparison();
				loadCategoryWiseComparison();
				
				loadLicencePercentage();

				loadBundlePieChartSAASData();
				loadBundlePieChartInstallData();
				
				loadCategoryPieChartSaaSData();
				loadCategoryPieChartInstallData()
				saaslicencesByAssociate();
				installlicencesByAssociate()
				loadWorkerActivePercrntage()
				loadDatabaseDetials();
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
			
			$scope.allAsset=function(){
				$location.path('main/asset');
			}
			$scope.gotoEmployee=function(){
				$location.path('main/employee');
			}
			
			$scope.assingedAsset=function(){
				$location.path('main/assetEmployeeMapped');
			}
			
			$scope.assingedAsset=function(){
				$location.path('main/assetEmployeeMapped');
			}
			
			
			$scope.installLicence=function(){
				$location.path('main/InstallLicence');
			}
			
			$scope.saasLicence=function(){
				$location.path('main/licence');
			}
			
			$scope.assingedLicence=function(){
				$location.path('main/licenceAllocation');
			}
			
			$scope.customerSuppliedSoftware=function(){
				$location.path('main/customerSuppliedSoftware');
			}
			
			$scope.saasLiceneExpiry=function(){
				$location.path('main/saasLiceneExpiry');
			}
			
			$scope.eodInstall=function(){
				$location.path('main/installedLiceneExpiry');
			}
			
			$scope.renewLicence=function(){
				$location.path('main/renewalLicence');
			}
			
			$scope.todayFetchCount=function(){
				$location.path('main/todayFetchInstallLicence');
			}
			
			
			function loadDashboardCount() {
				var msg = ""
				var url = dashboardUrl + "/getOverviewDashboardCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.overviewDashBoardCount = response.data;

							

						});
			}
			function loadDatabaseDetials() {
				var msg = ""
				var url = dashboardUrl + "/getDataBaseDetial";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.databaseDetials = response.data;

							

						});
			}
			function loadWorkerActivePercrntage(){
				var msg=""
					var url = dashboardUrl + "/getWorkerPercentage";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.workerActivePercentage= response.data;
						
						$scope.activeCount=vm.workerActivePercentage.activeCount
						$scope.inActiveCount=vm.workerActivePercentage.totalCount-vm.workerActivePercentage.activeCount
						$scope.activePer=((vm.workerActivePercentage.activeCount/vm.workerActivePercentage.totalCount)*100).toFixed(2);
						$scope.inactivePer=100-$scope.activePer;
						$scope.fetchDate=vm.workerActivePercentage.date;
						console.log("workerActivePercentage: " + JSON.stringify(vm.workerActivePercentage))
						console.log("activePer: " +$scope.activePer)
						console.log("inactivePer: " +$scope.inactivePer)
						document.getElementById("activeWorkerPro").setAttribute("style","width: "+$scope.activePer+"%");
						document.getElementById("inactiveWorkerPro").setAttribute("style","width: "+$scope.inactivePer+"%");

						

					});
			}
			function saaslicencesByAssociate(){
				var msg=""
				var url = licenceUrl + "/getlicencesCountsByAssociate";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.saaslicencesAssociateGraph= response.data;
					
					
					

				});
				var msg=""
					var url = dashboardUrl + "/getPublisherWiseSAASLicenceList";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.saaslicencesAssociateList= response.data;
						
						console.log("saaslicencesAssociateList: " + JSON.stringify(vm.installlicencesAssociateGraph))

						

					});
			}
			function installlicencesByAssociate(){
				var msg=""
				var url = dashboardUrl + "/getInstalllicencesByPubslisherForGraph";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.installlicencesAssociateGraph= response.data;
					
					//console.log("installlicencesAssociateGraph: " + JSON.stringify(vm.installlicencesAssociateGraph))

					

				});
				var msg=""
					var url = dashboardUrl + "/getPublisherWiseInstallLicenceList";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.installlicencesAssociateList= response.data;
						
						//console.log("installlicencesAssociateList: " + JSON.stringify(vm.installlicencesAssociateList))

						

					});
			}
			function loadBundlePieChartSAASData() {
				var msg = ""
				var url = dashboardUrl + "/getBundleWiseSAASLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bundlepieSaasChart= response.data;


				});

			}
			function loadBundlePieChartInstallData() {
				var msg = ""
				var url = dashboardUrl + "/getBundleWiseInstallLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bundlepieInstalChart= response.data;

					//console.log("bundlepieChart: " + JSON.stringify(vm.bundlepieChart))

				});

			}
			
			
			function loadCategoryPieChartSaaSData() {
				var msg = ""
				var url = dashboardUrl + "/getCategoryWiseSAASLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categorySaaspieChart = response.data;

					//console.log("categorypieChart: " + JSON.stringify(vm.categorySaaspieChart))

				});

			}
			function loadCategoryPieChartInstallData() {
				var msg = ""
				var url = dashboardUrl + "/getCategoryWiseInstallLicence";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categoryInstallpieChart = response.data;

					//console.log("categoryInstallpieChart: " + JSON.stringify(vm.categoryInstallpieChart))

				});

			}
			
			
			
			
			
			
			
			function loadLicencePercentage() {
				var msg = ""
				var url = dashboardUrl + "/getLicencePercentage";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencePercentage = response.data;
					$scope.licenceins="50%";
					$scope.saasLicencePer=((vm.licencePercentage.saasPercentage/vm.licencePercentage.totalCount)*100).toFixed(2);
					$scope.installLicencePer=((vm.licencePercentage.instalPercentage/vm.licencePercentage.totalCount)*100).toFixed(2);

					document.getElementById("saasPercentagePro").setAttribute("style","width: "+$scope.saasLicencePer+"%");
					document.getElementById("installPercentagePro").setAttribute("style","width: "+$scope.installLicencePer+"%");

					console.log("licencePercentage: " + JSON.stringify(vm.licencePercentage))

				});

			}
			
			
			
			function loadBundleWiseComparison() {
				var msg = ""
				var url = dashboardUrl + "/getBundleWiseComparision";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bundleWiseComparisons = response.data;
					$scope.labelsBundle = vm.bundleWiseComparisons.names
					  $scope.seriesBundle  = ['Installed', 'SAAS'];
					  $scope.dataBundle  = [
						  vm.bundleWiseComparisons.installed,
						  vm.bundleWiseComparisons.saas
					  ];
					//console.log("bundleWiseComparisons: " + JSON.stringify(vm.bundleWiseComparisons))

				});

			}
			
			
			
			function loadCategoryWiseComparison() {
				var msg = ""
				var url = dashboardUrl + "/getCategoryWiseComparision";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categoryWiseComparisons = response.data;
					$scope.labelsCategory = vm.categoryWiseComparisons.names
					  $scope.seriesCategory  = ['Installed', 'SAAS'];
					  $scope.dataCategory  = [
						  vm.categoryWiseComparisons.installed,
						  vm.categoryWiseComparisons.saas
					  ];
					//console.log("categoryWiseComparisons: " + JSON.stringify(vm.categoryWiseComparisons))

				});

			}
			
			function loadBundleWiseInstallLicence() {
				var msg = ""
				var url = dashboardUrl + "/getBundleWiseInstallLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bundleWiseInstallLicences = response.data;
					
					angular.forEach(vm.bundleWiseInstallLicences, function (item) {
						if(item.count!=0){
							vm.totalBundleIntsall+=item.count;
						}

		            });
					angular.forEach(vm.bundleWiseInstallLicences, function (item) {
						if(item.count!=0){
							var percentage=((item.count/vm.totalBundleIntsall)*100).toFixed(2);
							vm.bundleWiseIntsallPieCount.push(percentage)
							vm.bundleWiseInstallPieName.push(item.name)

						}

		            });
					
					
					//console.log("bundleWiseInstallLicences: " + JSON.stringify(vm.bundleWiseInstallLicences))

				});

			}
			function loadCategoryWiseInstallLicence() {
				var msg = ""
				var url = dashboardUrl + "/getCategoryWiseInstallLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categoryWiseInstallLicences = response.data;
					/*cateWiseInstallPieName:[],
					cateWiseIntsallPieCount:[],
					totalcateIntsall:0,*/
					//console.log("categoryWiseInstallLicences: " + JSON.stringify(vm.categoryWiseInstallLicences))
					angular.forEach(vm.categoryWiseInstallLicences, function (item) {
						if(item.count!=0){
							vm.totalcateIntsall+=item.count;
						}

		            });
					
					angular.forEach(vm.categoryWiseInstallLicences, function (item) {
						if(item.count!=0){
							var percentage=((item.count/vm.totalcateIntsall)*100).toFixed(2);
							vm.cateWiseIntsallPieCount.push(percentage)
							vm.cateWiseInstallPieName.push(item.name)

						}

		            });

				});

			}
			
			function loadBundleWiseSAASLicence() {
				var msg = ""
				var url = dashboardUrl + "/getBundleWiseSAASLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					var bundleWiseSAASLicence=[];
					vm.bundleWiseSAASLicences = response.data;
					angular.forEach(vm.bundleWiseSAASLicences, function (item) {
						if(item.count!=0){
							vm.totalBundleSaas+=item.count;
						}

		            });
					
					angular.forEach(vm.bundleWiseSAASLicences, function (item) {
						if(item.count!=0){
							var percentage=((item.count/vm.totalBundleSaas)*100).toFixed(2);
							vm.bundleWiseSAASPieCount.push(percentage)
							vm.bundleWiseSAASPieName.push(item.name)

						}

		            });


				});

			}
			function loadCategoryWiseSAASLicence() {
				var msg = ""
				var url = dashboardUrl + "/getCategoryWiseSAASLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categoryWiseSAASLicences = response.data;

					/*cateWiseSAASPieName:[],
					cateWiseSAASPieCount:[],
					totalcateSaas:0,*/
					//console.log("categoryWiseSAASLicences: " + JSON.stringify(vm.categoryWiseSAASLicences))
					angular.forEach(vm.categoryWiseSAASLicences, function (item) {
						if(item.count!=0){
							vm.totalcateSaas+=item.count;
						}

		            });
					
					angular.forEach(vm.categoryWiseSAASLicences, function (item) {
						if(item.count!=0){
							var percentage=((item.count/vm.totalcateSaas)*100).toFixed(2);
							vm.cateWiseSAASPieCount.push(percentage)
							vm.cateWiseSAASPieName.push(item.name)

						}

		            });
				});

			}
			
			//************************************* PAI CHART  START *******************************//	
			$scope.colorsPie = [ '#90EE90', '#FF6600', '#8080FF' ];

			function loadAllAssetStatus() {
				var msg = ""
				var url = dashboardUrl + "/getStatusWiseAsset";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.statusWiseAsset = response.data;

					//console.log("statusWiseAsset: " + JSON.stringify(vm.statusWiseAsset))

				});

			}
			function loadAllAssetTypes() {
				var msg = ""
				var url = dashboardUrl + "/getTypeWiseAsset";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.typesWiseAsset = response.data;

					//console.log("typesWiseAsset: " + JSON.stringify(vm.typesWiseAsset))

				});

			}
			
			
			


		//*******************************************************************8
			
			
			

		
		
		function doLogout() {
			loginFactory.ClearCredentials();
			$state.go('login');
			localStorageService.remove(ApiEndpoint.userKey);
		}

		
		

	
		
	}
})();
