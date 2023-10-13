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
			if(report.detectedDate==""||report.detectedDate==undefined){
				$scope.dateErr=true;
				return;
			}else{
				$scope.dateErr=false;
			}
			
			
			var url =softwareUrl+"/getInstallLiceneceByDate";
			var msg=""
				var obj={}
			obj.date=report.detectedDate;
			genericFactory.add(msg,url,obj).then(function(response) {
				vm.reports= response.data;
				console.log("reports: "+JSON.stringify(vm.reports[0]))
				
								
			})

			
			
		}
		function cancle(){
			$scope.showDatatable=false
		}
		
		$scope.file="Customer"
			vm.labels={'srNo':'Sr No','publisher':'Associate Name','applicationName': 'Product  Name','version': 'Version','edition': 'Edition','asset.make':'Make','asset.model':'Model','asset.serialNo':'Serial No','computeName':'Computer Name','asset.employeeNo':'Employee No','asset.employeeName':'Employee Name','insDate':'Install Date'}
		
		
		
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
