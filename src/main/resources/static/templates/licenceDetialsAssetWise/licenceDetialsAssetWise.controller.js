(function() {
	'use strict';

	angular.module('myApp.licenceDetialsAssetWise').controller('LicenceDetialsAssetWiseController', LicenceDetialsAssetWiseController);

	LicenceDetialsAssetWiseController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function LicenceDetialsAssetWiseController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var licenceUrl = ApiEndpoint.url+"licence";
		var assetUrl = ApiEndpoint.url+"asset";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var sId=$stateParams.id;
		console.log("State Param sId:"+sId)

		var vm = angular.extend(this, {
			showOther:showOther
		});

		(function activate() {
		
			assetInfo()
			loadTypes();
			
		})();
		
		function showOther(licence){
			console.log("licence "+JSON.stringify(licence));

			
		}
		vm.labels={'srNo': 'Sr No','associate.associateName': 'Associate','product.productName': 'Product','productVersion': 'Version','asset.make':'Make','asset.model':'Model','asset.serialNo':'Serial No','asset.assetId':'Asset Id ','computerName':'Computer Name', 'detectedDate':'Detected Date'}
		$scope.newExcel= function(associate){
				if(vm.lists==undefined){
					var msg="";
					var 	url=licenceUrl+"/getInstallLicenceBYProductNameProduct";
					var obj={};
					obj.inputStr=associate.associateName;
					console.log("url "+url)
					genericFactory.add(msg,url,obj).then(function(response) {
						vm.lists= response.data;
						// document.getElementById('btnExport').click();
						 setTimeout(function(){
							 
							 //
								 document.getElementById('btnExport').click();
								 $rootScope.loader=false;
							  $rootScope.$digest();
							},1000);
							console.log("lists "+JSON.stringify(vm.lists))

										
					});
				}else{
					 document.getElementById('btnExport').click();

				}
			// $rootScope.loader=true;
			//vm.employee.dateOfBirth=new Date(employee.dateOfBirth)
				
			
		}
		function loadTypes(){
			var msg="";
			var 	url=licenceUrl+"/AssociatedAndCountByAssetId?assetId="+sId;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.types = response.data;
				console.log("types: "+JSON.stringify(vm.types))
								
			});
			
			
			
		}
		
		function assetInfo(){
			var msg="";
			var 	url=assetUrl+"/getAssetById?id="+sId;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.asset = response.data;
				console.log("asset: "+JSON.stringify(vm.asset))
								
			});
			
			
			
		}
		
		function getDataByTypes(type){
			var msg="";
			var 	url=licenceUrl+"/getProductWithCountByAssociates?associateName="+type;
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
			var 	url=licenceUrl+"/getInstallLicenceBYProductNameProduct";
			var obj={};
			obj.inputStr=$scope.selassociateNames
		
			console.log("url "+url)
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.lists= response.data;
			
								
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
