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
		var assetLifeUrl = ApiEndpoint.url + "assetLife";
		var assetUrl = ApiEndpoint.url + "asset";
		var assetRegistationUrl = ApiEndpoint.url + "assetRegistation";
		var notificationUrl = ApiEndpoint.url + "notification";
		var dashboardUrl = ApiEndpoint.url + "dasshboard";
		var notificationUrl = ApiEndpoint.url + "notification";
		var commonUrl = ApiEndpoint.url + "common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		$scope.totalAsset = 0;
		var vm = angular.extend(this, {
			user : userDetail,
			branches:[]

		});
		
			(function activate() {
				$scope.selTab="home"
				$rootScope.branchId=0
			$scope.day = moment();
			loadBranches()
			/*loadRegisterAssetCount()
			loadUnRegisterAssetCount()
			loadAllocatedAssetCount()
			loadUnAllocatedAssetCount()
			getAllUnSeenNotifications();*/
			loadBangaloreCount();
			loadPuneCount()
			loadAssetCategoryWiseCount()
		loadAssetTypesPune()
		loadAssetTypesBengauru()
			//loadAssetTypesDatePune()
			//loadDataForGraph()
			loadAllAssetStatusForPune();
			loadAllAssetStatussForBangaluru();
			getAllAssetStatusDataForPune();
			loadAllAssetStatusDataForBangaluru()
			loadEOFCount()
			loadEOFCountCompleted()
			loadEmployeeCount()
			loadAssetInOfficeCount()
			loadNotifications()
			getDataForAIOPune();
			getDataForAIOBengaluru()
				getDataAssetByStoreLocaton()
			vm.dashBoradbranchId=0
		})();
			
			$scope.goToAssetDashboard=function (){
				$location.path('main/home');
				//$scope.selTab="home"
			}
			$scope.goToLicenceDashboard=function (){
				$location.path('main/licenceDashboard');
				//$scope.selTab="licence"
			}
			function loadBranches() {
				vm.branches=[]
				var msg = ""
				var url = commonUrl + "/getAllBranches";
				console.log("url: " + url)
				genericFactory.getAll(msg, url).then(function(response) {
					vm.bras = response.data;
					console.log("vm.brans : " + JSON.stringify(vm.bras))
					var branch={}
					branch.branchId=0
					branch.branchName="All"
						vm.branches.push(branch);
						angular.forEach(vm.bras, function (item) {
							vm.branches.push(item);
			            });
						
					console.log("branches : " + JSON.stringify(vm.branches))

				});
			}
			$scope.changeBranch=function(branchId){
				console.log("vm.brans : " + JSON.stringify(vm.bras))
				$rootScope.branchId=branchId
			}
		
		//************************************************ Over view tabs***************************************//
			function loadBangaloreCount() {
				var msg = ""
				var url = dashboardUrl + "/getBangaloreCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.bangaloreCount = response.data;
							console.log("bangaloreCount: "
									+ JSON.stringify(vm.bangaloreCount))

						});
			}
			function loadPuneCount() {
				var msg = ""
				var url = dashboardUrl + "/getPuneCount";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.puneCount = response.data;
					console.log("Pune: " + JSON.stringify(vm.puneCount))

				});
			}
			
			
			function loadEOFCount() {
				var msg = ""
				var url = assetLifeUrl + "/getEOLCount";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.eolCount = response.data;

					console.log("eolCount : " + JSON.stringify($scope.eolCount))

				});

			}
			function loadEOFCountCompleted() {
				var msg = ""
				var url = assetLifeUrl + "/getEOLCount2";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.eolCount2 = response.data;

					console.log("eolCount : " + JSON.stringify($scope.eolCount2))

				});

			}
			
			function loadAssetInOfficeCount() {
				var msg = ""
				var url = dailyTransactionUrl + "/getAssetInOfficeCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.assetInOfficce = response.data;

							console.log("assetInOfficce: "
									+ JSON.stringify(vm.assetInOfficce))

						});
			}
			function loadEmployeeCount() {
				var msg = ""
				var url = dashboardUrl + "/getEmployeeCount";
				genericFactory.getAll(msg, url).then(function(response) {
					vm.empCount = response.data;

					console.log("empCount: " + JSON.stringify(vm.empCount))

				});
			}
			
			
			$scope.allPuneAsset = function() {
				$location.path('main/AllAsset/' + 1);
				// $location.path("main/AllAsset/:1")
			}
			$scope.allBengaluruAsset = function() {
				$location.path('main/AllAsset/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.UnRegisterAssetPuneAsset = function() {
				$location.path('main/UnRegisterAsset/' + 1);
				// $location.path("main/AllAsset/:1")
			}
			$scope.UnRegisterAssetBengaluruAsset = function() {
				$location.path('main/UnRegisterAsset/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.eolPune = function() {
				$location.path('main/endOfLifeReport/' + 1);
				// $location.path("main/AllAsset/:1")
			}

			$scope.eolBengaluru = function() {
				$location.path('main/endOfLifeReport/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.assetInOfficePune = function() {
				$location.path('main/assetInOfficeReport/' + 1);
				// $location.path("main/AllAsset/:1")
			}

			$scope.assetInOfficeBengaluru = function() {
				$location.path('main/assetInOfficeReport/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.eolPuneCompleted = function() {
				$location.path('main/eol/' + 1);
				// $location.path("main/AllAsset/:1")
			}

			$scope.eolBengaluruCompleted = function() {
				$location.path('main/eol/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.allocatedPuneAsset = function() {
				$location.path('main/allocatedAsset/' + 1);
				// $location.path("main/AllAsset/:1")
			}
			$scope.allocatedBengaluruAsset = function() {
				$location.path('main/allocatedAsset/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.unAllocatedPuneAsset = function() {
				$location.path('main/unAllocatedAsset/' + 1);
				// $location.path("main/AllAsset/:1")
			}
			$scope.unAllocatedBengaluruAsset = function() {
				$location.path('main/unAllocatedAsset/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			$scope.employeesPune = function() {
				$location.path('main/employeeBranchWise/' + 1);
				// $location.path("main/AllAsset/:1")
			}
			$scope.employeesBengaluru = function() {
				$location.path('main/employeeBranchWise/' + 2);
				// $location.path("main/AllAsset/:1")
			}

			
			
			
			
			
			
			
			
			//************************************* PAI CHART  START *******************************//	
			$scope.colorsPie = [ '#90EE90', '#FF6600', '#8080FF' ];

			function loadAllAssetStatusForPune() {
				var msg = ""
				var url = dashboardUrl + "/getAllAssetStatusForPune";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.labelsPune = response.data;

					console.log("AssetTypes: " + JSON.stringify($scope.labelsPune))

				});

			}
			
			function getAllAssetStatusDataForPune() {
				var msg = ""
				var url = dashboardUrl + "/getAllAssetStatusDataForPune";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.datasPune = response.data;

					console.log("data: " + JSON.stringify($scope.datasPune))

				});

			}
			
			//PieDataSetOverride is used to draw lines to display the labels

			// $scope.PieDataSetOverride = [{ yAxisID: 'y-axis-1' }]; //y-axis-1 is the ID defined in scales under options.
			$scope.optionsPie = {
				legend : {
					display : true
				},
				responsive : true, // set to false to remove responsiveness. Default responsive value is true.
			/*scales: {
			    yAxes: [
			      {
			          id: 'y-axis-1',
			          type: 'linear',
			          display: true,
			          position: 'left'
			      }]
			} */
			}
			
			function loadAllAssetStatussForBangaluru() {
				var msg = ""
				var url = dashboardUrl + "/getAllAssetStatusForBangaluru";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.labelsBengaluru= response.data;

					console.log("labels1: " + JSON.stringify($scope.labelsBengaluru))

				});

			}
			function loadAllAssetStatusDataForBangaluru() {
				var msg = ""
				var url = dashboardUrl + "/getAllAssetStatusDataForBangaluru";
				genericFactory.getAll(msg, url).then(function(response) {
					$scope.datasBengaluru = response.data;

					console.log("data1: " + JSON.stringify($scope.labelsBengaluru))

				});

			}
			//************************************* PAI CHART  END *******************************//		
			
			//************************************* Asset Status BY type ******************//
			function loadAssetCategoryWiseCount() {
				var msg = ""
				var url = dashboardUrl + "/getAssetCategoryWiseCount";
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.assetCategoryWiseCounts = response.data;
							console.log("AssetCategoryWiseCount: "
									+ JSON.stringify(vm.assetCategoryWiseCounts))

						});
			}
			
			
			//************************************* Asset In Officce Graph ******************//
			
			
			

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
			function getDataForAIOBengaluru() {
				var msg = ""
				var url = dailyTransactionUrl + "/getlast7DysTranacstionCountForBranch?branchId="+2;
				console.log("url : " + url)
				genericFactory.getAll(msg, url).then(
						function(response) {
							vm.dataAIOBengaluru = response.data;
							console.log("dataAIOBengaluru: "
									+ JSON.stringify(vm.dataAIOBengaluru))
									 
									$scope.labelsAIOBengaluru =vm.dataAIOBengaluru.dates
									$scope.dataAIOBengaluru =vm.dataAIOBengaluru.datas
						});
			}
			

			/*$scope.labelsAIOPune = [ 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
					'Friday', 'Saturday', 'Sunday' ];
			$scope.dataAIOPune = [ 
					[ 28, 48, 40, 19, 86, 27, 90 ] ];*/

			/*$scope.datasetOverride2 = [ {
				label : "Bar chart",
				borderWidth : 1,
				type : 'bar'
			}, {
				label : "Line chart",
				borderWidth : 3,
				hoverBackgroundColor : "rgba(255,99,112,0.4)",
				hoverBorderColor : "rgba(25,112,132,1)",
				type : 'line'
			} ];

			$scope.SelectedEvent = null;
			var isFirstTime = true;

			$scope.events = [];
			$scope.eventSources = [ $scope.events ];

			//Load events from server
			$http.get('/home/getevents', {
				cache : true,
				params : {}
			}).then(function(data) {
				$scope.events.slice(0, $scope.events.length);
				angular.forEach(data.data, function(value) {
					$scope.events.push({
						title : value.Title,
						description : value.Description,
						start : new Date(parseInt(value.StartAt.substr(6))),
						end : new Date(parseInt(value.EndAt.substr(6))),
						allDay : value.IsFullDay
					});
				});
			});

			//configure calendar
			$scope.uiConfig = {
				calendar : {
					height : 450,
					editable : true,
					displayEventTime : false,
					header : {
						left : 'month basicWeek basicDay agendaWeek agendaDay',
						center : 'title',
						right : 'today prev,next'
					},
					eventClick : function(event) {
						$scope.SelectedEvent = event;
					},
					eventAfterAllRender : function() {
						if ($scope.events.length > 0 && isFirstTime) {
							//Focus first event
							uiCalendarConfig.calendars.myCalendar.fullCalendar(
									'gotoDate', $scope.events[0].start);
						}
					}
				}
			};*/

		//*******************************************************************8
			
			
			
			function loadAssetTypesPune(){
				var msg = ""
					var url = assetUrl + "/loadAssetTypesDateByBranch?branchId="+1;
					genericFactory.getAll(msg, url).then(
							function(response) {
								$scope.assetTypesDataPune=response.data.data;
								$scope.assetTypeslabelsPune=response.data.types;

							});
			}
			function loadAssetTypesBengauru(){
				var msg = ""
					var url = assetUrl + "/loadAssetTypesDateByBranch?branchId="+2;
					genericFactory.getAll(msg, url).then(
							function(response) {
								vm.assetTypesBengauru = response.data;
								$scope.assetTypesDataBengaluru=response.data.data;
								$scope.assetTypeslabelsBengaluru=response.data.types;
								console.log("assetTypesBengauru: "
										+ JSON.stringify(vm.assetTypesBengauru))

							});
			}
			$scope.colorsLocations = [ '#3226ae', '#3226aeb0', '#3226aeb10','#3226ae2e', '#ff1e72' ];
			function getDataAssetByStoreLocaton(){
				var msg = ""
					var url = assetUrl + "/loadAssetByStoreLocation";
					genericFactory.getAll(msg, url).then(
							function(response) {
								vm.assetStoreLocationwise = response.data;
							/*	$scope.assetTypesDataBengaluru=response.data.data;
								$scope.assetTypeslabelsBengaluru=response.data.types;
							*/	console.log("assetStoreLocationwise: "
										+ JSON.stringify(vm.assetStoreLocationwise))
										$scope.puneLocations=vm.assetStoreLocationwise.puneData.types;
										$scope.puneCounts=vm.assetStoreLocationwise.puneData.data;
										$scope.bengaluruLocations=vm.assetStoreLocationwise.bengaluruDate.types;
										$scope.bengaluruCounts=vm.assetStoreLocationwise.bengaluruDate.data;
										console.log("$scope.puneLocations: "+ JSON.stringify($scope.puneLocations))
											console.log("$scope.puneCounts: "+ JSON.stringify($scope.puneCounts))
												console.log("$scope.bengaluruLocations: "+ JSON.stringify($scope.bengaluruLocations))
													console.log("$scope.bengaluruCounts: "+ JSON.stringify($scope.bengaluruCounts))

							});
			}
			
		
			

		$scope.colors3 = [ '#45b7cd', '#ff6384', '#ff8e72','#45b7cd', '#ff1e72' ];

		$scope.labels3 = [ 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
				'Friday', 'Saturday', 'Sunday' ];
		$scope.data3 = [ [ 65, 59, 80, 81,256, 55, 40 ]];

		$scope.datasetOverride3 = [ {
			label : "Bar chart",
			borderWidth : 1,
			type : 'bar'
		}, {
			label : "Line chart",
			borderWidth : 3,
			hoverBackgroundColor : "rgba(255,99,132,0.4)",
			hoverBorderColor : "rgba(255,99,132,1)",
			type : 'line'
		} ];

		//***********************************************//


		

		
		
		function doLogout() {
			loginFactory.ClearCredentials();
			$state.go('login');
			localStorageService.remove(ApiEndpoint.userKey);
		}

		
		

	

		//****************************** NOTIFICATION **********************//
		function loadNotifications() {

			var msg = ""
			var url = notificationUrl + "/getNotificationLast10";
			genericFactory.getAll(msg, url).then(
					function(response) {
						vm.notifications = response.data;
						console.log("notification: "
								+ JSON.stringify(vm.notifications))

					});
		}
		function loadNotificationsBySearch() {

			var msg = ""
			var url = notificationUrl
					+ "/getNotificationListBySearch?serachText="
					+ vm.serachText;
			genericFactory.getAll(msg, url).then(
					function(response) {
						vm.notifications = response.data;
						console.log("notification: "
								+ JSON.stringify(vm.notifications))

					});
		}
		function read(notification) {
			notification.view_bit = 1
			var msg = "Notification updated"
			var url = notificationUrl + "/updateNotification";
			genericFactory.add(msg, url, notification).then(function(response) {
				loadNotificationCount();
				loadNotifications()

			});
		}

		$scope.showNotification = function(notification) {
			$scope.selNotificaion = notification
		}
		$scope.getNotificationByDate = function(selDate) {
			var obj = {}
			obj.date = selDate
			var msg = ""
			var url = notificationUrl + "/getNotificationListByDate";
			genericFactory.add(msg, url, obj).then(
					function(response) {
						vm.notifications = response.data;

						console.log("notificationBYDate: "
								+ JSON.stringify(vm.notifications))

					});
		}
		$scope.viewNotification = function(notification) {
			notification.view_bit = 1
			var msg = "Notification updated"
			var url = notificationUrl + "/updateNotification";
			genericFactory.add(msg, url, notification).then(function(response) {
				getAllUnSeenNotifications();
				loadNotifications()

			});
		}
		function loadNotificationCount() {
			var msg = ""
			var url = notificationUrl + "/getNotificationCount";
			genericFactory.getAll(msg, url).then(
					function(response) {
						$rootScope.notificationCount = response.data;
						console.log("notification Count: "
								+ JSON.stringify($rootScope.notificationCount))

					});
		}
	}
})();
