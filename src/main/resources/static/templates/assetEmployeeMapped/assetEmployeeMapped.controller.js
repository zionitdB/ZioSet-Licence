(function() {
	'use strict';

	angular.module('myApp.assetEmployeeMapped').controller('AssetEmployeeMappedController', AssetEmployeeMappedController);

	AssetEmployeeMappedController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http'];
	
	/* @ngInject */
	function AssetEmployeeMappedController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http) {
	
		var assetEmpMappedUrl = ApiEndpoint.url+"assetEmpMapped";
		var assetUrl = ApiEndpoint.url+"asset";
		var employeeUrl = ApiEndpoint.url+"employee";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			pageno:1,
			serachText:"",
			perPage : 10,
			total_count:100,
			addNew:addNew,
			cancle:cancle,
			mappeAsset:mappeAsset,
			transafer:transafer,
			release:release,
			checkEmployee:checkEmployee,
			transfer:transfer,
			loadAssetsEmployeedMappeds:loadAssetsEmployeedMappeds
		});

		(function activate() {
			loadAssetsEmployeedMappeds();
			loadAssetsEmployeedCount();
		})();
		
		$scope.totalAssignedAsset=function (){
			$scope.type=
			loadAssetsEmployeedMappeds
		}
		$scope.totalRemainingAsset=function (){
			loadAssetsEmployeedMappeds
		}
		$scope.totolAssignedEmployee=function (){
			loadAssetsEmployeedMappeds
		}
		$scope.totalRemainingEmployee=function (){
			loadAssetsEmployeedMappeds
		}
		
		function loadAssetsEmployeedCount(){
			var msg=""
				 var url =assetEmpMappedUrl+"/getAllAssetEmployeesCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employeesAssetCounts = response.data;
				 $rootScope.loader=false;
				console.log("employeesAssetCounts: "+JSON.stringify(vm.employeesAssetCounts))
								
			});
		}
		//vm.labels={'srNo': 'Sr No','asset.assetType': 'Asset Type','asset.serialNo':'Serial No','asset.assetId':'Asset Id','asset.make':'Make','asset.model':'Model', 'employee.employeeNo':'Employee Name','empName':'Employee Name','mapDateStr':'Mapped Date'}
		vm.labels={'srNo': 'Sr No','asset.serialNo': 'Serial Number','asset.assetId':'HostName','asset.branch.branchName':'Location','asset.assetType':'Asset Type','asset.make':'Make','asset.model':'Model','asset.purchaseOrderNo':'Purchase Order','asset.invoiceNo':'Invoice Number','asset.invoiceDate':'Invoice Date','asset.age':'Age', 'employee.employeeNo':'Employee No','employee.username':'Employee NTID','empName':'Employee Name','employee.department.departmentName':'Department Name','employee.manager':'Manager','employee.employeeType':'Employee Type','asset.status':'Status','mapDateStr':'Mapped Date',}

$scope.newExcel= function(){
			
			
			
			$rootScope.loader=true;
			getAllAssetEmployees();
				 
			//	exportData();
				;	
	    	 // document.getElementById('btnExport').click();
			
			}
		function getAllAssetEmployees(){
			var msg=""
				 var url =assetEmpMappedUrl+"/getAllAssetEmployees";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employeesAsset = response.data;
				
				 setTimeout(function(){
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
						// 
						  $rootScope.$digest();
						},1000)
				
			
				console.log("employeesAsset: "+JSON.stringify(vm.employeesAsset))
								
			});
		}
		
		 function addNew(){
			 vm.mapped={}
			 $scope.addNew=true;
			 $scope.transTab=false;
			 loadEmployees();
			 loadAvailableAsset();
		 }
		 function cancle(){
			 $scope.addNew=false;
			 $scope.transTab=false;
			 vm.mapped={}
		 }
		 function transafer(assetsMappe){
			 $scope.transTab=true;
			 console.log("assetsMappe :"+JSON.stringify(assetsMappe.asset.branch.branchName))
			 vm.assetsMappe=assetsMappe
			// loadEmployees();
			 loadEmployeesByBranch(assetsMappe.asset.branch.branchName)
			 $scope.addNew=false;
		 }
		 $scope.onSelectEmployee=function (employee){
			 console.log("employee :"+JSON.stringify(employee))
			 loadAvailableAsset(employee.branch.branchId)
		 }
		 function transfer(assetsMappe){
			
				if(vm.mapped.newemployee.employeeId==vm.assetsMappe.employee.employeeId){
					$scope.sameEmpErr=true
					console.log("SAME :")
					return;
				}else{
					$scope.sameEmpErr=false
					console.log("DIFF:")
				}
			 
			 var newMapped={}
			 newMapped.newemployee=vm.mapped.newemployee
			 newMapped.assetEmployeeAssigned=vm.assetsMappe
			 
			 
			 console.log("newMapped "+JSON.stringify(newMapped))
				var msg=""
					 var url =assetEmpMappedUrl+"/transferAsset";
						genericFactory.add(msg,url,newMapped).then(function(response) {
							console.log("resp:"+JSON.stringify(response))
							console.log("data :"+JSON.stringify(response.data.code))
							loadAssetsEmployeedMappeds();
							$scope.transTab=false;
							vm.mapped={}
							if(response.data.code==200){
								toastr.success(response.data.message);
								
								
							}else{
								toastr.error(response.data.message);
								
							}
							
					});
			 
		 }
		 function checkEmployee(assetsMappe){
			 console.log("NEW :"+JSON.stringify(vm.mapped))
				console.log("OLD :"+JSON.stringify(vm.assetsMappe))
				if(vm.mapped.newemployee.employeeId==vm.assetsMappe.employee.employeeId){
					$scope.sameEmpErr=true
					console.log("SAME :")
				}else{
					$scope.sameEmpErr=false
					console.log("DIFF:")
				}
		 }
		 function release(assetsMappe){
			 var msg=""
				 var url =assetEmpMappedUrl+"/releaseMappedAsset";
					genericFactory.add(msg,url,assetsMappe).then(function(response) {
						console.log("resp:"+JSON.stringify(response))
						console.log("data :"+JSON.stringify(response.data.code))
						loadAssetsEmployeedMappeds();
						
					
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							toastr.error(response.data.message);
							
						}
						
				});
		 }
		
		
			function loadEmployees(){
				var msg=""
					 var url =employeeUrl+"/getAllEmployees";
					genericFactory.getAll(msg,url).then(function(response) {
					vm.employees = response.data;
					console.log("employees: "+JSON.stringify(vm.employees))
									
				});
			}
			
			
			function loadEmployeesByBranch(branchbranchName){
				var msg=""
					 var url =employeeUrl+"/getAllEmployeeListByBranch?branchName="+branchbranchName;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.employees = response.data;
					console.log("employees: "+JSON.stringify(vm.employees))
									
				});
			}
			
			function loadAvailableAsset(branchId){
				var msg=""
					 var url =assetUrl+"/getAllAvailableTags?branchId="+branchId;
				
				if(userDetail.role.roleId==1||userDetail.role.roleId==3||userDetail.role.roleId==4){
					url =assetUrl+"/getAllAvailableTags1";
				}else{
					url =assetUrl+"/getAllAvailableTags?branchId="+branchId;
				}
				console.log("url: "+url)
					genericFactory.getAll(msg,url).then(function(response) {
					vm.availablesAssets = response.data;
					console.log("availablesAssets: "+JSON.stringify(vm.availablesAssets))
									
				});
			}
			
		
	//***********************Pagination Start*****************************//
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAssetsEmployeedMappeds();
			
		}
		
		function loadAssetsEmployeedMappeds(){
			var url=""
			var urlCount=""
			var msg=""
				var dataReq=""
					console.log("userDetail"+JSON.stringify(userDetail))
					if(userDetail.role.roleId==1||userDetail.role.roleId==3||userDetail.role.roleId==4){
						dataReq="ALL"
					}else{
						dataReq=userDetail.branch.branchName
					}
			if(vm.serachText==""||vm.serachText==undefined){
				url=assetEmpMappedUrl+"/getAssetEmployeeAssignedByLimit/"+vm.pageno+"/"+vm.perPage+"/"+dataReq;
				urlCount=assetEmpMappedUrl+"/getAssetEmployeeAssignedCount/"+dataReq
			}else{
				url=assetEmpMappedUrl+"/getAssetEmployeeAssignedByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&dataReq="+dataReq;
				urlCount=assetEmpMappedUrl+"/getAssetEmployeeAssignedCountAndSearch?searchText="+vm.serachText+"&dataReq="+dataReq
			}
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetsMappes = response.data;
				
				console.log("assetsMappes : "+JSON.stringify(vm.assetsMappes))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.total_count= response.data;
				console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		function mappeAsset(mapped){
			console.log("mapped: "+JSON.stringify(mapped.asset.id))
			if(mapped==undefined||mapped.employee==undefined){
				$scope.empErr=true
				return;
			}else{
				$scope.empErr=false
			}
			if(mapped.asset==undefined){
				$scope.assetErr=true
				return;
			}else{
				$scope.assetErr=false
			}
			if(mapped.employee.employeeId==undefined){
				$scope.empInvErr=true
				return;
			}else{
				$scope.empInvErr=false
			}
			if(mapped.asset.id==undefined){
				$scope.assInvErr=true
				return;
			}else{
				$scope.assInvErr=false
			}
			
			mapped.mappedBy=userDetails.firstName+" "+userDetails.lastName
			var msg=""
				console.log("mapping  new Asset :"+JSON.stringify(mapped))
			 var url =assetEmpMappedUrl+"/mappedAsset";
				genericFactory.add(msg,url,mapped).then(function(response) {
					console.log("resp:"+JSON.stringify(response))
					console.log("data :"+JSON.stringify(response.data.code))
					loadAssetsEmployeedMappeds();
					$scope.addNew=false;
					vm.mapped={}
					if(response.data.code==200){
						toastr.success(response.data.message);
						
						
					}else{
						toastr.error(response.data.message);
						
					}
					
			});
		}
	
	
		
	}

	
})();
