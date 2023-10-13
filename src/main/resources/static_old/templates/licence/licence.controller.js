(function() {
	'use strict';

	angular.module('myApp.licence').controller('LicenceController', LicenceController);

	LicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var commonUrl = ApiEndpoint.url+"common";
		var tagUrl = ApiEndpoint.url+"tag";
		var assetUrl = ApiEndpoint.url+"asset";
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadLicence:loadLicence,
			addNew:addNew,
			user:userDetails,
			upload:upload,
			uploadSave:uploadSave,
			cancle:cancle,
			save:save,
			edit:edit,
			addAssociate:addAssociate,
			addProduct:addProduct,
			saveProduct:saveProduct,
			saveAssociate:saveAssociate,
			saveProduct:saveProduct,
			getDataByTypes:getDataByTypes,
			getDataByTypeAndAssociate:getDataByTypeAndAssociate,
			getDataByTypeAndAssociateAndProductName:getDataByTypeAndAssociateAndProductName,
			getDataByTypeAndAssociateAndProductNameVersion:getDataByTypeAndAssociateAndProductNameVersion,
			addType:addType,
			saveType:saveType
		});

		(function activate() {
		
			loadLicence();
			loadTypes();
			
		})();
		function saveType(type){
			if(type.typeName==""||type==undefined){
				$scope.typeNameErr=true
				return;
			}else{
				$scope.typeNameErr=false
			}
			
			console.log("associate: "+JSON.stringify(type))
			var msg="";
			var 	url=licenceUrl+"/addLicenceTypes";
			genericFactory.add(msg,url,type).then(function(response) {
				vm.res1 = response.data;
				console.log("res : "+JSON.stringify(vm.res1))
				loadTypes();
				vm.type={}
				$scope.addNewType=false;
			});
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
			var url=licenceUrl+"/getAllLicencceByType?type="+type;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				console.log("licences"+JSON.stringify(vm.licences))
								
			});
		}
		function getDataByTypeAndAssociate(type,associateName){
			var msg="";
			var 	url=licenceUrl+"/getProductNameByTypeAndAssociate?type="+type+"&associateName="+associateName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.productNames = response.data;
				console.log("productNames"+JSON.stringify(vm.productNames))
								
			});
			var 	url=licenceUrl+"/getAllLicencceByTypeAndAssociate?type="+type+"&associateName="+associateName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				console.log("licences"+JSON.stringify(vm.licences))
								
			});
		}
		function getDataByTypeAndAssociateAndProductName(type,associateName,productName){
			var msg="";
			var 	url=licenceUrl+"/getVersionByTypeAndAssociate?type="+type+"&associateName="+associateName+"&productName="+productName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.versions = response.data;
				console.log("version"+JSON.stringify(vm.version))
								
			});
			var 	url=licenceUrl+"/getAllLicencceByTypeAndAssociateAndProduct?type="+type+"&associateName="+associateName+"&productName="+productName;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				console.log("licences"+JSON.stringify(vm.licences))
								
			});
		}
		function getDataByTypeAndAssociateAndProductNameVersion(type,associateName,productName,version){
			var msg="";
			
			var 	url=licenceUrl+"/getAllLicencceByTypeAndAssociateAndProductAndVersion?type="+type+"&associateName="+associateName+"&productName="+productName+"&version="+version;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				console.log("licences"+JSON.stringify(vm.licences))
								
			});
		}
		function loadpaAssociate(){
			var msg="";
			var 	url=licenceUrl+"/getAssociates";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.associates = response.data;
				console.log("associates: "+JSON.stringify(vm.associates))
								
			});
		}
		function addType(){
			$scope.addNewAssociate=false;
			$scope.addNewProduct=false;
			$scope.addNewType=true;

		}
		function addAssociate(){
			$scope.addNewAssociate=true;
			$scope.addNewProduct=false;
			$scope.addNewType=false;
		}
		function addProduct(){
			$scope.addNewProduct=true;
			$scope.addNewAssociate=false;
			$scope.addNewType=false;

		}
		
		function saveAssociate(associate){
			if(associate==undefined||associate.associateId==undefined){
				$scope.associateIdErr=true;
				return;
			}else{
				$scope.associateIdErr=false;
			}
			if(associate.associateName==""||associate.associateName==undefined){
				$scope.associateNameErr=true
				return;
			}else{
				$scope.associateNameErr=false
			}
			
			console.log("associate: "+JSON.stringify(associate))
			var msg="";
			var 	url=licenceUrl+"/addAssociate";
			genericFactory.add(msg,url,associate).then(function(response) {
				vm.res1 = response.data;
				console.log("res : "+JSON.stringify(vm.res1))
				loadAssocistes();
				vm.associate={}
				$scope.addNewAssociate=false;
			});
			
			
		}
		function saveProduct(product){
			if(product==undefined||product.productId==undefined){
				$scope.productIdErr=true;
				return;
			}else{
				$scope.productIdErr=false;
			}
			if(product.productName==""||product.productName==undefined){
				$scope.productNameErr=true
				return;
			}else{
				$scope.productNameErr=false
			}
			
			console.log("product: "+JSON.stringify(product))
			var msg="";
			var 	url=licenceUrl+"/addProduct";
			genericFactory.add(msg,url,product).then(function(response) {
				vm.res = response.data;
				console.log("res : "+JSON.stringify(vm.res))
				loadProducts();		
				vm.product={}
				$scope.addNewProduct=false;

			});
		}
		function loadLicenceCount(){
			var msg="";
			var 	url=licenceUrl+"/getLicenceTypeWiseCount";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licaenceCounts = response.data;
				console.log("Licence  Count: "+JSON.stringify(vm.licaenceCounts))
								
			});
		}
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','deviceGrouping': 'Device Grouping','deskLocation': 'Location','assetType':'Asset Type','serialNo':'Serial No','assetId':'Asset Id','tagCode':'EPC','purchaseOrderNo':'Purchase Order No', 'invoiceNo':'InvoiceNo','invoiceDate':'Invoice Date','age':'Age','make':'Make','model':'Model','status':'Status'}
		$scope.checkSerialNo=function (serialNo){
			var msg=""
				 var url =assetUrl+"/checkBySerialNo?serialNo="+serialNo;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.serialRes = response.data;
				//console.log("serialRes: "+JSON.stringify(vm.serialRes))
				});
					
		}
		 $scope.exportData = function () {  
			  console.log("Exporting .........")
		        //get current system date.         
		        var date = new Date();  
		        $scope.CurrentDateTime = $filter('date')(new Date().getTime(), 'MM/dd/yyyy HH:mm:ss');          
		        //To convert Date[mm/dd/yyyy] from jsonDate "/Date(1355287447277)/"  
		        for(var i=0; i<$scope.searchCaseResult.length;i++)  
		        {  
		            $scope.searchCaseResult[i].DocCreatedDate;  
		            var dateString = $scope.searchCaseResult[i].DocCreatedDate.substr(6);  
		            var currentTime = new Date(parseInt(dateString));  
		            var month = currentTime.getMonth() + 1;  
		            var day = currentTime.getDate();  
		            var year = currentTime.getFullYear();  
		            var date = month + "/" + day + "/" + year;  
		            $scope.searchCaseResult[i].DocCreatedDate = date;              
		        }  
		        //Create XLS format using alasql.js file.  
		        alasql('SELECT * INTO XLS("SearchResult' + $scope.CurrentDateTime + '.xls",?) FROM ?', [allAssets, $scope.searchCaseResult]);  
		    };  
		$scope.newExcel= function(){
			
			
			
			$rootScope.loader=true;
	    	  getAllAssets();
			// $rootScope.loader=true;
				exportData();
	    	  document.getElementById('btnExport').click();
			
			}
		
		
		
		
		
		
		function addNew(){
			vm.asset={}
			$scope.addNew=true;
			$scope.uploadTab=false;
			$scope.addNewProduct=false;
			$scope.addNewAssociate=false;
			$scope.addNewType=false;
			loadBranch();
			loadAssocistes()
			loadProducts()
			
		}
		
		function cancle(){
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.addNewProduct=false;
			$scope.addNewAssociate=false;
			vm.asset={};
		
		}
		function upload(){
			$scope.addNew=false;
			$scope.uploadTab=true;
		}
		function edit(licence){
			$scope.addNew=true;
			vm.licence=licence;
			loadBranch()
			vm.licence.purchaseDate= new Date(licence.purchaseDate);
			vm.licence.installlationDate= new Date(licence.installlationDate);
			vm.licence.licenceExpiryDate= new Date(licence.licenceExpiryDate);
			
			
		}
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadLicence();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
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
				url=licenceUrl+"/getLincencceByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getLicenceCount"
			}else{
				url=licenceUrl+"/getLicenceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&type="+$scope.type;
				urlCount=licenceUrl+"/getLicenceCountAndSearch?searchText="+vm.serachText
			}
			
			
			//console.log("$scope.type :: "+$scope.type)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.licences = response.data;
				
				console.log("licencees: "+JSON.stringify(vm.licences ))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("Licence  Count: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
		
  //***********************Pagination End *****************************//
		function loadAssocistes(){
			var msg=""
				 var url =licenceUrl+"/getAssociates";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.associates = response.data;
				console.log("associates: "+JSON.stringify(vm.associates))
								
			});
		}
		//********************Pagination End *****************************//
		function loadProducts(){
			var msg=""
				 var url =licenceUrl+"/getProducts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.products = response.data;
				console.log("products: "+JSON.stringify(vm.products))
								
			});
		}
		
		
		
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				//console.log("branches: "+JSON.stringify(vm.branches))
								
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
			
	
			if(licence.associate==""||licence.associate==null){
				$scope.associateErr=true
				return;
			}else{
				$scope.associateErr=false
			}
			
			if(licence.product==""||licence.product==null){
				$scope.productErr=true
				return;
			}else{
				$scope.productErr=false
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
			 var url =licenceUrl+"/addLicence";
				genericFactory.add(msg,url,licence).then(function(response) {
					//console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadLicence();
					$scope.addNew=false;
					$scope.asset={}
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
			var url = licenceUrl + "/uploadLicence?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
			console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			.then(function successCallback(response) {
				
				$('.loading').hide();
				var filename="lincence_management"+$filter('date')(new Date(), "MM/dd/yyyy h:mma");+".txt"
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
