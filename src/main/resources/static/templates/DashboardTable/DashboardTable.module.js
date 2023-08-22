(function() {
	'use strict';

	angular
	.module('myApp.DashboardTable', [])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.DashboardTable', {
					url : "/DashboardTable",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/DashboardTable.html",
							controller : "DashboardTableController as vm"
						}
					}
				})
				.state('main.BundleSaas', {
					url : "/BundleSaas",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/BundleSaas.html",
							controller : "BundleSaasController as vm"
						}
					}
				})
				.state('main.BundleInstall', {
					url : "/BundleInstall",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/BundleInstall.html",
							controller : "BundleInstallController as vm"
						}
					}
				})
				.state('main.CategorySAAS', {
					url : "/CategorySAAS",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/CategorySAAS.html",
							controller : "CategorySAASController as vm"
						}
					}
				})
				.state('main.CategoryInstall', {
					url : "/CategoryInstall",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/CategoryInstall.html",
							controller : "CategoryInstallController as vm"
						}
					}
				})
				.state('main.PublisherSAAS', {
					url : "/PublisherSAAS",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/PublisherSAAS.html",
							controller : "PublisherSAASController as vm"
						}
					}
				})
				.state('main.PublisherInstall', {
					url : "/PublisherInstall",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/PublisherInstall.html",
							controller : "PublisherInstallController as vm"
						}
					}
				})
				.state('main.BundleCost', {
					url : "/BundleCost",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/BundleCost.html",
							controller : "BundleCostController as vm"
						}
					}
				})
				.state('main.CategoryCost', {
					url : "/CategoryCost",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/CategoryCost.html",
							controller : "CategoryCostController as vm"
						}
					}
				})
				.state('main.PubliasherStock', {
					url : "/PubliasherStock",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/PubliasherStock.html",
							controller : "PubliasherStockController as vm"
						}
					}
				})
				.state('main.SAASStock', {
					url : "/SAASStock",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/SAASStock.html",
							controller : "SAASStockController as vm"
						}
					}
				})
				.state('main.ProductInstall', {
					url : "/ProductInstall",
					views : {
						"sub" : {
							templateUrl : "templates/DashboardTable/ProductInstall.html",
							controller : "ProductInstallController as vm"
						}
					}
				})
			});
	

})();