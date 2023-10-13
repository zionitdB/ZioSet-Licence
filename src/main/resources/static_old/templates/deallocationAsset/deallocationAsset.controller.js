(function() {
	'use strict';

	angular.module('myApp.deallocationAsset').controller('DeallocationAssetController', DeallocationAssetController);

	DeallocationAssetController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function DeallocationAssetController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
		var assetEmpMappedUrl = ApiEndpoint.url+"assetEmpMapped";
		var employeeUrl = ApiEndpoint.url+"employee";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			getAllocatedAsset:getAllocatedAsset,
			allocatedAssets:[],
			deallocate:deallocate,
			upload:upload,
			cancle:cancle,
			uploadSave:uploadSave
			
		});

		(function activate() {
			loadEmployees()
			loadDeallocationCount()
		})();
		
		
		function upload(){
			$scope.uploadTab=true
		}
		function cancle(){
			$scope.uploadTab=false
		}
		
		function uploadSave(){
			$scope.diabledSaveButton=true
			var file = document.getElementById('uploadAssets').files[0];
			

			if (file == undefined) {
				$scope.uploadErr=true
				return;
			}else{
				$scope.uploadErr=false
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

			$('.loading').show();
			var fd = new FormData();
			fd.append('file', file);
			var url = assetUrl + "/uploadDeallocationAsset?uploadBy="+userDetails.firstName+" "+userDetails.lastName;
			console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			.then(function successCallback(response) {
				
				$('.loading').hide();
				var filename="asset_upload_log_"+$filter('date')(new Date(), "MM/dd/yyyy h:mma");+".txt"
				var text=response.data.resmessage
				download(filename, text) 
				$rootScope.loader=false;
				document.getElementById("uploadAssets").value=null
				$scope.diabledSaveButton=false
				$scope.uploadTab=false;
				loadAssets();
			}, function errorCallback(response) {
		    	$('.loading').hide();
				
		    	document.getElementById("uploadAssets").value=null
				toastr.error('Upload....', 'UnSuccesful !!');
				$scope.uploadTab=false;
				loadAssets();
				$scope.diabledSaveButton=false
					    });

			angular.element("input[type='file']").val(null);
		}
		
		
		
		
		
		
		
		function loadDeallocationCount(){
			var msg=""
				 var url =assetEmpMappedUrl+"/getDeallocationCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.deallocationCounts = response.data;
				console.log("deallocationCounts: "+JSON.stringify(vm.deallocationCounts))
								
			});
		}
		function loadEmployees(){
			var msg=""
				 var url =employeeUrl+"/getAllEmployees2";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employees = response.data;
				//console.log("employees: "+JSON.stringify(vm.employees))
								
			});
		}
		
$scope.check=function(employee){
	console.log("CHECK  : "+JSON.stringify(employee))
	console.log("Ename  : "+$scope.ename);
	
	if($scope.ename!=employee){
		$scope.invalidEmployee=true;
	}
		}
		function getAllocatedAsset(employee){
			console.log("employeeId : "+JSON.stringify(employee.employeeId))
			console.log("employee Name: "+employee.firstName+" "+employee.lastName)
			$scope.ename=employee.firstName+" "+employee.lastName
			if(employee.employeeId==undefined){
				$scope.invalidEmployee=false;
			}else{
				$scope.invalidEmployee=false;
				var msg=""
					 var url =assetEmpMappedUrl+"/getAllocatedAssetByEmployee?employeeId="+employee.employeeId;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.allocatedAssets = response.data;
					if(vm.allocatedAssets.length==0){
						toastr.error("No Asset Allocated");
					}
				console.log("allocatedAsset : "+JSON.stringify(vm.allocatedAssets))
									
				});
			}
			
		}
		
		function deallocate(allocatedAsst){
			var msg=""
				 var url =assetEmpMappedUrl+"/releaseMappedAsset";
				genericFactory.add(msg,url,allocatedAsst).then(function(response) {
			console.log("allocatedAsset : "+JSON.stringify(vm.allocatedAssets))
						getAllocatedAsset(allocatedAsst.employee)		
			});
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
