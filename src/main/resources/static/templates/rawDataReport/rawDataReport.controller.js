(function() {
	'use strict';

	angular.module('myApp.rawDataReport').controller('RawDataReportController', RawDataReportController);

	RawDataReportController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$filter'];
	
	/* @ngInject */
	function RawDataReportController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$filter) {
		var assetRegistationUrl  = ApiEndpoint.url+"assetRegistation";
		var reportUrl  = ApiEndpoint.url+"report";
		var employeeUrl  = ApiEndpoint.url+"employee";
		var assetUrl  = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var vm = angular.extend(this, {
			reports:[],
			generateReport:generateReport,
			clear:clear,
			
		});

		function exportTableToExcelPackingBox(){
			 $("#table2excel").table2excel({
				
				     // exclude CSS class
				  exclude:".noExl",
				   name:"Worksheet Name",
				   filename:"Activity Log",//do not include extension
				 fileext:".xls" // file extension
				
				   });

		}


		(function activate() {
			
			
			$scope.showExport=false
			$scope.showButton=true
		
		})();
		
		
	
		

		/*$scope.file="AssetMovementReport"
			vm.labels={'emoloyeeCode': 'Employee Code ', 'fName': 'First Name','lName': 'Last Name','uhfCode':'Card No','contactNo':'contactNo','emailId':'EmailId','addedBy':'AddedBy','addedDate':'AddedDate'}*/
		$scope.file="AssetMovementReport"
			vm.labels={'srNo':'Sr No','device.branch.branchName': 'Branch','device.deviceName': 'Device Name','device.locationName': 'Device Location','device.macId': 'Device MAC Id', 'assetEpc': 'Asset EPC','assetId': 'Asset ID','asset.serialNo':'Serial No','asset.make':'Make','asset.model':'Model','message':'Message','insertDateTime':'Date'}
		
		$scope.newExcel= function(){
			
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
	
			function generateReport(report){
				//$rootScope.loader=true;
				console.log("report: "+JSON.stringify(report))
				 var url ="";
				if(report==undefined||report.fromdate==""){
					console.log("ddd")
					$scope.fromDateErr=true
					return;
				}else{
					$scope.fromDateErr=false
				}
				
				if(report.todate==undefined||report.todate ==""){
					$scope.toDateErr=true
					return;
				}else{
					$scope.toDateErr=false
					var date1=report.fromdate
					var date2=report.todate
				
						if (date1 > date2) {
							console.log("Date 1 is more");
						  $scope.toDateVal=true
						  return;
						  }else{
							  console.log("Date 2 is more");
							  $scope.toDateVal=false
						  }
					
					
				}
				
				 $rootScope.loader=true;
				var msg=""
					console.log("url : "+url)
					var url=reportUrl+"/getRowDataReportByDate"
					console.log("url : "+url)
					
							genericFactory.add(msg,url,report).then(function(response) {
							vm.rawDatas = response.data;
						
								
							 $rootScope.loader=false;
							console.log("rawData: "+JSON.stringify(vm.rawDatas))
											
						});
				
			}
		
			function clear(){
				$scope.report={}
				vm.reports=[]
			}

	
	}
})();
