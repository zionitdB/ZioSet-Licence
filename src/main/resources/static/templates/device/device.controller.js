(function() {
	'use strict';

	angular.module('myApp.device').controller('DeviceController', DeviceController);

	DeviceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope'];
	
	/* @ngInject */
	function DeviceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope) {
		var zonedeviceUrl = ApiEndpoint.url+"device";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add:add,
			save:save,
			cancle:cancle,
			changeStatus:changeStatus,
			loadDevice:loadDevice,
			edit:edit,
			reload:reload,
			delet:delet,
			perPage : 10,
			total_count:0,
			pageno:1,
			serachText:"",
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
			
			loadDevice();
			loadDeviceCount();
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
			loadDevice();
			loadDeviceCount();
		}
		$scope.totalDevice=function (){
			$scope.type="all"
				loadDevice();
		}
		$scope.activeDevice=function (){
			$scope.type="active"
				loadDevice();
		}
		$scope.PuneDevice=function (){
			$scope.type="pune"
				loadDevice();
		}
		$scope.bengaluruDevice=function (){
			$scope.type="bengaluru"
				loadDevice();
		}
		function loadDeviceCount(){
			var msg=""
				 var url =zonedeviceUrl+"/getAllDeviceCount";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.deviceCount = response.data;
				console.log("deviceCount: "+JSON.stringify(vm.deviceCount))
								
			});
		}
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadDevice();
			
		}
		$scope.searchByPagination=function (search){
			loadDevice();
			
		}
		$scope.checkMacID=function (macId){
			console.log("macId: "+macId)
			var msg=""
				 var url =zonedeviceUrl+"/checkDeviceMacID?macId="+macId;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.responceObj = response.data;
				console.log("responceObj: "+JSON.stringify(vm.responceObj))
								
			});
		}
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		function loadDevice() {
		     var msg=""
		    	 var url=""
		    		 var urlCount=""
		    	 if(vm.serachText==""||vm.serachText==undefined){
						url=zonedeviceUrl+"/getDeviceByLimit/"+vm.pageno+"/"+vm.perPage+"/"+$scope.type;
						urlCount=zonedeviceUrl+"/getDeviceCount/"+$scope.type;
					}else{
						url=zonedeviceUrl+"/getDeviceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
						urlCount=zonedeviceUrl+"/getDeviceCountAndSearch?searchText="+vm.serachText;
					}
					
					
		/*	 var url =zonedeviceUrl+"/getAllDevice";*/
			genericFactory.getAll(msg,url).then(function(response) {
			vm.devices = response.data;
			console.log("devices: "+JSON.stringify(vm.devices))
			
			
		});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
	}
		function edit(device){
			$scope.addNew=true;
			vm.responceObj={}
			vm.responceObj.code=200
			vm.device=device;
			loadBranch();
		}
		function add(){
			$scope.addNew=true;
			$scope.device={}
			loadBranch();
			
		}

		function cancle(){
			
			$scope.addNew=false;
		
		}
		function changeStatus(device){
			var msg=""
				 var url =zonedeviceUrl+"/updateDeviceStatus";
				genericFactory.add(msg,url,device).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
				}else{
					toastr.error(response.data.message);

				}
				loadDevice();
				
			});
		}
		function delet(device){
			var msg=""
				 var url =zonedeviceUrl+"/deletDevice";
				genericFactory.add(msg,url,device).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
				}else{
					toastr.error(response.data.message);

				}
				loadDevice();
				
			});
		}
		function save(device){
			if(device==undefined||device.branch==""){
				$scope.branchErr=true
				return;
			}else{
				$scope.branchErr=false	
				
			}
			if(device.deviceType==undefined||device.deviceType==""){
				$scope.deviceTypeErr=true
				return;
			}else{
				$scope.deviceTypeErr=false	
				
			}
			if(device.locationName==""||device.locationName==undefined){
				$scope.locationNameErr=true
				return;
			}else{
				$scope.locationNameErr=false	
			}
			if(device.deviceName==""||device.deviceName==undefined){
				$scope.deviceNameErr=true
				return;
			}else{
				$scope.deviceNameErr=false	
			}
			
			
			if(device.macId==""||device.macId==undefined){
				$scope.macErr=true
				return;
			}else{
				$scope.macErr=false	
			}
			if(vm.responceObj.code==500){
				return;
			}
			
			if(device.deviceType=="1"||device.deviceType==1){
				if(device.antenaNo==""||device.antenaNo==undefined){
					$scope.antenaNoErr=true
					return;
				}else{
					$scope.antenaNoErr=false	
				}
				
			}
			console.log("device: "+JSON.stringify(device))

			device.addedBy=	userDetail.firstName+" "+userDetail.lastName
			device.addedDate=new Date();
			device.active=1
			
			var msg=""
				 var url =zonedeviceUrl+"/addNewDevice";
				genericFactory.add(msg,url,device).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				vm.device={}
				$scope.addNew=false;
				if(response.data.code==200){
					toastr.success(response.data.message);
					$scope.addNew=false;

				}else{
					toastr.error(response.data.message);

				}
				loadDevice();
			});
			
		}
			
		
	}

	
})();
