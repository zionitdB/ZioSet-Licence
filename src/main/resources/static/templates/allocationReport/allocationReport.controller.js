(function() {
	'use strict';

	angular.module('myApp.allocationReport').controller('AllocationReportController', AllocationReportController);

	AllocationReportController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$filter'];
	
	/* @ngInject */
	function AllocationReportController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$filter) {
		var assetRegistationUrl  = ApiEndpoint.url+"assetRegistation";
		var reportUrl  = ApiEndpoint.url+"report";
		var employeeUrl  = ApiEndpoint.url+"employee";
		var assetUrl  = ApiEndpoint.url+"asset";
		var commonUrl = ApiEndpoint.url+"common";
		var vm = angular.extend(this, {
			reports:[],
			generateReport:generateReport,
			clear:clear,
			callData:callData,
			exportTableToExcelPackingBox:exportTableToExcelPackingBox
			
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
			loadBranch()
		})();
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		
		
		
		
		function callData(report){
			console.log("report: "+JSON.stringify(report))
			if(report.reportType=="Employee"){
				 loadEmployees(report);
			}
			if(report.reportType=="Asset"){
				loadAssets(report)
			}
		}

		function loadEmployees(report){
			var msg=""
				 var url =employeeUrl+"/getBranchWiseEmployees?branch_id="+report.branch.branchId;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employees = response.data;
				console.log("employees: "+JSON.stringify(vm.employees))
								
			});
		}
		function loadAssets(report){
			var msg=""
				console.log("report: "+JSON.stringify(report))
				 var url =assetUrl+"/getBranchWiseAssetaAssigned?branch_id="+report.branch.branchId;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				console.log("assets: "+JSON.stringify(vm.assets))
								
			});
		}
		/*$scope.file="AssetMovementReport"
			vm.labels={'emoloyeeCode': 'Employee Code ', 'fName': 'First Name','lName': 'Last Name','uhfCode':'Card No','contactNo':'contactNo','emailId':'EmailId','addedBy':'AddedBy','addedDate':'AddedDate'}*/
		$scope.file="AssetMovementReport"
			vm.labels={'srNo':'Sr No','asset.assetType': 'Asset Type','asset.make': 'Make','asset.model': 'Model','asset.serialNo': 'Serial No', 'asset.assetId': 'Asset Id','asset.purchaseOrderNo': 'Purchase Order No','asset.invoiceNo':'Invoice No','asset.invoiceDate':'Invoice Date','asset.age':'Age','employee.employeeName':'Employee Name','employee.employeeNo':'Employee no','employee.email':'Email','employee.mobileNo':'Mobile No','employee.mobileNo':'Mobile No','mapDateStr':'Allocation Date'}
		
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
				if(report==undefined||report.reportType==undefined){
				$scope.reportTypeErr=true;
					return;
				}else{
					$scope.reportTypeErr=false;
				}
				if(report.reportType=="Asset"){
					
					
					/*if(report.spec==undefined){
						$scope.assetErr=true;
							return;
						}else{
							$scope.assetErr=false;
						}*/
					 var url =reportUrl+"/getALLAssetAllocationBranchWise?branchId="+report.branch.branchId;
				}else{
					if(report.spec==undefined){
						$scope.empErr=true;
							return;
						}else{
							$scope.empErr=false;
						}
					 var url =reportUrl+"/getEmployeeWiseAllocationReport?empId="+report.spec.employeeId;

				}
				
				var msg=""
					console.log("url : "+url)

					genericFactory.getAll(msg,url).then(function(response) {
					vm.reports = response.data;
					console.log("report: "+JSON.stringify(vm.reports))
					if(vm.reports.length <= 0){
						toastr.error("Record Not Found");
					}
					
					$rootScope.loader=false;
				});
				
			}
		
			function clear(){
				$scope.report={}
				vm.reports=[]
			}

	
	}
})();
