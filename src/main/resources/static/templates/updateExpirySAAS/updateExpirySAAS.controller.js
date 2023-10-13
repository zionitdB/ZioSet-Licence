(function() {
	'use strict';

	angular.module('myApp.updateExpirySAAS').controller('UpdateExpirySAASController', UpdateExpirySAASController);

	UpdateExpirySAASController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function UpdateExpirySAASController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
	
	
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			upload:upload,
			uploadSave:uploadSave,
			addNew:addNew,
			getLicenceByPublisherAndProduct:getLicenceByPublisherAndProduct,
			save:save,
			cancle:cancle
		});

		(function activate() {
		
		getListOFUpdatedExpiry()
		})();
		
		function cancle(){
			$scope.addNewTab=false;
			$scope.uploadTab=false;
		}
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','licence.associate.associateName': 'Publisher','licence.product.productName': 'Product','licence.licenceVersion': 'Version','existingExpiryDate':'','newExpiryDate':'New Expiry'}


		$scope.newExcel= function(){
			$rootScope.loader=true;
	    	  getAllUpdatedExpirys();
				// $rootScope.loader=true;
				//document.getElementById('btnExport').click();
			
			}
		function getAllUpdatedExpirys(){
			var msg=""
				var url=licenceUrl+"/getAllupdatedExpiry"
				genericFactory.getAll(msg,url).then(function(response) {
					vm.updatedExpiryLists = response.data;
					setTimeout(function(){	
						
						
						 $rootScope.loader=false; 
						 console.log("CLICK ")
						 document.getElementById('ExportButton').click()
						//  $rootScope.$digest();
						},1000);
					//console.log("updatedExpiryLists : "+JSON.stringify(vm.updatedExpiryLists ))
									
				});
		}
		
		function save(expiryUpdate){
			if(expiryUpdate==undefined||expiryUpdate.associate==undefined){
				$scope.associateErr=true;
				return;
			}else{
				$scope.associateErr=false;
			}
			if(expiryUpdate.product==undefined){
				$scope.productErr=true;
				return;
			}else{
				$scope.productErr=false;
			}
			
			if(expiryUpdate.licence==undefined){
				$scope.licenceErr=true;
				return;
			}else{
				$scope.licenceErr=false;
			}
			
			if(expiryUpdate.newExpDate==undefined){
				$scope.newExpDateErr=true;
				return;
			}else{
				$scope.newExpDateErr=false;
			}
			
			var msg=""
				var url=licenceUrl+"/addExpiryUpdate"
				genericFactory.add(msg,url,expiryUpdate).then(function(response) {
					vm.resObj = response.data;
					if(vm.resObj.code=200){
						toastr.success(vm.resObj.message);
					}
					$scope.addNewTab=false;
					console.log("resObj: "+JSON.stringify(vm.resObj))
					getListOFUpdatedExpiry()			
				});
			
		}
		
		
		function addNew(){
			$scope.addNewTab=true;
			$scope.uploadTab=false;
			loadPublisher();
			loadProduct();
		}

		// current page
		$scope.pagination = {
		        current: 1
		    };
		$scope.searchByPagination=function (search){
			getListOFUpdatedExpiry();
			
		}
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			getListOFUpdatedExpiry();
			
		}
		
		
		function loadPublisher(){
			var msg=""
			var url=licenceUrl+"/getAllPublisher"
			genericFactory.getAll(msg,url).then(function(response) {
				vm.publishers = response.data;
				
				console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
		}
		function loadProduct(){
			var msg=""
			var url=licenceUrl+"/getAllProduct"
			genericFactory.getAll(msg,url).then(function(response) {
				vm.products = response.data;
				
				console.log("products: "+JSON.stringify(vm.products))
								
			});
		}
		
		
		function getLicenceByPublisherAndProduct(expiryUpdate){
			var msg=""
			var url=licenceUrl+"/getLicenceByPublisherAndProduct?publisher="+expiryUpdate.associate.id+"&product="+expiryUpdate.product.id
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				
				console.log("licences: "+JSON.stringify(vm.licences))
								
			});
		}
		function getListOFUpdatedExpiry(){
			
			var url=""
				var urlCount=""
					var msg=""
			if(vm.serachText==""||vm.serachText==undefined){
				url=licenceUrl+"/getExpiryUpdateByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getExpiryUpdateCount"
			}else{
				url=licenceUrl+"/getExpiryUpdateByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage
				urlCount=licenceUrl+"/getExpiryUpdateCountAndSearch?searchText="+vm.serachText;
			}
			genericFactory.getAll(msg,url).then(function(response) {
				vm.updatedexpiryLicences = response.data;
				
				console.log("updatedexpiryLicences: "+JSON.stringify(vm.updatedexpiryLicences))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				
				vm.total_count= response.data;
				console.log("total_count: "+JSON.stringify(vm.total_count))
								
			});
		}
		
		
		
		
		
		
		
		
		
		
	function upload(){
		$scope.uploadTab=true;
	}
	function uploadSave(){
		$scope.diabledSaveButton=true
		var file = document.getElementById('uploadExpiry').files[0];
		

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
		var url = licenceUrl + "/uploadExpiryUpdate?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
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
			//loadAssets();
			getListOFUpdatedExpiry()
		}, function errorCallback(response) {
	    	$('.loading').hide();
			
	    	document.getElementById("uploadAssets").value=null
			toastr.error('Upload....', 'UnSuccesful !!');
			$scope.uploadTab=false;
			//loadAssets();
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
		
		
	
		
	}

	
})();
