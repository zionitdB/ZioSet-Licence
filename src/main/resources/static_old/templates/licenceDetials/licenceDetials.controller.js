(function() {
	'use strict';

	angular.module('myApp.licenceDetials').controller('LicenceDetialsController', LicenceDetialsController);

	LicenceDetialsController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceDetialsController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
		
			
			loadTypes();
			
		})();
		
		function loadTypes(){
			var msg="";
			var 	url=licenceUrl+"/getLicenceTypeWiseCount";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.types = response.data;
				console.log("types: "+JSON.stringify(vm.types))
								
			});
			
			
			
		}
		
		function getDataByTypes(type){
			var msg="";
			var 	url=licenceUrl+"/getAssociateWithCountByType?type="+type;
			console.log("URL "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.associateNames = response.data;
				console.log("associateNames"+JSON.stringify(vm.associateNames))
				if(vm.associateNames.length!=0){
					$scope.showAsso=true
				}
								
			});
		}
		
		$scope.selectQR = function (index) {
			vm.types[index].check = !vm.types[index].check;
			console.log("Type"+JSON.stringify(vm.types[index].licenceType))
			
			 
			
			
			
			if(vm.types[index].check){
				$scope.selTypeName=vm.types[index].licenceType
				 getDataByTypes(vm.types[index].licenceType)

			}
			for(var i=0;i<=vm.types.length;i++){
				if(i!=index){
					vm.types[i].check=false
				}
			}

			
		}
		
		$scope.selectAssocaiation = function (index) {
			vm.associateNames[index].check = !vm.associateNames[index].check;
			console.log("Type"+JSON.stringify(vm.associateNames[index].associateName))
			// getDataByTypes(vm.types[index].typeName)
			if(vm.associateNames[index].check){
				$scope.selassociateNames=vm.associateNames[index].associateName
				getDataByTypeAndAssociate()
			}

		
			
		}
		function getDataByTypeAndAssociate(){
			var msg="";
			var 	url=licenceUrl+"/getProductNameWithCountByTypeAssociate?type="+$scope.selTypeName+"&associateName="+$scope.selassociateNames;
			
			console.log("url "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.products= response.data;
				console.log("productNames"+JSON.stringify(vm.products))
								
			});
		}
		
		$scope.selectProduct = function (index) {
			vm.products[index].check = !vm.products[index].check;
			console.log("Type"+JSON.stringify(vm.products[index]))
			// getDataByTypes(vm.types[index].typeName)
			if(vm.products[index].check){
				$scope.selProductName=vm.products[index].productName
				getDataByTypeAndAssociateAndProductName()
			}

		
			
		}
		function getDataByTypeAndAssociateAndProductName(){
			var msg="";
			var 	url=licenceUrl+"/getAllLicencceByTypeAndAssociateAndProduct?type="+$scope.selTypeName+"&associateName="+$scope.selassociateNames+"&productName="+$scope.selProductName;
			
			console.log("url "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.lists= response.data;
				console.log("lists"+JSON.stringify(vm.lists))
								
			});
		}
		
	}

	
})();
