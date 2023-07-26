(function() {
	'use strict';

	angular.module('myApp.customerSuppliedSoftware').controller('CustomerSuppliedSoftwareController', CustomerSuppliedSoftwareController);

	CustomerSuppliedSoftwareController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function CustomerSuppliedSoftwareController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var customerSuppliedSoftwareUrl = ApiEndpoint.url+"customerSuppliedSoftware";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			upload:upload,
			addNew:addNew,
			save:save,
			cancle:cancle,
			edit:edit,
			delet:delet,
			uploadSave:uploadSave
		});

		(function activate() {
			loadCustomerSuppliedSoftwares()
		})();
		
		function upload(){
			
			$scope.uploadTab=false;
			$scope.uploadTab=true;
		}
		//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadCustomerSuppliedSoftwares();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadCustomerSuppliedSoftwares();
			
		}
		function addNew(){
			$scope.addNewTab=true;
			$scope.uploadTab=false;
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
			var url = customerSuppliedSoftwareUrl + "/uploadCustomerSuppliedSoftware?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
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
				loadCustomerSuppliedSoftwares();
			}, function errorCallback(response) {
		    	$('.loading').hide();
				
		    	document.getElementById("uploadAssets").value=null
				toastr.error('Upload....', 'UnSuccesful !!');
				$scope.uploadTab=false;
				loadCustomerSuppliedSoftwares();
				$scope.diabledSaveButton=false
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
		
		function loadCustomerSuppliedSoftwares(){
			if(vm.perPage>=1000){
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=customerSuppliedSoftwareUrl+"/getCustomerSuppliedSoftwareByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=customerSuppliedSoftwareUrl+"/getCustomerSuppliedSoftwareCount"
			}else{
				url=customerSuppliedSoftwareUrl+"/getCustomerSuppliedSoftwareByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage
				urlCount=customerSuppliedSoftwareUrl+"/getCustomerSuppliedSoftwareCountAndSearch?searchText="+vm.serachText
			}
			
			
			console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.suppliedSoftwares = response.data;
				
				console.log("suppliedSoftwares: "+JSON.stringify(vm.suppliedSoftwares))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.total_count= response.data;
				console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
		}

	
		function cancle(){
			$scope.addNewTab=false;
			vm.assetL={}
		}
		
		function delet(suppliedSoftware){
			var msg=""
				 var url =customerSuppliedSoftwareUrl+"/deleteCustomerSuppliedSoftware";
					genericFactory.add(msg,url,suppliedSoftware).then(function(response) {
						
						loadCustomerSuppliedSoftwares();
					
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		}
		function edit(suppliedSoftware){
		
			$scope.addNewTab=true
			vm.suppliedSoftware=suppliedSoftware
			vm.suppliedSoftware.licenceCount=parseInt(suppliedSoftware.licenceCount)
			
		}
		
		function save(suppliedSoftware){
			
			if(suppliedSoftware==undefined||suppliedSoftware.formSrNo==""){
				$scope.formSrNoErr=true;
				return;
			}else{
				$scope.formSrNoErr=false;
			}
			
			if(suppliedSoftware.assetTagNo==undefined||suppliedSoftware.assetTagNo==""){
				$scope.assetTagNoErr=true;
				return;
			}else{
				$scope.assetTagNoErr=false;
			}
			if(suppliedSoftware.title==undefined||suppliedSoftware.title==""){
				$scope.titleErr=true;
				return;
			}else{
				$scope.titleErr=false;
			}
			
			if(suppliedSoftware.version==undefined||suppliedSoftware.version==""){
				$scope.versionErr=true;
				return;
			}else{
				$scope.versionErr=false;
			}
			if(suppliedSoftware.language==undefined||suppliedSoftware.language==""){
				$scope.languageErr=true;
				return;
			}else{
				$scope.languageErr=false;
			}
			if(suppliedSoftware.licenceCount==undefined||suppliedSoftware.licenceCount==""||suppliedSoftware.licenceCount==0){
				$scope.licenceCount=true;
				return;
			}else{
				$scope.licenceCount=false;
			}
			
			
			
			
			console.log("suppliedSoftware: "+JSON.stringify(suppliedSoftware))

			var msg=""
				 var url =customerSuppliedSoftwareUrl+"/addNewCustomerSuppliedSoftware";
					genericFactory.add(msg,url,suppliedSoftware).then(function(response) {
						
						loadCustomerSuppliedSoftwares();
						$scope.addNewTab=false;
						vm.suppliedSoftware={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							alert(response.data.message);
							
						}
						
				});
			
		
		}
		
	}

	
})();
