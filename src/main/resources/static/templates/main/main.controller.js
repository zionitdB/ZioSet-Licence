(function() {
	'use strict';

	angular
		.module('myApp.main')
		.controller('mainController', mainController);

		mainController.$inject = ['localStorageService', 'ApiEndpoint', '$state','loginFactory','$rootScope','genericFactory','$timeout','$location','$scope','$window','toastr'];

	/* @ngInject */
	function mainController(localStorageService, ApiEndpoint, $state,loginFactory,$rootScope,genericFactory,$timeout,$location,$scope,$window,toastr) {
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var notificationUrl = ApiEndpoint.url+"notification";
		var assetUrl = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url + "common";
		var vm = angular.extend(this, {
			user:userDetails,
			doLogout : doLogout,
			user : userDetails,
			doLogout:doLogout,
			search:search,
		});
		
		

		(function activate() {
			$rootScope.menuBranch=0
			loadBranches()
		//	$window.onbeforeunload=call();
			//$scope.day = moment();
			//console.log("user       "+JSON.stringify(vm.user));
			//toastr.success("hiii");
			
			$rootScope.loader=false;
			//console.log("LOGIN DETIALSS "+JSON.stringify(vm.user));
			loadNotificationCount();
			getAllAsset();
			setInterval(function(){ 
				var msg=""
					 var url =notificationUrl+"/getNotificationCount";
					genericFactory.getAll(msg,url).then(function(response) {
						$rootScope.notificationCount = response.data;
						//console.log("$rootScope.notificationCount     "+JSON.stringify($rootScope.notificationCount));
					
					
				});
					
				
				
			}, 3000);
			// $timeout(loadNotificationCount(), 1000);
		})();
		
		
		function loadBranches() {
			vm.branches=[]
			var msg = ""
			var url = commonUrl + "/getAllBranches";
			//console.log("url: " + url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.bras = response.data;
				//console.log("vm.brans : " + JSON.stringify(vm.bras))
				var branch={}
				branch.branchId=0
				branch.branchName="All"
					vm.branches.push(branch);
					angular.forEach(vm.bras, function (item) {
						vm.branches.push(item);
		            });
					
				//console.log("branches : " + JSON.stringify(vm.branches))

			});
		}
		
		$rootScope.changeBranch=function(branchId){
			//console.log("vm.brans : " + JSON.stringify(vm.bras))
			$rootScope.menuBranch=branchId
		}
		function call(){
			alert("hiii am closing")
		}
		   // $window.onbeforeunload =  $scope.onExit;
		// ******************************************************
		function search(selecteAsset){
			//console.log("selecteAsset "+JSON.stringify(selecteAsset));
			$location.path('main/assetInfo/'+selecteAsset.id);
		}
		function loadNotificationCount(){
			 var msg=""
				 var url =notificationUrl+"/getNotificationCount";
				genericFactory.getAll(msg,url).then(function(response) {
					$rootScope.notificationCount = response.data;
					//console.log("$rootScope.notificationCount     "+JSON.stringify($rootScope.notificationCount));
				
				
			});
		}
	
		function getAllAsset(){
			var msg=""
				 var url =assetUrl+"/getAllAsseta1";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.allAssets = response.data;
					//console.log("allAssets  :"+JSON.stringify(vm.allAssets))
				
			});
		}
		function doLogout (){
			loginFactory.ClearCredentials();
			$state.go('login');
			localStorageService.remove(ApiEndpoint.userKey);
		}

	}
})();
