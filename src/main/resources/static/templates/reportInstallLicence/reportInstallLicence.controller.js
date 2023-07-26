(function() {
	'use strict';

	angular.module('myApp.reportInstallLicence').controller('ReportInstallLicenceController', ReportInstallLicenceController);

	ReportInstallLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function ReportInstallLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		var assetMovementUrl = ApiEndpoint.url+"assetMovement";
		var softwareUrl = ApiEndpoint.url+"software";
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			getData:getData,
			cancle:cancle,
			callData:callData,
			getDataByAssociate:getDataByAssociate,
			getDataAssociateAndProductName:getDataAssociateAndProductName
		});

		(function activate() {
			$scope.showDatatable=false
			getInstallLicenceReport()
			loadAssociates();
			loadProducuts();
		})();
		
		
		
		
		function loadAssociates(){
			var msg="";
			var 	url=licenceUrl+"/getAssociates";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.associateNames = response.data;
				console.log("associateNames: "+JSON.stringify(vm.associateNames))
								
			});
			
			
			
		}
		
		
		function loadProducuts(){
			var msg="";
			var 	url=licenceUrl+"/getProducts";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.productNames = response.data;
				console.log("productNames"+JSON.stringify(vm.productNames))
								
			});
			
			
			
		}
		function getDataByAssociate(associateName){
		
			var msg=""
				var url =softwareUrl+"/getAllInstalledLicencceReportByAssociate?associateName="+associateName;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.reports= response.data;
					console.log("reports : "+JSON.stringify(vm.reports))

									
				});
		}
		function getDataAssociateAndProductName(associateName,productName){
			var msg=""
				var url =softwareUrl+"/getAllInstalledLicencceReportByAssociateAndProduct?associateName="+associateName+"&productName="+productName;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.reports= response.data;
					console.log("reports : "+JSON.stringify(vm.reports))

									
				});
		}
		function callData(report){
			console.log("report: "+JSON.stringify(report))
			if(report.reportBy=="Publisher"){
				getAllPublisher();
			}
			if(report.reportBy=="Licencce Name"){
				getAllLicenceName();
			}

		}
		
		
		function getAllPublisher(){
			var msg=""
			var url =softwareUrl+"/getAllPublisher";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.publishers= response.data;
				console.log("publishers : "+JSON.stringify(vm.publishers))

								
			});
		}
		function getAllLicenceName(){
			var msg=""
				var url =softwareUrl+"/getAllLicenceName";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.licencceNames= response.data;
					console.log("licencceNames : "+JSON.stringify(vm.licencceNames))

									
				});
		}
		
		function getInstallLicenceReport(){
			var msg=""
				var url =softwareUrl+"/getAllInstalledLicencceReport";
				genericFactory.getAll(msg,url).then(function(response) {
					vm.reports= response.data;
					console.log("reports : "+JSON.stringify(vm.reports))

									
				});
		}
		
		function getData(report){
			if(report==undefined||report.reportBy==undefined||report.reportBy==""){
				$scope.reportByErr=true;
				return;
			}else{
				$scope.reportByErr=false;
			}
			if(vm.report.reportBy=="Publisher"){
				if(report.publisher==""||report.publisher==undefined){
					$scope.publisherErr=true;
					return;
				}else{
					$scope.publisherErr=false;
				}
				var url =softwareUrl+"/getInstallApplicationByPublisher?publisher="+report.publisher;
				var msg=""
				genericFactory.getAll(msg,url).then(function(response) {
					vm.transactions= response.data;
					console.log("transactions: "+JSON.stringify(vm.transactions))
					
									
				});
			}
			if(vm.report.reportBy=="Detection Date"){
				if(report.detectedDate==""||report.detectedDate==undefined){
					$scope.dateErr=true;
					return;
				}else{
					$scope.dateErr=false;
				}
				
				
				var url =softwareUrl+"/getInstallApplicationByApplicationName";
				var msg=""
					var obj={}
				obj.iDate=report.detectedDate;
				genericFactory.add(msg,url,obj).then(function(response) {
					vm.transactions= response.data;
					console.log("transactions: "+JSON.stringify(vm.transactions))
					
									
				});
			}
			if(vm.report.reportBy=="Licencce Name"){
				if(report.licenceName==""||report.licenceName==undefined){
					$scope.licenceNameErr=true;
					return;
				}else{
					$scope.licenceNameErr=false;
				}
				var url =softwareUrl+"/getInstallApplicationByApplicationName?applicationName"+report.licenceName;
				var msg=""
				genericFactory.getAll(msg,url).then(function(response) {
					vm.reports= response.data;
					console.log("reports: "+JSON.stringify(vm.reports))
					
									
				});
			}

			
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','associate.associateName':'Associate Name','product.productName': 'Product  Name','productVersion': 'Version','asset.make':'Make','asset.model':'Model','asset.serialNo':'Serial No','asset.assetId':'Asset Id','insDate':'Install Date'}
		
		
		
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
