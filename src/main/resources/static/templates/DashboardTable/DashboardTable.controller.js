(function() {
	'use strict';

	angular.module('myApp.DashboardTable')
	.controller('DashboardTableController', DashboardTableController)
	.controller('BundleSaasController', BundleSaasController)
	.controller('BundleInstallController', BundleSaasController)
	.controller('CategorySAASController', CategorySAASController)
	.controller('CategoryInstallController', CategoryInstallController)
	.controller('PublisherSAASController', PublisherSAASController)
	.controller('PublisherInstallController', PublisherInstallController)
	.controller('BundleCostController', BundleCostController)
	.controller('CategoryCostController', CategoryCostController)
	.controller('PubliasherStockController', PubliasherStockController)
	.controller('SAASStockController', SAASStockController)
	.controller('ProductInstallController', ProductInstallController);

	DashboardTableController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	BundleSaasController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	BundleInstallController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	CategorySAASController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	CategoryInstallController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	PublisherSAASController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	PublisherInstallController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	BundleCostController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	CategoryCostController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	PubliasherStockController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	SAASStockController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	ProductInstallController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];

	
	
	/* @ngInject */
	function DashboardTableController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
		
		})();
		
		
	}
	
function BundleSaasController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadBundleWiseSAASLicence()
		})();
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
		
	}
function BundleInstallController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			console.log("BUNDLE INSTALL")
			loadBundleWiseInstallLicence()
		})();
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
		
	}

function CategorySAASController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadCategoryWiseSAASLicence()
		})();
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
		
	}

function CategoryInstallController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadCategoryWiseSAASLicence()
		})();
		function loadCategoryWiseSAASLicence() {
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
		
	}


function PublisherSAASController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadPublisherWiseSAASLicenceList()
		})();
		function loadPublisherWiseSAASLicenceList() {
		
			
			

			var msg=""
				var url = dashboardUrl + "/getPublisherWiseSAASLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.saaslicencesAssociateList= response.data;
					
					console.log("saaslicencesAssociateList: " + JSON.stringify(vm.installlicencesAssociateGraph))

					

				});

		}
		
	}



function PublisherInstallController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadPublisherWiseInstallLicenceList()
		})();
		function loadPublisherWiseInstallLicenceList() {
			var msg=""
				var url = dashboardUrl + "/getPublisherWiseInstallLicenceList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.installlicencesAssociateList= response.data;
					
					//console.log("installlicencesAssociateList: " + JSON.stringify(vm.installlicencesAssociateList))

					

				});
		}
		
	}
function BundleCostController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			getBundleWiseSAASCost()
		})();
		function getBundleWiseSAASCost() {
			var msg = ""
				var url = dashboardUrl + "/getBundleWiseSAASCost";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bundleWiseCost = response.data;
					

				
					console.log("bundleWiseCost" + JSON.stringify(vm.bundleWiseCost))
					

				});
			
		}
		
	}
function CategoryCostController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	
	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			loadPublisherWiseInstallLicenceList()
		})();
		function loadPublisherWiseInstallLicenceList() {
			var msg = ""
				var url = dashboardUrl + "/getCategoryWiseSAASCost";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.categoryWiseCost= response.data;
					

				
					console.log("categoryWiseCost" + JSON.stringify(vm.categoryWiseCost))
					

				});
			
		}
		
	}

function PubliasherStockController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	var licenceUrl = ApiEndpoint.url + "licence";

	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			getlicencesByPubslisher()
		})();
		function getlicencesByPubslisher() {
			var msg = ""
				var url = licenceUrl + "/getlicencesByPubslisher";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesCountByPublisher = response.data;
					//console.log("licencesCountByPublisher: " + JSON.stringify(vm.licencesCountByPublisher))

					

				});
			
		}
		
	}
function SAASStockController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	var licenceUrl = ApiEndpoint.url + "licence";

	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			getlicencesStockByPubslisher()
		})();
		function getlicencesStockByPubslisher() {
			var msg = ""
				var url = licenceUrl + "/getlicencesStockByPubslisher";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.licencesStockCountByPublisher = response.data;
					//console.log("licencesStockCountByPublisher: " + JSON.stringify(vm.licencesStockCountByPublisher))

					

				});
		}
		
	}
function ProductInstallController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
	var licenceUrl = ApiEndpoint.url + "licence";

	var dashboardUrl = ApiEndpoint.url + "dashboard";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
	
		var vm = angular.extend(this, {
			
			
		});

		(function activate() {
			
			getlicencesStockByPubslisher()
		})();
		function getlicencesStockByPubslisher() {
			var msg = ""
				var url = dashboardUrl + "/getProductWiseInstallList";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.productWiseInstallList = response.data;
					

				
					console.log("productWiseInstallList" + JSON.stringify(vm.productWiseInstallList))
					

				});

		}
		
	}
})();
