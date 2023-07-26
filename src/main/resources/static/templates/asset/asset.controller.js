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
			user:userDetails,
			upload:upload,
			uploadSave:uploadSave,
			cancle:cancle,
			save:save,
			edit:edit,
			delet:delet,
			assingeTag:assingeTag,
			print:print,
			loadAssets:loadAssets,
			reload:reload,
			upateTags:upateTags,
			selectAllChk: false,
			print:print,
			registerType:"",
			allAssets:[],
			serialRes:{}
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
			$rootScope.loader=false;

			$scope.alertType=false;
			//console.log("pageno: "+vm.pageno)
			//console.log("perPage: "+vm.perPage)
			console.log("ROOT BRANCH: "+	$rootScope.menuBranch)
			loadAssets();
			loadAssetCounts();
			$scope.dataQR="";
			$scope.type="all"
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
			loadAssets();
			loadAssetCounts();
		}
		
		
		$scope.totalAsset=function (){
			$scope.type="all"
				loadAssets();
		}
		$scope.tagRegisterAsset=function (){
		//	console.log("tagRegisterAsset")
			$scope.type="register"
				loadAssets();
		}
		$scope.assingedAsset=function (){
			$scope.type="assinged"
				loadAssets();
		}
		$scope.eOLAsset=function (){
			$scope.type="eol"
				loadAssets();
		}
		$scope.puneAsset=function (){
			$scope.type="pune"
				loadAssets();
		}
		$scope.bengaluruAsset=function (){
			$scope.type="bengaluru"
				loadAssets();
		}
		
		function loadAssetCounts(){
			var msg=""
				 var url =assetUrl+"/getAssetDetialsCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.assetCounts = response.data;
				//console.log("assetCounts: "+JSON.stringify(vm.assetCounts))
								
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
				 $rootScope.loader=true;
				exportData();
	    	  //document.getElementById('btnExport').click();
			
			}
		
		
		function getAllAssets(){
			var msg=""
				 var url =assetUrl+"/getAllAsseta1";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.allAssets1 = response.data;
				angular.forEach(vm.allAssets1, function(value, key) {
					var emp;
					var srNo=key+1;
					emp=value
					emp.srNo=srNo=key+1;
					vm.allAssets.push(emp);
					});
				setTimeout(function(){
					 
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
					 
					  $rootScope.$digest();
					},1000);		
				
				//console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		
		$scope.selectQR = function (index) {
			vm.assets[index].check = !vm.assets[index].check;

			if (vm.assets[index].check == true) {
				$scope.selectedDataCounter++;
			} else
				$scope.selectedDataCounter--;

			if ($scope.selectedDataCounter == vm.assets.length)
				vm.selectAllChk = true;
			else {
				vm.selectAllChk = false;
			}

			
		}
		$scope.selectAllTable = function () {
			for (var index in vm.assets) {
				vm.assets[index].check = vm.selectAllChk;
				//console.log("SEL "+JSON.stringify(vm.assets[index]))
			}
		
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
		
		function upateTags(){
			//console.log("Asste "+JSON.stringify(vm.asset));
			//console.log("selecteTag "+JSON.stringify(vm.selecteTag.tagCode));
			vm.asset.tagCode=vm.selecteTag.tagCode
			
			var msg=""
				 var url =assetUrl+"/assignedTag";
					genericFactory.add(msg,url,vm.asset).then(function(response) {
						//console.log("resp:"+JSON.stringify(response))
						//console.log("data :"+JSON.stringify(response.data.code))
						loadAssets();
						$scope.viewQRTab=false;
						$scope.asset={}
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
			loadBranch()
			
		}
		function assingeTag(asset){
			vm.asset=asset
			$scope.addNew=false;
			$scope.uploadTab=false;
			$scope.viewQRTab=true;
			loadAvailableTags();
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
			console.log("deviceGrouping: "+JSON.stringify(asset.deviceGrouping))
			console.log("deskLocation: "+JSON.stringify(asset.deskLocation))
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
			
			
			//console.log("$scope.type :: "+$scope.type)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				
			//	console.log("assets: "+JSON.stringify(vm.assets))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
			//	console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
		
  //***********************Pagination End *****************************//
		function loadAvailableTags(){
			var msg=""
				 var url =tagUrl+"/getAllAvailableTags";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.availableTags = response.data;
				//console.log("availableTags: "+JSON.stringify(vm.availableTags))
								
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
			
			if(asset==undefined||asset.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(asset.assetType==""||asset.assetType==undefined){
				$scope.assetTypeErr=true
				return;
			}else{
				$scope.assetTypeErr=false
			}
			if(asset.serialNo==""||asset.serialNo==undefined){
				$scope.serialNoErr=true
				return;
			}else{
				$scope.serialNoErr=false
			}
		/*	if(asset.assetId==""||asset.assetId==null){
				$scope.assetIdErr=true
				return;
			}else{
				$scope.assetIdErr=false
			}
			*/
			if(asset.status==""||asset.status==null){
				$scope.statusErr=true
				return;
			}else{
				$scope.statusErr=false
			}
			if(asset.make==""||asset.make==null){
				$scope.makeErr=true
				return;
			}else{
				$scope.makeErr=false
			}
			if(asset.model==""||asset.model==null){
				$scope.modelErr=true
				return;
			}else{
				$scope.modelErr=false
			}
		if(vm.serialRes.code==500){
			return;
		}
			
			
			asset.added_by=userDetails.firstName+" "+userDetails.lastName
			asset.addedDate=new Date();
			var msg=""
			 var url =assetUrl+"/addNewAsset";
				genericFactory.add(msg,url,asset).then(function(response) {
					//console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadAssets();
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
			var url = assetUrl + "/uploadAsset?uploadBy="+userDetail.firstName+" "+userDetail.lastName;
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
		
		
		function download(filename, text) {
			  var element = document.createElement('a');
			  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
			  element.setAttribute('download', filename);

			  element.style.display = 'none';
			  document.body.appendChild(element);

			  element.click();

			  document.body.removeChild(element);
			}
		
		
		
		
		
	function print(){
			
			$scope.printSizeErr=false;
			
		
		
		var selCount=0
		for (var i in vm.assets) {
			if (vm.assets[i].check) {
				selCount++;
			}
		}
		if(selCount==0){
			
			alert("Select atleast one barcode")
				//genericFactory.showAlert(containt);
			return;
		}
		printCode();
			
		console.log(" Caqll to   ")
			
			
			
		}
	 function printCode(){
		 console.log(" Called   ")
			var windowContent = '';
			var count=0
		 angular.forEach(vm.assets,function(value,index){
			//console.log(" index  "+index)
							//console.log("SEL "+JSON.stringify(vm.tags))

				if(vm.assets[index].check){
					
				//	console.log("SEL qq  "+JSON.stringify(vm.assets[index].check))

		    		var idName='print' + index;
					//console.log(" idName "+idName)

		    		//var dataUrl = document.getElementById('print' + index).toDataURL();
					var canvas = document.getElementById('print' + index).querySelector('canvas');
					var dataURL = canvas.toDataURL();
		    		//windowContent += '<div style="page-break-after: always"><div style="width:350px;height:180px;border:1px solid"><span style="height:45px;witdh:100px; margin: -75px 0px 0px 5px ;important;"><span style="height:100px;witdh:100px;padding:5px;margin-left:20px" src="'  + dataUrl + '</span></div>';
		    	///	console.log("dataUrl  :: "+dataURL)
						//12 X 50 
					//	console.log("SIZE  IN 12 50 cut ")
				windowContent+=	'<div style="page-break-after: always ;important;margin:10px;margin-top:20px" ><div style="width:132px;height:56px;"><img src="'  + dataURL + '"></div></div>'
						

					
					

		    	}
				
				
         
     })
   
			var popupWinindow = window.open('','_blank','width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
			popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
			popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
			popupWinindow.document.close();
		//	savePrintedCode()
	    
		
	 }	
	/*function print() {
			
			var innerContents = document.getElementById('printableArea').innerHTML;
		//	innerContents = '<div><qr text="'+voucherPvmsMap.qr_code+'" type-number="2" correction-level="correctionLevel" size="50" input-mode="inputMode" image="image"></qr><br><span>'+voucherPvmsMap.qr_code+'</span></div>';
			var popupWinindow = window
					.open(
							'',
							'_blank',
							'width=350,height=200,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
			popupWinindow.document.open();
			
			
			popupWinindow.document.write('<html><head><title> QR Code Print </title>');
			popupWinindow.document.write('</head><body onload="window.print()">');
			
		        //Append the external CSS file.
		    popupWinindow.document.write('<link rel="stylesheet" href="css/root.css">');
		  //  popupWinindow.document.write('<link rel="stylesheet" type="text/css" href="<?php echo base_url();?>assets_login/css/main.css">');
				//Append the DIV contents.
			popupWinindow.document.write(innerContents);
		
		    popupWinindow.document.write('</body></html>');
		    popupWinindow.document
			.write('<style> @page {  margin: 10px;} </style>');
			popupWinindow.document.close();
			
		}*/
		
	}

	
})();
