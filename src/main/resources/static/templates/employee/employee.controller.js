(function() {
	'use strict';

	angular.module('myApp.employee')
	.controller('EmployeeController', EmployeeController);
	EmployeeController.$inject = [ '$state','$compile','$uibModal',
		'$log', '$scope', 'toastr', 'localStorageService', '$timeout','ApiEndpoint','genericFactory','$rootScope','$window','$filter','$http'];

	
	/* @ngInject */
	function EmployeeController($state, $compile,$uibModal, $log,$scope, toastr, localStorageService, $timeout, ApiEndpoint , genericFactory,$rootScope,$window,$filter,$http) {

		var employeeUrl = ApiEndpoint.url+"employee";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			user:userDetail,
			add:add,
			save:save,
			edit:edit,
			cancle:cancle,
			delet:delet,
			print:print,
			reload:reload,
			upload:upload,
			uploadEmployee:uploadEmployee,
			cancleDepartment:cancleDepartment,
			saveDepartment:saveDepartment,
			changeStatus:changeStatus,
			createUser:createUser,
			perPage : 10,
			total_count:0,
			pageno:1,
			serachText:"",
			loadEmployees:loadEmployees,
			empErr:{}
		});

		(function activate() {
			if($rootScope.menuBranch==0){
				$scope.type="all"
			}
			if($rootScope.menuBranch==1){
				$scope.type="pune"
			}
			if($rootScope.menuBranch==2){
				$scope.type="bengaluru"
			}
			$scope.employee={};
			$scope.alertType=false;
			loadEmployees();
			$scope.editView=false;
			$rootScope.loader=false;
			
			loadEmployeesCount();
		})();
		$rootScope.changeBranch=function(branchId){
			console.log("vm.brans : " + JSON.stringify(branchId))
			$rootScope.menuBranch=branchId
			reload()
		}
		function reload(){
			if($rootScope.menuBranch==0){
				$scope.type="all"
			}
			if($rootScope.menuBranch==1){
				$scope.type="pune"
			}
			if($rootScope.menuBranch==2){
				$scope.type="bengaluru"
			}
			loadEmployees();
		}
		
		$scope.totalEmployee=function (){
			$scope.type="all"
				loadEmployees();
		}
		$scope.puneEmployee=function (){
			$scope.type="pune"
				loadEmployees();
		}
		$scope.bengaluruEmployee=function (){
			$scope.type="bengaluru"
				loadEmployees();
		}
		function loadEmployeesCount(){
			var msg=""
				 var url =employeeUrl+"/getEmployeeCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employeesCount = response.data;
				console.log("employeesCount: "+JSON.stringify(vm.employeesCount))
								
			});
		}
		/*************************future date disabled**********************/
		
		$scope.searchByPagination=function (search){
			loadEmployees();
			
		}
		$scope.checkEmpNo=function(empNo){
			var msg=""
				 var url =employeeUrl+"/checkEmployeeNo?employeeNo="+empNo;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.empErr = response.data;
				console.log("empErr: "+JSON.stringify(vm.empErr))
								
			});
		}
		/*$scope.today = function () {
            $scope.employee.dateOfBirth= new Date();
        };
$scope.dateformat="dd-MM-yyyy";
        $scope.today();
$scope.showcalendar = function ($event) {
            $scope.showdp = true;
            $scope.dtmax = new Date();
        };
$scope.showdp = false;  
$scope.dtmax = new Date();*/
	  

		// current page
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadEmployees();
			
		}
		
		function delet(employee){
			var msg=""
				 var url =employeeUrl+"/deleteEmployee";
				genericFactory.add(msg,url,employee).then(function(response) {
					console.log("response: "+JSON.stringify(response))
					if(response.data.code==200){
						loadEmployees();
						toastr.success(response.data.message);
						$rootScope.loader=false;
						
					}else{
						toastr.error(response.data.message);
						$rootScope.loader=false;
					}
						
			});
		}
		function upload(){
			$scope.uploadtab=true;
			$scope.addNew=false;
			$scope.editView=false;
		}
		function add(){
			
			window.scrollTo(0, 0)
			$scope.addNew=true;
			$scope.uploadtab=false;
			loadBranches();
			loadDepartments();
			loadDesignations();
			loadAllMangers();
			$scope.addNewDepartment=false;
			$scope.editView=false;
			$scope.employee={}
		
		}
		function edit(employee){
					window.scrollTo(0,0);
			$scope.editView=true;
			$scope.uploadtab=false;
			$scope.employee=employee
			$scope.employee.mobileNo=parseInt(employee.mobileNo)
	
			$scope.employee.hireDate=new Date(employee.hireDate)
			$scope.addNew=true;
			loadBranches();
			loadDepartments();
			loadDesignations();
		
			loadAllMangers();
		}
		function cancle(){
			$scope.addNew=false;
			$scope.uploadtab=false;
		}
		function cancleDepartment(){
			$scope.addNewDepartment=false;
		}
		
		$scope.capitalFName=function(name){
			$scope.employee.fName= name.charAt(0).toUpperCase() + name.slice(1);
		}
		$scope.capitalLName=function(name){
			$scope.employee.lName= name.charAt(0).toUpperCase() + name.slice(1);
		}
		
		
		function createUser(employee){
			console.log("crete user employee :"+JSON.stringify(employee.active))
			if(employee.active==0){
				toastr.error("Selected Employee is Inactive ,Please make sure selected employe is Active");
				//toastr.warning("");
			}else{
			$rootScope.loader=true;
			var msg="Employee User Created successfully"
			 var url =employeeUrl+"/createUser";
			genericFactory.add(msg,url,employee).then(function(response) {
				loadEmployees();
				if(response.data.code==200){
					toastr.success(msg);
					$rootScope.loader=false;
				}else{
					toastr.error(response.data.msg);
					$rootScope.loader=false;
				}
				
					
		});	
			}
		}
		
		/**************************export excel*********************/
		
		$scope.file="Employees"
			vm.labels={'srNo':'Sr No','branch.branchName': 'Location', 'employeeType': 'Employee Type','employeeNo': 'Employee No','username': 'Username','firstName':'First Name','lastName':'Last Name','mobileNo':'Mobile No','email':'Email','team':'Teams','workLocation':'Work Location','bussinessUnit':'Bussiness Unit','manager':'Manager','hrDate':'Hire Date','department.departmentName':'Department','addDate':'AddedDate','addBy':'AddedBy'}
			
		
		$scope.newExcel= function(){
			getAllEmployeesForExport()
			 $rootScope.loader=true;
			//vm.employee.dateOfBirth=new Date(employee.dateOfBirth)
			 setTimeout(function(){
				 
				 //
				 $rootScope.loader=false;
				  $rootScope.$digest();
				},1000);		
			
		}
		function getAllEmployeesForExport(){
			var msg=""
				 var url =employeeUrl+"/getAllEmployees";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allEmployees = response.data;
				console.log("allEmployees: "+JSON.stringify(vm.allEmployees))
				document.getElementById('btnExport').click();
				 $rootScope.loader=false;
								
			});
		}
		
		
		/***********************************************************/
		function changeStatus(employee){
			console.log("employee active: "+JSON.stringify(employee.active))
			
			if(employee.active==1){
				employee.active=0
			}else{
				employee.active=1
			}
			$rootScope.loader=true;
			var msg=""
			 var url =employeeUrl+"/changeStatus";
			genericFactory.add(msg,url,employee).then(function(response) {
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
					$rootScope.loader=false;
				}else{
					toastr.error(response.data.message);
					$rootScope.loader=false;
				}
					
		});
		}
		/*****************************************************************/
		function loadBranches(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		function loadDepartments(){
			var msg=""
				 var url =employeeUrl+"/getAllDepartments";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.departments = response.data;
				console.log("department: "+JSON.stringify(vm.departments))
								
			});
		}
		function loadDesignations(){
			var msg=""
				 var url =employeeUrl+"/getDesignationList";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.designations = response.data;
				console.log("designations: "+JSON.stringify(vm.designations))
								
			});
		}
		
		
		
		
		
		function loadAllMangers(){
			var msg=""
				 var url =employeeUrl+"/getAllManagers";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.managers= response.data;
				console.log("managers: "+JSON.stringify(vm.managers))
								
			});
		}
		
		
		function loadEmployees() {
			loadCount()
			console.log("userDetail "+JSON.stringify(userDetail))
			
			var url=""
				var urlCount=""
					var msg=""
					if(vm.serachText==""||vm.serachText==undefined){
						url=employeeUrl+"/getEmployeeByLimit/"+vm.pageno+"/"+vm.perPage+"/"+$scope.type;
						urlCount=employeeUrl+"/getEmployeeCount/"+$scope.type
					}else{
						url=employeeUrl+"/getEmployeeByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&type="+$scope.type;
						urlCount=employeeUrl+"/getEmployeeCountAndSearch?searchText="+vm.serachText+"&type="+$scope.type;
					}
					genericFactory.getAll(msg,url).then(function(response) {
						vm.employees = response.data;
						
						console.log("employees: "+JSON.stringify(vm.employees))
										
					});
					
					genericFactory.getAll(msg,urlCount).then(function(response) {
						
						vm.total_count= response.data;
						console.log("total_count: "+JSON.stringify(vm.total_count))
										
					});
		}
function loadCount(){
	var dataReq=""
		console.log("userDetail"+JSON.stringify(userDetail))
		if(userDetail.role.roleId==1||userDetail.role.roleId==3||userDetail.role.roleId==4){
			dataReq="ALL"
		}else{
			dataReq=userDetail.branch.branchName
		}
			var msg=""
				 var url =""
				if($scope.search==""||$scope.search==undefined){
					url =employeeUrl+"/getEmployeeCount/"+dataReq
				}
			else{
				url=employeeUrl+"/getEmployeeCountAndSearch?search="+$scope.search+"&dataReq="+dataReq;
			}
				 //var url =employeeUrl+"/getEmployeesCount";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.total_count = response.data;
				console.log("total_count: "+JSON.stringify(vm.total_count))
								
			});
		}
		/*
		function loadEmployees(){
			
			
			loadCount();
			var msg=""
				var url="";
				if($scope.search==""||$scope.search==undefined){
					url =employeeUrl+"/getEmployeeByLimit?page_no="+vm.pageno+'&item_per_page='+vm.perPage;
				}
			else{
				url=employeeUrl+"/getEmployeeByLimitAndSearch?page_no="+vm.pageno+'&item_per_page='+vm.perPage+"&search="+$scope.search;
			}
				//
				// var url =employeeUrl+"/getAllEmployees";
				console.log("Call EMPLOYEE : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employees = response.data;
				console.log("employees: "+JSON.stringify(vm.employees))
								
			});
		}
		
		function loadCount(){
			
			var msg=""
				 var url =""
				if($scope.search==""||$scope.search==undefined){
					url =employeeUrl+"/getEmployeeCount"
				}
			else{
				url=employeeUrl+"/getEmployeeCountAndSearch?search="+$scope.search;
			}
				 //var url =employeeUrl+"/getEmployeesCount";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.total_count = response.data;
				console.log("total_count: "+JSON.stringify(vm.total_count))
								
			});
		}
		*/
		
		$scope.checkemployeeCode=function (employeeCode){
			var msg=""

				 var url =employeeUrl+"/checkEmployeeCode?employeeCode="+employeeCode;
				genericFactory.getAll(msg,url).then(function(response) {
				var employeeCodeisAvailable = response.data;
				console.log("employeeCodeisAvailable: "+JSON.stringify(employeeCodeisAvailable))
				if(employeeCodeisAvailable){
					$scope.invalidemployeeCodeERR=true;
					$scope.invalidemployeeCodemsg="Already exits"
				}else{
					$scope.invalidemployeeCodeERR=false;
				}
								
			});
		}
		$scope.checkUhfCode=function (uhfCode){
			var msg=""

				 var url =employeeUrl+"/checkUhfCode?uhfCode="+uhfCode;
				genericFactory.getAll(msg,url).then(function(response) {
				var uhfCodeisAvailable = response.data;
				console.log("uhfCodeisAvailable: "+JSON.stringify(uhfCodeisAvailable))
				if(uhfCodeisAvailable){
					$scope.invaliduhfNoERR=true;
					$scope.invaliduhfNomsg="Uhf Code already assigned to other"
				}else{
					$scope.invaliduhfNoERR=false;
				}
								
			});
		}
		
		
		$scope.validateContatNo=function (contactNo){
			
		

				if(contactNo.toString().length!=10){
					
					$scope.invalidMoNo=true;
					$scope.MoNoErr=false;
					$scope.contactNo1ErrMsg="Contact must be 10 digit only"
				}else{
					$scope.invalidMoNo=false;
					$scope.MoNoErr=false;
				}
		
		}
		$scope.validateEmail=function (mail) 
		{
			
		 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
		  {
			 $scope.emialErr=false
		  }else{
			  $scope.emailErrMsg="Invalid email address"
				  $scope.emialErr=true
		  }
			
		}
		
		/*********************date of birth********************/
		
		$scope.dob=function(employeeDob){
			
			if(employeeDob > (new Date()).getTime()){
				
				$scope.invaliddateOfBirth=true;
			//	$scope.disabledClick=true;
			}else{
				$scope.invaliddateOfBirth=false;
				//$scope.disabledClick=false;
			}
		}
		
		$scope.doj=function(employeeDoj){
			
			if(employeeDoj > (new Date()).getTime()){
				
				$scope.invaliddateOfjoin=true;
				//$scope.disabledClick=true;
			}else{
				$scope.invaliddateOfjoin=false;
			//	$scope.disabledClick=false;
			}
		}
		/*****************************save employee**************************/
		
		function save(employee){
			
			if(employee==undefined||employee.firstName==undefined||employee.firstName==""){
				$scope.fNameErr=true;
				return;
			}else{
				$scope.fNameErr=false;
			}
			if(employee.lastName==undefined||employee.lastName==""){
				$scope.lNameErr=true;
				return;
			}else{
				$scope.lNameErr=false;
			}

			if(employee.mobileNo==undefined||employee.mobileNo==""){
				$scope.MoNoErr=true;
				return;
			}else{
				$scope.MoNoErr=false;
			}
			
			if(employee.email==undefined||employee.email==""){
				$scope.emailErr=true;
				return;
			}else{
				$scope.emailErr=false;
			}
			
			if(employee.employeeNo==undefined||employee.employeeNo==""){
				$scope.employeeNoErr=true;
				return;
			}else{
				$scope.employeeNoErr=false;
			}
			/*******************************/
			
			/*****************************/
			
			if(employee.employeeType==undefined||employee.employeeType==""){
				$scope.employeeTypeErr=true;
				return;
			}else{
				$scope.employeeTypeErr=false;
			}
			
			if(employee.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			
			if(employee.department==undefined){
				$scope.deptalrt=true;
				return;
			}else{
				$scope.deptalrt=false;
			}
			
	
			
			if(vm.empErr.code==500){
				return;
			}
			
			/*******************************/
			console.log("employee "+JSON.stringify(employee))
			if($scope.invalidMoNo){
				
				return;
			}
			
		
			$rootScope.loader=true;
			employee.active=1;
			employee.addedDate=new Date();
			employee.addedBy=vm.user
			 var url ="";
			 console.log("editView  :"+$scope.editView);
			if($scope.editView){
				employee.updDatetime=new Date();
				var msg="Selected Employee has been updated"
					
					url =employeeUrl+"/updateEmployee"
			}else{
				employee.updDatetime=null;
				url =employeeUrl+"/addNewEmployee"
			}
			console.log("url:"+url);
			
			// var url =employeeUrl+"/addNewEmployee";
				genericFactory.add(msg,url,employee).then(function(response) {
					console.log("resp:"+JSON.stringify(response))
					
					loadEmployees();
					$scope.addNew=false;
					$scope.employee={}
					if(response.data.code==200){
						
						if($scope.editView==true){
							toastr.success(msg);
						$rootScope.loader=false;
						}else{
							toastr.success(msg);
							$rootScope.loader=false;
						}
						
					}else{
						toastr.error(response.data.msg);
						$rootScope.loader=false;
					}
					
			});
		}
			
	
	
		function saveDepartment(department){
		
		if(department==undefined){
			$scope.depatName=true
			console.log("Department NAME ")
			return;
		}

			department.active=1;
			department.addedDate=new Date();
			department.addedBy=vm.user
			department.updDatetime=new Date()
			console.log("Department "+JSON.stringify(department))
			var msg="New Department Added Sucessfully"
				 var url =employeeUrl+"/addNewDepartment";
				genericFactory.add(msg,url,department).then(function(response) {
					if(response.data.code==200){
						toastr.success(msg);	
					}else{
						toastr.error(response.message);
					}
					loadDepartments();
					$scope.addNewDepartment=false;
					$scope.department={}	
				//	toastr.success("New Department Added Sucessfully");
			});

		}

	
		

		function uploadEmployee(){
			
			
			console.log("UPloading"+$('.loading').show())
			
			$('.loading').show();
			
		
			var file = document.getElementById('uploadEmployee').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			
		$rootScope.loader=true;
		
		var fd = new FormData();
		fd.append('file', file);
		var url = employeeUrl + "/uploadEmployee?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
			var filename="employee_upload_log_"+$filter('date')(new Date(), "MM/dd/yyyy h:mma");+".txt"
			var text=response.data.resmessage
			download(filename, text) 
			toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });					
			loadEmployees();
			$rootScope.loader=false;
			$scope.uploadtab=false;
		}, function errorCallback(response) {
	    	$('.loading').hide();
	    	
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccesful !!');
			loadEmployees();
			$scope.uploadtab=false;
			$rootScope.loader=false;
				    });

		angular.element("input[type='file']").val(null);
	}
		function download(filename, text) {
			  var element = document.createElement('a');
			  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
			  element.setAttribute('download', filename);

			  element.style.display = 'none';
			  document.body.appendChild(element);

			  element.click();

			  document.body.removeChild(element);
			}
		
		
		
		
		
	}
})();
