(function() {
	'use strict';

	angular.module('myApp.reportExpiryLicence').controller('ReportExpiryLicenceController', ReportExpiryLicenceController);

	ReportExpiryLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportExpiryLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle,
			getDataByTypes:getDataByTypes,
			getDataByTypeAndAssociate:getDataByTypeAndAssociate,
			getDataByTypeAndAssociateAndProductName:getDataByTypeAndAssociateAndProductName
		});

		(function activate() {
			$scope.showDatatable=false
			loadExpiryLicence()
			loadTypes()
			loadAssociation()
			loadProducts()
		})();
		function loadExpiryLicence(){
			
			
			var msg=""
				 var url =licenceUrl+"/getAllExpiryLicences";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.reports = response.data;
				console.log("reports : "+JSON.stringify(vm.reports))
								
			});
			
			
		}
	function loadTypes(){
					
			var msg=""
				 var url =licenceUrl+"/getLicenceTypes";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.types = response.data;
				console.log("types : "+JSON.stringify(vm.types))
								
			});
			
			
		}
	function loadAssociation(){
		
		var msg=""
			 var url =licenceUrl+"/getAssociates";
			genericFactory.getAll(msg,url).then(function(response) {
			vm.associations = response.data;
			console.log("associations"+JSON.stringify(vm.associations))
							
		});
		
		
	}
function loadProducts(){
		
		var msg=""
			 var url =licenceUrl+"/getProducts";
			genericFactory.getAll(msg,url).then(function(response) {
			vm.products = response.data;
			console.log("products "+JSON.stringify(vm.products))
							
		});
		
		
	}
	function getDataByTypes(type){
		var msg=""
			 var url =licenceUrl+"/getAllExpiryLicencesByTypes?type="+type;
			genericFactory.getAll(msg,url).then(function(response) {
			vm.reports = response.data;
			console.log("reports : "+JSON.stringify(vm.reports))
							
		});
		
		
	
	}
	function getDataByTypeAndAssociate(type,association){
		var msg=""
			 var url =licenceUrl+"/getAllExpiryLicencesByTypesAndAssociation?type="+type+"&association="+association;
		console.log("url : "+url)
			genericFactory.getAll(msg,url).then(function(response) {
			vm.reports = response.data;
			console.log("reports : "+JSON.stringify(vm.reports))
							
		});
		
		
	
	}
	function getDataByTypeAndAssociateAndProductName(type,association,product){
		var msg=""
			 var url =licenceUrl+"/getAllExpiryLicencesByTypesAndAssociationAndProduct?type="+type+"&association="+association+"&product="+product;
			
		console.log("url : "+url)
		genericFactory.getAll(msg,url).then(function(response) {
			vm.reports = response.data;
			console.log("reports : "+JSON.stringify(vm.reports))
							
		});
		
		
	
	}
		function getData(){
			console.log("$scope.transDate : "+JSON.stringify(vm.transDate))
			if(vm.fromDate==""||vm.fromDate==undefined){
				$scope.dateErr=true;
				return;
			}else{
				$scope.dateErr=false;
			}
			if(vm.toDate==""||vm.toDate==undefined){
				$scope.dateErr1=true;
				return;
			}else{
				$scope.dateErr1=false;
			}
			var url =assetMovementUrl+"/getTransactionByDate";
			var msg=""
				var obj={}
			obj.fromDate=vm.fromDate
			obj.toDate=vm.toDate
			console.log("obj: "+JSON.stringify(obj))
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.transactions= response.data;
				console.log("transactions: "+JSON.stringify(vm.transactions))
				if(vm.transactions.length!=0){
					$scope.showDatatable=true
				}
								
			});
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','branch.branchName':'Branch','licenceType': 'Type','associate.associateName': 'Associate','product.productName':'Product','projectName':'Project','licenceVersion':'Version','purDate':'Purchase Date','installDate':'Install Date','expDate':'Expiry Date'}
		
		
		
		$scope.newExcel= function(){
		
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
	

		
		
	}

	
})();
