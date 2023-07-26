(function() {
	'use strict';

	angular.module('myApp.updateTagRegistration').controller('UpdateTagRegistrationController', UpdateTagRegistrationController);

	UpdateTagRegistrationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http'];
	
	/* @ngInject */
	function UpdateTagRegistrationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http) {
	
		var assetEmpMappedUrl = ApiEndpoint.url+"assetEmpMapped";
		var assetUrl = ApiEndpoint.url+"asset";
		var tagUrl = ApiEndpoint.url+"tag";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			
			checkSerialNoAssetId:checkSerialNoAssetId,
			update:update,
			remove:remove,
			saveupdate:saveupdate
			
		});

		(function activate() {
			$scope.rfidTagErr=false;
			$scope.rfidTagVrf=false;
			$scope.tagMan=false
		})();
		function checkSerialNoAssetId(assetId){
			var msg=""
				var requestObj={};
			requestObj.assetId=assetId;
				 var url =assetUrl+"/checkAssetByIdSerialNo";
				genericFactory.add(msg,url,requestObj).then(function(response) {
				vm.responce = response.data;
				if(vm.responce.code=="200"){
					$scope.validID=true
					$scope.invalidID=false
				}else{
					$scope.invalidID=true
					$scope.validID=false
				}
				console.log("response: "+JSON.stringify(response))
								
			});
			
		}
		function update(asset){
			
			$scope.updateTab=true
			$scope.removeTab=false
		}
		function remove(asset){
			$scope.updateTab=false
			$scope.removeTab=true
			console.log("asset: "+JSON.stringify(asset))
			 makeAvailalbeTag(asset.assetId);
			asset.assetId=null
			asset.tagAlllocationStatus=0
			asset.tagCode=null
			var msg=""
				 var url =assetUrl+"/updateAsset";
				genericFactory.add(msg,url,asset).then(function(response) {
					toastr.success("Tag Removed Successfully");
						
			});
		}
		function makeAvailalbeTag(assetId){
			
			var msg=""
				 var url =assetUrl+"/makeAvailalbeTag?assetId="+assetId;
				genericFactory.getAll(msg,url).then(function(response) {
			
						
			});
		}
		$scope.checkRfidTag=function(rfidTag){
			console.log("rfidTag "+rfidTag)
			var msg=""
				 var url =tagUrl+"/checkrfidTag?rfidTag="+rfidTag;
					genericFactory.getAll(msg,url).then(function(response) {
						console.log("resp:"+JSON.stringify(response))
						var resData=response.data
						if(resData.code==200){
							$scope.rfidTagErr=false;
							$scope.rfidTagVrf=true;
							$scope.tagMan=false
							$scope.rfidTagMsg=resData.message
							vm.mappedTag=response.data.data
							
							
							console.log("mappedTag:"+JSON.stringify(vm.mappedTag))
							
							
							
							
							
							
						}else{
							$scope.rfidTagMsg=resData.message
							$scope.rfidTagErr=true;
							$scope.rfidTagVrf=false;
							return;
						}
						console.log("data :"+JSON.stringify(response.data.code))
						
						
				});
		}
		
		
		function saveupdate(asset){
			if($scope.rfidTagVrf){
				console.log("vm.mappRFID :"+JSON.stringify(vm.mappRFID))
				asset.assetId=vm.mappedTag.hostname
				asset.tagCode=vm.mappedTag.tagCode
				
				$scope.updateTab=false
				vm.asset.rfidTag="";
				
				var msg=""
					 var url =assetUrl+"/updateTag";
					genericFactory.add(msg,url,asset).then(function(response) {
						toastr.success("Tag Update Successfully");
							
				});
			}else{
				return;
			}
			
			
		}
		
		
	}

	
})();
