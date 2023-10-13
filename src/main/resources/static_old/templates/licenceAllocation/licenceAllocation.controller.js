(function() {
	'use strict';

	angular.module('myApp.licenceAllocation').controller('LicenceAllocationController', LicenceAllocationController);

	LicenceAllocationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceAllocationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadLicence:loadLicence,
			addNew:addNew,
			cancle:cancle,
			save:save,
			upload:upload,
			uploadSave:uploadSave,
			loadLicenceNameByType:loadLicenceNameByType,
			loadLicenceByTypeAndName:loadLicenceByTypeAndName,
			assignedLicence:assignedLicence,
			getDataByTypes:getDataByTypes,
			getDataByTypeAndAssociate:getDataByTypeAndAssociate,
			getDataByTypeAndAssociateAndProductName:getDataByTypeAndAssociateAndProductName,
			getDataByTypeAndAssociateAndProductNameVersion:getDataByTypeAndAssociateAndProductNameVersion,
			getDataByTypeAndAssociateAndProductNameVersionAndAssetId:getDataByTypeAndAssociateAndProductNameVersionAndAssetId
			
		});

		(function activate() {
			
			loadLicence();
			loadTypes();
			
		})();
		function upload(){
			$scope.addNew=false;
			$scope.uploadTab=true;
		}
		function loadTypes(){
			var msg="";
			var 	url=licenceUrl+"/getLicenceTypes";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.types = response.data;
				console.log("types: "+JSON.stringify(vm.types))
								
			});
			
			
			
		}
		
		function getDataByTypes(type){
			var msg="";
			var 	url=licenceUrl+"/getAssocitaeNameByType?type="+type;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.associateNames = response.data;
				console.log("associateNames"+JSON.stringify(vm.associateNames))
								
			});
			var url=licenceUrl+"/getAllAssetLicencceByType?type="+type;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				console.log("assetLicences"+JSON.stringify(vm.assetLicences))
								
			});
		}
		function getDataByTypeAndAssociate(type,associateName){
			var msg="";
			var 	url=licenceUrl+"/getProductNameByTypeAndAssociate?type="+type+"&associateName="+associateName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.productNames = response.data;
				console.log("productNames"+JSON.stringify(vm.productNames))
								
			});
			var 	url=licenceUrl+"/getAllAssetLicencceByTypeAndAssociate?type="+type+"&associateName="+associateName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				console.log("assetLicences"+JSON.stringify(vm.assetLicences))
								
			});
		}
		function getDataByTypeAndAssociateAndProductName(type,associateName,productName){
			var msg="";
			var 	url=licenceUrl+"/getVersionByTypeAndAssociate?type="+type+"&associateName="+associateName+"&productName="+productName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.versions = response.data;
				console.log("version"+JSON.stringify(vm.versions))
								
			});
			var 	url=licenceUrl+"/getAllAssetLicencceByTypeAndAssociateAndProduct?type="+type+"&associateName="+associateName+"&productName="+productName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				console.log("assetLicences"+JSON.stringify(vm.assetLicences))
								
			});
		}
		function getDataByTypeAndAssociateAndProductNameVersion(type,associateName,productName,version){
			var msg="";
			var 	url=licenceUrl+"/getAssetIdByTypeAndAssociationAndProductNameAndVersion?type="+type+"&associateName="+associateName+"&productName="+productName+"&version="+version;;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetIds = response.data;
				console.log("assetIds"+JSON.stringify(vm.assetIds))
								
			});
			var 	url=licenceUrl+"/getAllAssetLicencceByTypeAndAssociateAndProductAndVersion?type="+type+"&associateName="+associateName+"&productName="+productName+"&version="+version;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				console.log("assetLicences"+JSON.stringify(vm.assetLicences))
								
			});
		}
		function getDataByTypeAndAssociateAndProductNameVersionAndAssetId(type,associateName,productName,version,assetId){
			
			var 	url=licenceUrl+"/getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId?type="+type+"&associateName="+associateName+"&productName="+productName+"&version="+version+"&assetId="+assetId;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				console.log("assetLicences"+JSON.stringify(vm.assetLicences))
								
			});
		}
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','deviceGrouping': 'Device Grouping','deskLocation': 'Location','assetType':'Asset Type','serialNo':'Serial No','assetId':'Asset Id','tagCode':'EPC','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
		
		
		$scope.newExcel= function(){
			
			
			
			$rootScope.loader=true;
	    	//  getAllAssets();
				 $rootScope.loader=true;
				exportData();
	    	  //document.getElementById('btnExport').click();
			
			}
		
		
	
		function addNew(){
			vm.asset={}
			$scope.addNew=true;
			loadAsseyUnalllocatedLicence();
			loadLicenceType()
			$rootScope.loader=true;

		}
		
		function cancle(){
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.viewQRTab=false;
			vm.asset={};
		
		}
		
		
		function loadAsseyUnalllocatedLicence(){
			var msg="";
			var url=licenceUrl+"/getAssetUnalllocatedLicence"

			genericFactory.getAll(msg,url).then(function(response) {
				vm.asseyUnalllocatedLicences= response.data;
				
				console.log("asseyUnalllocatedLicence: "+JSON.stringify(vm.asseyUnalllocatedLicences ))
				$rootScope.loader=false;
		
			});
		}
		
		function loadLicenceType(){
			var msg="";
			var url=licenceUrl+"/getLicenceTypes"

			genericFactory.getAll(msg,url).then(function(response) {
				vm.licencesTypes = response.data;
				
				console.log("licencesTypes: "+JSON.stringify(vm.licencesTypes ))
								
			});
		}
		
		function loadLicenceNameByType(type){
			var msg="";
			var url=licenceUrl+"/getLicenceNameByType?type="+type

			genericFactory.getAll(msg,url).then(function(response) {
				vm.licencesNames = response.data;
				
				console.log("licencesNames: "+JSON.stringify(vm.licencesNames ))
								
			});
		}
		function loadLicenceByTypeAndName(type,name){
			var msg="";
			var url=licenceUrl+"/LicenceByTypeAndName?type="+type+"&name="+name
			
			
			console.log("url: "+url)

			genericFactory.getAll(msg,url).then(function(response) {
				vm.availablelicences = response.data;
				
				console.log("availablelicences: "+JSON.stringify(vm.availablelicences ))
								
			});
		}
	//***********************Pagination Start*****************************//
		
		function assignedLicence(){
			console.log("assetLicence  : "+JSON.stringify(vm.assetLicence.type ))

			if(vm.assetLicence==null||vm.assetLicence==undefined){
				$scope.assetErr=true;
				return;
			}else{
				$scope.assetErr=false;
			}
			if(vm.assetLicence.type==null||vm.assetLicence.type==""){
				$scope.typeErr=true;
				return;
			}else{
				$scope.typeErr=false;
			}
			
			if(vm.assetLicence.name==null||vm.assetLicence.name==""){
				$scope.nameErr=true;
				return;
			}else{
				$scope.nameErr=false;
			}
			
			if(vm.assetLicence.type==null||vm.assetLicence.type==""){
				$scope.keyErr=true;
				return;
			}else{
				$scope.keyErr=false;
			}
			
			if(vm.assetLicence.licence==null||vm.assetLicence.licence==""){
				$scope.keyErr=true;
				return;
			}else{
				$scope.keyErr=false;
			}
			var msg="";
			var url=licenceUrl+"/addAssetLicence"
			console.log("assetLicence  : "+JSON.stringify(vm.assetLicence ))
			vm.assetLicence.assingBy=userDetail.firstName+" "+userDetail.lastName
			vm.assetLicence.assingDate=new Date();
			vm.assetLicence.licenceKey=vm.assetLicence.licence.licenceKey;

			genericFactory.add(msg,url,vm.assetLicence).then(function(response) {
				vm.licences = response.data;
				
				console.log("licencees: "+JSON.stringify(vm.licences ))
								
			});

		}
		$scope.searchByPagination=function (search){
			loadLicence();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			console.log("PAGE ................ "+pageNo)
			loadLicence();
			
		}
		
		function loadLicence(){
			if(vm.perPage>=1000){
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=licenceUrl+"/getAssetLicenceByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getAssetLicenceLicenceCount"
			}else{
				url=licenceUrl+"/getAssetLicenceLicenceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&type="+$scope.type;
				urlCount=licenceUrl+"/getAssetLicenceLicenceCountAndSearch?searchText="+vm.serachText
			}
			
			
			//console.log("$scope.type :: "+$scope.type)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assetLicences = response.data;
				
				console.log("assetLicences: "+JSON.stringify(vm.assetLicences))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("Licence  Count: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
		
 
		
		
		
		
		
		
		function save(licence){
			
			console.log("licence : "+JSON.stringify(licence))
			
			if(licence==undefined||licence.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(licence.licenceType==""||licence.licenceType==undefined){
				$scope.licenceTypeErr=true
				return;
			}else{
				$scope.licenceTypeErr=false
			}
			if(licence.category==""||licence.category==undefined){
				$scope.categoryErr=true
				return;
			}else{
				$scope.categoryErr=false
			}
	
			if(licence.licenceName==""||licence.licenceName==null){
				$scope.licenceNameErr=true
				return;
			}else{
				$scope.licenceNameErr=false
			}
			if(licence.licenceVersion==""||licence.licenceVersion==null){
				$scope.licenceVersionErr=true
				return;
			}else{
				$scope.licenceVersionErr=false
			}
			if(licence.licenceKey==""||licence.licenceKey==null){
				$scope.licenceKeyErr=true
				return;
			}else{
				$scope.licenceKeyErr=false
			}
		
				
			if(licence.brand==""||licence.brand==null){
				$scope.brandErr=true
				return;
			}else{
				$scope.brandErr=false
			}
			if(licence.supplier==""||licence.supplier==null){
				$scope.supplierErr=true
				return;
			}else{
				$scope.supplierErr=false
			}
			
			
			
			
			if(licence.licencePeriod==""||licence.licencePeriod==null){
				$scope.licencePeriodErr=true
				return;
			}else{
				$scope.licencePeriodErr=false
			}
			if(licence.licenceStatus==""||licence.licenceStatus==null){
				$scope.statusErr=true
				return;
			}else{
				$scope.statusErr=false
			}
			
			
			if(licence.cost==""||licence.cost==null){
				$scope.costErr=true
				return;
			}else{
				$scope.costErr=false
			}
			if(licence.purchaseDate==""||licence.purchaseDate==null){
				$scope.purchaseDateErr=true
				return;
			}else{
				$scope.purchaseDateErr=false
			}
			
			console.log("licence : "+JSON.stringify(licence))

			
			licence.added_by=userDetails.firstName+" "+userDetails.lastName
			licence.addedDate=new Date();
			var msg=""
			 var url =licenceUrl+"/addAssetLicence";
				genericFactory.add(msg,url,licence).then(function(response) {
					//console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadLicence();
					$scope.addNew=false;
					vm.assetLicence={}
					if(response.data.code==200){
						toastr.success(response.data.message);
						$scope.diabledSaveButton=true
						
					}else{
						alert(response.data.message);
						$scope.diabledSaveButton=false
						
					}
					
			});
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
			var url = licenceUrl + "/uploadLicenceAllocation?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
			console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			.then(function successCallback(response) {
				
				$('.loading').hide();
				var filename="lincence_allocation"+$filter('date')(new Date(), "MM/dd/yyyy h:mma");+".txt"
				var text=response.data.resmessage
				download(filename, text) 
				$rootScope.loader=false;
				document.getElementById("uploadAssets").value=null
				$scope.diabledSaveButton=false
				$scope.uploadTab=false;
				loadLicence();
			}, function errorCallback(response) {
		    	$('.loading').hide();
				
		    	document.getElementById("uploadAssets").value=null
				toastr.error('Upload....', 'UnSuccesful !!');
				$scope.uploadTab=false;
				loadLicence();
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
