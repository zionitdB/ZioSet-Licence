(function() {
	'use strict';

	angular.module('myApp.updateMac').controller('UpdateMacController', UpdateMacController);

	UpdateMacController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function UpdateMacController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var tagUrl = ApiEndpoint.url+"tag";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			update:update,
			cancle:cancle
		});

		(function activate() {
			
			vm.hostname=""
		})();
		
		$scope.checkHostName=function(hostname){
			var msg=""
				 var url =tagUrl+"/checkHostNameNew?hostname="+hostname;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.hostNameRes = response.data;
				vm.tag=vm.hostNameRes.data
				console.log("hostNameRes: "+JSON.stringify(vm.hostNameRes))
				console.log("tag: "+JSON.stringify(vm.tag))
				
			});
		}
		function update(){
			
			if(vm.hostname==""||vm.hostname ==undefined){
				$scope.hostnameErr=true;
				return;
			}else{
				$scope.hostnameErr=false;
				if(vm.hostNameRes.code==500){
					return;
				}
				
			}
			if(vm.newEPC==""||vm.newEPC==undefined){
				
			}else{
				
			}
			var msg=""
				 var url =tagUrl+"/updateEPC";
				vm.tag.tagCode=vm.newEPC
				console.log("NEW tag: "+JSON.stringify(vm.tag))

				genericFactory.add(msg,url,vm.tag).then(function(response) {
					vm.tag={}
					vm.newEPC=""
						vm.hostname=""
							vm.hostNameRes.code==500
				
			});
			
		}
		function cancle(){
			vm.tag={}
			vm.newEPC=""
			$scope.hostname=""
		}
		
	}

	
})();
