(function() {
	'use strict';

	angular.module('myApp.asset').controller('AssetController', AssetController);

	AssetController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function AssetController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var commonUrl = ApiEndpoint.url+"common";
		var tagUrl = ApiEndpoint.url+"tag";
		var assetUrl = ApiEndpoint.url+"asset";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
		
			addNew:addNew,
			upload:upload,
			uploadSave:uploadSave,
			cancle:cancle,
			save:save,
			edit:edit,
			delet:delet,
			loadAssets:loadAssets,
			viewConsole:viewConsole
			
		});

		(function activate() {
			$scope.type="all"
			loadAssets();
			loadAssetCounts();
		
			
		})();
	
		function viewConsole(asset){
			if(asset.systemIp!=null){
				$rootScope.loader=true;
				console.log("NO IP FOND")
				console.log("asset: "+JSON.stringify(asset.systemIp))
				var urlCheck="http://"+asset.systemIp+":8088/config/checkLink";
				var url="http://"+asset.systemIp+":8088/#!/updateConfig";
			console.log("url: "+urlCheck)
			
			
			  $http({
				    method : "GET",
				      url : urlCheck
				  }).then(function mySuccess(response) {
					  console.log("RESOINCE FOUND  "+JSON.stringify(response))
					  window.open(url,'CINSIOLEE');

						$rootScope.loader=false;
					  
				  }, function myError(response) {
					  console.log("RESOINCE NOT  ")
						$rootScope.loader=false;
					  toastr.error("Network Error..........Can not connect to console");
				  });

			}else{
				toastr.error("IP is not Present..........Can not connect to console");
			}
			

		}
		$scope.totalAsset=function (){
			$scope.type="all"
				loadAssets();
		}
		
		$scope.assingedAsset=function (){
			$scope.type="assinged"
				loadAssets();
		}
		
		
		function loadAssetCounts(){
			var msg=""
				 var url =assetUrl+"/getAssetDetialsCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assetCounts = response.data;
				console.log("assetCounts: "+JSON.stringify(vm.assetCounts))
								
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
	  
		$scope.newExcel= function(){
			$rootScope.loader=true;
	    	  getAllAssets();
				 $rootScope.loader=true;
				//document.getElementById('btnExport').click();
			
			}
		
		
		function getAllAssets(){
			var msg=""
				 var url =assetUrl+"/getAllAsset/"+$scope.type;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allAssets = response.data;
				setTimeout(function(){
					 
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
					 
					  $rootScope.$digest();
					},1000);
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		
		
		
		function delet(asset){
			
			
			var msg=""
				 var url =assetUrl+"/deleteAsset";
				genericFactory.add(msg,url,asset).then(function(response) {
					loadAssets();
					console.log("data :"+JSON.stringify(response.data))

					if(response.data.code==200){
						toastr.success(response.data.message);
						
						
					}else{
						toastr.error(response.data.message);
						
					}
			});
		}
	
		function addNew(){
			vm.asset={}
			$scope.addNew=true;
			$scope.uploadTab=false;
			$scope.viewQRTab=false;
			$scope.diabledSaveButton=false
			loadBranch()
			
		}
		
		function cancle(){
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.viewQRTab=false;
			vm.asset={};
		
		}
		function upload(){
			$scope.addNew=false;
			$scope.uploadTab=true;
		}
		function edit(asset){
			console.log("asset: "+JSON.stringify(asset))

			loadBranch()
			$scope.addNew=true;
			$scope.viewQRTab=false;
			$scope.uploadTab=false;
			$scope.diabledSaveButton=false;
			vm.asset=asset;
			vm.asset.invoiceDate= new Date(asset.invoiceDate);
			vm.asset.assetType=asset.assetType
			vm.asset.status=asset.status
			vm.asset.age=parseInt(asset.age)
			window.scrollTo(0, 0);

			function selectElement() {    
			    let element = document.getElementById("selAssetType");
			    element.value = asset.assetType;
			}
			
		}
	//***********************Pagination Start*****************************//
		$scope.checkSerialNo=function (serialNo){
			var msg=""
				 var url =assetUrl+"/checkBySerialNo?serialNo="+serialNo;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.serialRes = response.data;
				console.log("serialRes: "+JSON.stringify(vm.serialRes))

				});
		}
		$scope.searchByPagination=function (search){
			loadAssets();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAssets();
			
		}
		
		function loadAssets(){
			if(vm.perPage>=1000){
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
	
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=assetUrl+"/getAssetsByLimit/"+vm.pageno+"/"+vm.perPage+"/"+$scope.type;
				urlCount=assetUrl+"/getAssetCount/"+$scope.type
			}else{
				url=assetUrl+"/getAssetsByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&type="+$scope.type;
				urlCount=assetUrl+"/getAssetCountAndSearch?searchText="+vm.serachText+"&type="+$scope.type
			}
			
			
			console.log("urlCount:: "+urlCount)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				
			//	console.log("assets: "+JSON.stringify(vm.assets))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
								
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
		
		function save(asset){
			
			//console.log("asset : "+JSON.stringify(asset))
			
			if(asset==undefined||asset.make==undefined){
				$scope.makeErr=true
				return;
			}else{
				$scope.makeErr=false;
			}
			if(asset.model==""||asset.model==null){
				$scope.modelErr=true
				return;
			}else{
				$scope.modelErr=false
			}
			if(asset.serialNo==""||asset.serialNo==undefined){
				$scope.serialNoErr=true
				return;
			}else{
				$scope.serialNoErr=false
			}
		
			
			
		if(vm.serialRes.code==500){
			return;
		}
			
			
			asset.added_by=userDetails.firstName+" "+userDetails.lastName
			asset.addedDate=new Date();
			var msg=""
			 var url =assetUrl+"/addNewAsset";
				genericFactory.add(msg,url,asset).then(function(response) {
					console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadAssets();
					loadAssetCounts()
					$scope.addNew=false;
					$scope.diabledSaveButton=true
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
			var url = assetUrl + "/uploadAssetNEW?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
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
				loadAssetCounts();
			}, function errorCallback(response) {
		    	$('.loading').hide();
				
		    	document.getElementById("uploadAssets").value=null
				toastr.error('Upload....', 'UnSuccesful !!');
				$scope.uploadTab=false;
				loadAssets();
				loadAssetCounts();
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
