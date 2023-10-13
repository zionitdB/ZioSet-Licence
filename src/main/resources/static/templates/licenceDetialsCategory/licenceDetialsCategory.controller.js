(function() {
	'use strict';

	angular.module('myApp.licenceDetialsCategory').controller('LicenceDetialsCategoryController', LicenceDetialsCategoryController);

	LicenceDetialsCategoryController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceDetialsCategoryController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		var bundleUrl = ApiEndpoint.url+"bundle";

		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
		});

		(function activate() {
		
			
			loadCategories();
			
		})();
		
		
		
		vm.labels={'srNo': 'Sr No','associate.associateName': 'Associate','product.productName': 'Product','productVersion': 'Version','asset.make':'Make','asset.model':'Model','asset.serialNo':'Serial No','asset.assetId':'Asset Id ','asset.employeeNo':'Employee No','asset.employeeName':'Employee Name','asset.projectId':'Project Id','asset.projectName':'Project Name','computerName':'Computer Name', 'detectedDate':'Detected Date'}
		$scope.newExcel= function(associate){
			vm.fileName=associate.applicationName
			if(vm.lists==undefined){
				var msg="";
				var 	url=licenceUrl+"/getInstallLicenceBYProductNameProduct";
				var obj={};
				obj.inputStr=associate.applicationName;
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
		
		
		
		
		
		
		
		
		
		function loadCategories(){
			var msg="";
			var 	url=bundleUrl+"/getCategoriesWiseCount";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.categories = response.data;
				console.log("categories: "+JSON.stringify(vm.categories))
								
			});
			
			
			
		}
		
		function getApplicationByCategory(categoryName){
			var msg="";
			var 	url=bundleUrl+"/getApplicationCountCategory?categoryName="+categoryName;
			console.log("URL "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.applications = response.data;
				console.log("applications"+JSON.stringify(vm.applications))
				if(vm.applications.length!=0){
					$scope.showAsso=true
				}
								
			});
		}
		
		$scope.selectQR = function (index) {
			vm.categories[index].check = !vm.categories[index].check;
			
			 
			
			
			
			if(vm.categories[index].check){
				$scope.selCategory=vm.categories[index].categoryName
				getApplicationByCategory(vm.categories[index].categoryName)

			}
			for(var i=0;i<=vm.categories.length-1;i++){
				console.log("Type"+JSON.stringify(vm.categories))
									console.log("i"+i+" index "+index+" Length "+vm.categories.length)

				if(i==index){
				}else{
					vm.categories[i].check=false

				}
			}

			
		}
		
		$scope.selectAppication= function (index) {
			vm.applications[index].check = !vm.applications[index].check;
			
			// getDataByTypes(vm.types[index].typeName)
			if(vm.applications[index].check){
				$scope.selApplications=vm.applications[index].applicationName
				getDataByApplication()
			}
			for(var i=0;i<=vm.applications.length-1;i++){
									console.log("i"+i+" index "+index+" Length "+vm.categories.length)

				if(i==index){
				}else{
					vm.applications[i].check=false

				}
			}
		
			
		}
		function getDataByApplication(){
			var msg="";
			var 	url=licenceUrl+"/getInstallLicenceBYProductNameProduct"
			var obj={};
			obj.inputStr=$scope.selApplications
			console.log("url "+url)
							console.log("obj"+JSON.stringify(obj))

			genericFactory.add(msg,url,obj).then(function(response) {
				vm.lists= response.data;
				console.log("lists"+JSON.stringify(vm.lists))
								
			});
		}
		
		
		
	}

	
})();
