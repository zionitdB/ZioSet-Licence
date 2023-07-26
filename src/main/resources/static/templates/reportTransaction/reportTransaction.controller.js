(function() {
	'use strict';

	angular.module('myApp.reportTransaction').controller('ReportTransactionController2', ReportTransactionController2);

	ReportTransactionController2.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportTransactionController2($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage:10,
			getData:getData,
			cancle:cancle
		});

		(function activate() {
			$scope.showDatatable=false
		})();
		
		
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
			vm.labels={'srNo':'Sr No','asset.assetType':'Asset Type','asset.serialNo': 'SerialNo','asset.assetId': 'Asset Id','asset.make':'Make','asset.model':'Model','tranactionType':'Transaction Type','employee.employeeNo':'Employee No','employee.username':'NTID','employee.employeeName':'Employee Name','tDate':'Transaction Date'}
		
		
		
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
