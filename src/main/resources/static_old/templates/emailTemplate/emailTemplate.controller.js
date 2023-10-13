(function() {
	'use strict';

	angular.module('myApp.emailTemplate').controller('EmailTemplateController', EmailTemplateController);

	EmailTemplateController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function EmailTemplateController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var emailUrl = ApiEndpoint.url+"email";
	
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addNew:addNew,
			
			save:save,
			edit:edit,
			cancle:cancle,
			delet:delet,
			showKeyWard:showKeyWard,
			close:close
		});

		(function activate() {
			
			loadTemplates();
		
		})();
		function showKeyWard(){
			$scope.showKeyWardTab=true
		}
		function loadTemplates(){
			var msg=""
				 var url =emailUrl+"/getAllEmailTemplate";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.emailTemplates = response.data;
				console.log("emailTemplate: "+JSON.stringify(vm.emailTemplates))
								
			});
		}
		
	
		
		function delet(template){
			var msg=""
				 var url =emailUrl+"/deletEmailTemplate";
				genericFactory.add(msg,url,template).then(function(response) {
				vm.emailTemplates = response.data;
				console.log("emailTemplate: "+JSON.stringify(vm.emailTemplates))
								
			});
		}

		function close(){
			$scope.showKeyWardTab=false
		}
		function addNew(){
			vm.template={}
			$scope.addNew=true;
			$scope.showKeyWardTab=false
			
		}
		
		
		function cancle(){
			$scope.addNew=false;
			$scope.showKeyWardTab=false
			vm.template={}
		
		}
		function edit(template){
			$scope.addNew=true;
			$scope.showKeyWardTab=false
			vm.template=template
			
		}
	//***********************Pagination Start*****************************//
		
		

		
		
		
		
		
		function save(template){
			
			//console.log("asset : "+JSON.stringify(asset))
			
			if(template==undefined||template.templateFor==undefined){
				$scope.templateForErr=true;
				return;
			}else{
				$scope.templateForErr=false;
			}
			if(template.subject==""||template.subject==undefined){
				$scope.subjectErr=true
				return;
			}else{
				$scope.subjectErr=false
			}
			if(template.templateBody==""||template.templateBody==undefined){
				$scope.templateErr=true
				return;
			}else{
				$scope.templateErr=false
			}
			if(template.signiture==""||template.signiture==undefined){
				$scope.signitureErr=true
				return;
			}else{
				$scope.signitureErr=false
			}
		
	
			
			
			template.addedBy=userDetail.firstName+" "+userDetail.lastName
			template.addedDate=new Date();
			var msg=""
			 var url =emailUrl+"/addEmailTemplate";
				genericFactory.add(msg,url,template).then(function(response) {
					//console.log("resp:"+JSON.stringify(response))
					//console.log("data :"+JSON.stringify(response.data.code))
					loadTemplates();
					$scope.addNew=false;
					vm.template={}
					if(response.data.code==200){
						toastr.success(response.data.message);
						$scope.diabledSaveButton=true
						
					}else{
						alert(response.data.message);
						$scope.diabledSaveButton=false
						
					}
					
			});
		}
	
	
	}

	
})();
