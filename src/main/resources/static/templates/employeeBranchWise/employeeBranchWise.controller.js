(function() {
	'use strict';

	angular.module('myApp.employeeBranchWise')
	.controller('EmployeeBranchWiseController', EmployeeBranchWiseController);
	EmployeeBranchWiseController.$inject = [ '$state','$compile','$uibModal',
		'$log', '$scope', 'toastr', 'localStorageService', '$timeout','ApiEndpoint','genericFactory','$rootScope','$window','$filter','$http','$stateParams'];

	
	/* @ngInject */
	function EmployeeBranchWiseController($state, $compile,$uibModal, $log,$scope, toastr, localStorageService, $timeout, ApiEndpoint , genericFactory,$rootScope,$window,$filter,$http,$stateParams) {

		var employeeUrl = ApiEndpoint.url+"employee";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		var vm = angular.extend(this, {
	
			perPage : 10,
			total_count:0,
			pageno:1,
			serachText:"",
		});

		(function activate() {
			
			loadEmployees();
		
			
		
		})();
		
		
		/*************************future date disabled**********************/
		
		$scope.searchByPagination=function (search){
			loadEmployees();
			
		}
		
		// current page
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadEmployees();
			
		}
		
		
		
		/**************************export excel*********************/
		
		$scope.file="Employees"
			vm.labels={'emoloyeeCode': 'Employee Code ', 'title': 'Title','fName': 'First Name','lName': 'Last Name','contactNo':'contactNo','emailId':'EmailId','branch.branchName':'Branch','department.departmentName':'Department','designation.designationName':'Designation','dateOfBirth':'DOB','dateOfJoining':'DOJ','workLocation':'Work Location','addedBy':'AddedBy','addedDate':'AddedDate'}
			
		
		$scope.newExcel= function(){
			getAllEmployeesForExport()
			 $rootScope.loader=true;
			//vm.employee.dateOfBirth=new Date(employee.dateOfBirth)
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
		function loadEmployees(){
			var msg=""
				var dataReq=""
			if(sId==1){
				dataReq="Pune"
			}else{
				dataReq="Bengaluru"
			}
			 var url =employeeUrl+"/getAllEmployeeListByBranch?branchName="+dataReq;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allEmployees = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		
		
		/***********************************************************/
	
		
		
		
		
		
	}
})();
