(function() {
	'use strict';

	angular.module('myApp.tagUpload').controller('TagUploadController', TagUploadController);

	TagUploadController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','fileUpload','$http','$filter'];
	
	/* @ngInject */
	function TagUploadController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,fileUpload,$http,$filter) {
		var tagUrl = ApiEndpoint.url+"tag";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			tags:[],
			user:userDetail,
			save:save,
			cancle:cancle,
			changeStatus:changeStatus,
			add:add,
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadTags:loadTags,
			upload:upload,
			uploadSave:uploadSave,
			selectAllChk: false,
			print:print,
			cancle:cancle,
			cancleUpload:cancleUpload,
			delet:delet,
			downloadTags:downloadTags,
			reload:reload
		});

		(function activate() {
			console.log("ROOT BRANCH: "+	$rootScope.menuBranch)
			if($rootScope.menuBranch==0){
				$scope.type="all"
			}
			if($rootScope.menuBranch==1){
				$scope.type="pune"
			}
			if($rootScope.menuBranch==2){
				$scope.type="bengaluru"
			}
			//$scope.type="all"
			$scope.alertType=false;
			loadTags();
			loadCount();
			$rootScope.loader=false;
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
			loadTags();
		}
		$scope.totalTag=function (){
			$scope.type="all"
				loadTags();
		}
		$scope.availableTags=function (){
			$scope.type="available"
				loadTags();
		}
		$scope.assingedTags=function (){
			$scope.type="assinged"
				loadTags();
		}
		$scope.puneTags=function (){
			$scope.type="pune"
				loadTags();
		}
		$scope.bengaluruTags=function (){
			$scope.type="bengaluru"
				loadTags();
		}
		function cancleUpload(){
			
			$scope.uploadTab=false;
		}
		function cancle(){
			$scope.addNew=false;
			
		}
		
		$scope.filename="TagUpload"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Branch','hostname':'Hostname','tagCode':'Tag Code','ntId':'NTID','empName':'Name'}
	
		function downloadTags(){
			
			 $rootScope.loader=true;
			
			var msg=""
				 var url =tagUrl+"/getDownloadTags";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.tagsU = response.data;
				//console.log("Downloaded TAg ::"+vm.tagsU.length  )
				setTimeout(function(){
					 
					 document.getElementById('btnExport').click();
					 $rootScope.loader=false;
					 
					  $rootScope.$digest();
					},9000);		
				
				//console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
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
		$scope.checkTagCode=function (tagcode){
			var msg=""
				 var url =tagUrl+"/checkTagCode?tagCode="+tagcode;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.tagCodeValRes = response.data;
				if(vm.tagCodeValRes.code==500){
					$scope.uniqueTagCodeVal=true
				}else{
					$scope.uniqueTagCodeVal=false
				}
				//console.log("tagCodeValRes: "+JSON.stringify(vm.tagCodeValRes))
								
			});
		}
		$scope.checkHostname=function (hostName){
			var msg=""
				 var url =tagUrl+"/checkHostName?hostname="+hostName;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.hostValRes = response.data;
				if(vm.hostValRes.code==500){
					$scope.uniqueHostNameVal=true
				}else{
					$scope.uniqueHostNameVal=false
				}
				//console.log("hostValRes: "+JSON.stringify(vm.hostValRes))
								
			});
		}
		$scope.selectQR = function (index) {
			vm.tags[index].check = !vm.tags[index].check;

			if ($scope.tags[index].check == true) {
				$scope.selectedDataCounter++;
			} else
				$scope.selectedDataCounter--;

			if ($scope.selectedDataCounter == vm.tags.length)
				vm.selectAllChk = true;
			else {
				vm.selectAllChk = false;
			}

			
		}
		$scope.selectAllTable = function () {
			for (var index in vm.tags) {
				vm.tags[index].check = vm.selectAllChk;
				//console.log("SEL "+JSON.stringify(vm.tags[index]))
			}
		
		}
		
		function add(){
			$scope.addNew=true;
			loadBranch();
		}
		function upload(){
			$scope.addNew=false;
			$scope.uploadTab=true;
		}
		function loadCount(){
			var msg=""
				 var url =tagUrl+"/getTagCount";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.tagCounts = response.data;
				//console.log("tagCounts: "+JSON.stringify(vm.tagCounts))
								
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
		function delet(tag){
			
			var msg=""
				 var url =tagUrl+"/deletTags";
				genericFactory.add(msg,url,tag).then(function(response) {
					loadTags();
					if(response.data.code==200){
						toastr.success(response.data.message);
						
						
					}else{
						toastr.error(response.data.message);
						
					}
								
			});
		}
	function cancle(){
		$scope.tag={}
		$scope.addNew=false;
	}
	
	$scope.pagination = {
	        current: 1
	    };
	
	// page changed 
	$scope.pageChanged = function(pageNo){
		vm.pageno=pageNo;
		loadTags();
		
	}
		
		
		function loadTags(){
			

			if(vm.perPage>=1000){
				//console.log("MORE THAN 100")
				vm.perPage=100
			}
			
			var dataReq=""
				//console.log("userDetail"+JSON.stringify(userDetail))
				
			var url=""
				var urlCount=""
				var msg=""
				if(vm.serachText==""||vm.serachText==undefined){
					url=tagUrl+"/getTagsByLimit/"+vm.pageno+"/"+vm.perPage+"/"+$scope.type;
					urlCount=tagUrl+"/getTagCount/"+$scope.type
				}else{
					url=tagUrl+"/getTagsByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&dataReq="+dataReq+"&type="+$scope.type;
					urlCount=tagUrl+"/getTagCountAndSearch?searchText="+vm.serachText+"&dataReq="+dataReq+"&type"+$scope.type
				}
			console.log(" url : "+url)
					//console.log(" urlCount : "+urlCount)
				genericFactory.getAll(msg,url).then(function(response) {
					vm.tags1 = response.data;
					vm.tags=[];
					angular.forEach(vm.tags1, function(value) {
						 var tag={};
						 tag=value;
						 tag.barcode=value.tagCode
						 vm.tags.push(tag)
						});
					//console.log(" tags : "+JSON.stringify(vm.tags))
									
				});
				
				genericFactory.getAll(msg,urlCount).then(function(response) {
					
					vm.total_count= response.data;
					//console.log("assetCount: "+JSON.stringify(vm.assetCount))
									
				});
		}
		
		function save(tag){
			$scope.diabledSaveButton=true
			//console.log("TAG"+JSON.stringify(userDetail))
			if(tag==undefined||tag.branch==undefined){
				$scope.branchErr=true;
				return;
			}else{
				$scope.branchErr=false;
			}
			if(tag.tagCode==""||tag.tagCode==undefined){
				$scope.tagCodeErr=true;
				return;
			}else{
				$scope.tagCodeErr=false;
			}
			if(tag.hostname==undefined||tag.hostname==""){
				$scope.hostnameErr=true;
				return;
			}else{
				$scope.hostnameErr=false;
			}
				//console.log("$scope.uniqueHostNameVal:"+$scope.uniqueHostNameVal)
				//console.log("$scope.uniqueTagCodeVal:"+$scope.uniqueTagCodeVal)

			if($scope.uniqueHostNameVal){
				return;
			}
			if($scope.uniqueTagCodeVal){
				return;
			}
			tag.addedBy=userDetail
			var msg=""
			 var url =tagUrl+"/addNewTags";
				genericFactory.add(msg,url,tag).then(function(response) {
					//console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadTags();
					$scope.addNew=false;
					$scope.diabledSaveButton=false
					vm.tag={}
					if(response.data.code==200){
						toastr.success(response.data.message);
						
						
					}else{
						toastr.error(response.data.message);
						
					}
					
			});
		}
		
		function changeStatus(tag){
			tag.statusBit=2;
			var msg="Tag status updated sucessfully"
				 var url =assetRegistationUrl+"/updateTag";
				genericFactory.add(msg,url,tag).then(function(response) {
					loadTags();
								
			});
			
			
		}
		
		function uploadSave(){
			
			$scope.diabledSaveButton=true
			$rootScope.loader=true;
			//console.log("diabledSaveButton :: "+$scope.diabledSaveButton)
			var file = document.getElementById('uploadTags').files[0];
			

			if (file == undefined) {
				$scope.uploadErr=true
				$scope.diabledSaveButton=false
				
				$rootScope.loader=false;
				return;
			}else{
				$scope.uploadErr=false
			}

			var fileName = file.name;
			var extension = ".xlsx";
			var extension1 = ".xls";
			//console.log("Format  "+fileName.includes(extension))

			//console.log("Format 1 "+fileName.includes(extension1))
			if(!fileName.includes(extension1)){
				toastr.error('Selected File is not a xlsx or xls');
				$scope.diabledSaveButton=false
				$rootScope.loader=false;
				return;
			}			

			$('.loading').show();
			var fd = new FormData();
			fd.append('file', file);
			var url = tagUrl + "/uploadTags?uploadedBy="+userDetail.userId
			//console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			.then(function successCallback(response) {
				//console.log("resp:"+JSON.stringify(response))
				$('.loading').hide();
				var filename="tag_upload_log_"+$filter('date')(new Date(), "MM/dd/yyyy h:mma");+".txt"
					var text=response.data.resmessage
				download(filename, text) 
				//window.alert("File uploaded successfully!");
				//angular.element("uploadTags").val(null);
				$scope.uploadTab=false
				$scope.diabledSaveButton=false
				$rootScope.loader=false;
				document.getElementById("uploadTags").value=null
				toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });		
				$scope.uploadTab=false;
				loadTags();
			}, function errorCallback(response) {
		    	$('.loading').hide();
		    	//console.log("resp:"+JSON.stringify(response))
				//window.alert("File upload - unsuccessfull!");
				//init();
		    	$scope.uploadTab=false
		    	document.getElementById("uploadTags").value=null
		    	//angular.element("uploadTags").val(null);
				toastr.error('Upload....', 'UnSuccesful !!');
				$scope.uploadTab=false;
				$rootScope.loader=false;
				loadTags();
				$scope.diabledSaveButton=false
					    });

			angular.element("uploadTags").val(null);
		}
		
		
		function print(){
			
			$scope.printSizeErr=false;
			
		
		
		var selCount=0
		for (var i in vm.tags) {
			if (vm.tags[i].check) {
				selCount++;
			}
		}
		if(selCount==0){
			
			alert("Select atleast one barcode")
				//genericFactory.showAlert(containt);
			return;
		}
		printCode();
			
		//console.log(" Caqll to   ")
			
			
			
		}
		
		 function printCode(){
			 
			 //  custome 28
			 //console.log(" Called   ")
				var windowContent = '';
				var count=0
			 angular.forEach(vm.tags,function(value,index){
				//console.log(" index  "+index)
								////console.log("SEL "+JSON.stringify(vm.tags))
var newInx=parseInt(vm.pageno-1)*10+parseInt(index)
/*//console.log("vm.pageno "+vm.pageno)
//console.log("parseInt(vm.pageno-1)  "+parseInt(vm.pageno-1))
								//console.log("newInx"+newInx)
*/

						//console.log("TAG  "+JSON.stringify(vm.tags[newInx]))
						
						
						//console.log("lenth  "+JSON.stringify(vm.tags.length))
						//console.log("index  "+newInx)
						if(newInx<vm.tags.length){
							//console.log("OK")
							if(vm.tags[newInx].check){
								
								//console.log("TAG  "+JSON.stringify(vm.tags[newInx]))

					    		var idName='print' + index;
								//console.log(" idName "+idName)

					    		//var dataUrl = document.getElementById('print' + index).toDataURL();
								var canvas = document.getElementById('print' + index).querySelector('canvas');
								var dataURL = canvas.toDataURL();
					    		//windowContent += '<div style="page-break-after: always"><div style="width:350px;height:180px;border:1px solid"><span style="height:45px;witdh:100px; margin: -75px 0px 0px 5px ;important;"><span style="height:100px;witdh:100px;padding:5px;margin-left:20px" src="'  + dataUrl + '</span></div>';
					    		//console.log("dataUrl  :: "+dataURL)
									//12 X 50 
									//console.log("SIZE  IN 12 50 cut ")
							windowContent+=	'<div style="page-break-after: always ;important;margin-left:20px;margin-top:40px" ><span style=" font-family: Arial;font-size: 50px;"><b><span style="text-align:center;margin-left:40px">'+vm.tags[index].hostname+'</span></b></span></br><div  "><span style=" font-family: Arial"><img src="'  + dataURL + '"></span></div></div>'
							//windowContent+=	'<div style="page-break-after: always ;important;margin-left:20px;margin-top:40px" ><span style=" font-family: Arial;font-size: 50px;"><b>DATTATRAY BODHALE<b><span></div>'
				

								
								

					    	}
						}

					
					
					
             
         })
       
				var popupWinindow = window.open('','_blank','width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
				popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
				popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
				popupWinindow.document.close();
			//	savePrintedCode()
		    
			
		 }	
	}

	
})();
