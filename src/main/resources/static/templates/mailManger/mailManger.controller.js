(function() {
	'use strict';

	angular.module('myApp.mailManger').controller('MailMangerController', MailMangerController);

	MailMangerController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http'];
	
	/* @ngInject */
	function MailMangerController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http) {
		var emailUrl = ApiEndpoint.url+"email";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			changeStatuts:changeStatuts,
			save:save,
			addNew:addNew,
			cancle:cancle,
			saveNewReciever:saveNewReciever,
			addNewReciever:addNewReciever,
			changeStatutsemailReceiver:changeStatutsemailReceiver,
			editEmailReceiver:editEmailReceiver,
			editEmailSender:editEmailSender,
			deletEmailReceiver:deletEmailReceiver,
			reload:reload,
			
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
			loadSenderMailList()
			loadRecieverMailList()
			loadMailRecieverCount()
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
			loadRecieverMailList()
			loadMailRecieverCount()
		}
		$scope.totalReceiver=function (){
			$scope.type="all"
			loadRecieverMailList()
		}
		$scope.activeReceiver=function (){
			$scope.type="active"
			loadRecieverMailList()
		}
		$scope.puneReceiver=function (){
			$scope.type="pune"
			loadRecieverMailList()
		}
		$scope.bengaluruReceiver=function (){
			$scope.type="bengaluru"
			loadRecieverMailList()
		}
		function loadMailRecieverCount(){
			var msg=""
				 var url =emailUrl+"/getMailCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.mailCounts = response.data;
				console.log("mailCounts: "+JSON.stringify(vm.mailCounts))
								
			});
		}
		function addNew(){
			$scope.addNewTab=true
			loadBranch();
		}
		function editEmailReceiver(emailReceiver){
			$scope.addNewRecieverTab=true
			vm.email=emailReceiver
			loadBranch()
			
		}
		function editEmailSender(emailSender){
			$scope.addNewTab=true
			vm.email=emailSender
			
			
			
		}
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		function deletEmailReceiver(emailReceiver){
			var msg=""
				 var url =emailUrl+"/deletEmailReceiver";
				genericFactory.add(msg,url,emailReceiver).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
					
					
				}else{
					toastr.error(response.data.message);
					
				}
				loadRecieverMailList();
				});
			
		}
		function addNewReciever(){
			console.log("hoi")
			$scope.addNewRecieverTab=true
			loadBranch();
		}
		function cancle(){
			$scope.addNewTab=false
			$scope.addNewRecieverTab=false
		}
		function changeStatuts(emailSender){
			
			var msg=""
				 var url =emailUrl+"/changeStatusEmail";
					genericFactory.add(msg,url,emailSender).then(function(response) {
						console.log("resp:"+JSON.stringify(response))
						console.log("data :"+JSON.stringify(response.data.code))
						loadSenderMailList();
						$scope.addNew=false;
						$scope.asset={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							toastr.error(response.data.message);
							
						}
						
				});
			
		}
		
		
		function save(email){
		
			console.log("email:"+JSON.stringify(email))
			if(email==undefined||email.host==""||email.host==undefined){
				$scope.hostErr=true
				return;
			}else{
				$scope.hostErr=false
			}
			
			if(email.port==""||email.port==undefined){
				$scope.portErr=true
				return;
			}else{
				$scope.portErr=false
			}
			if(email.userName==""||email.userName==undefined){
				$scope.userNameErr=true
				return;
			}else{
				$scope.userNameErr=false
			}
			if(email.fromMail==""||email.fromMail==undefined){
				$scope.fromMailErr=true
				return;
			}else{
				$scope.fromMailErr=false
			}
			
			if(email.password==""||email.password==undefined){
				$scope.passwordErr=true
				return;
			}else{
				$scope.passwordErr=false
			}
			
			var msg=""
				 var url =emailUrl+"/addEmailDetial";
					genericFactory.add(msg,url,email).then(function(response) {
						
						console.log("data :"+JSON.stringify(response.data.code))
						loadSenderMailList();
						$scope.addNewTab=false;
						vm.email={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							toastr.error(response.data.message);
							
						}
						
				});
		}
		function changeStatutsemailReceiver(email){
			
			var msg=""
				 var url =emailUrl+"/changeStatusEmailReceiver";
					genericFactory.add(msg,url,email).then(function(response) {
						
						console.log("data :"+JSON.stringify(response.data.code))
						loadRecieverMailList();
						$scope.addNewTab=false;
						vm.email={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							toastr.error(response.data.message);
							
						}
						
				});
			
		}
		function loadSenderMailList(){
			var msg=""
				 var url =emailUrl+"/getAllEmailSenderDetials";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.emailSenders = response.data;
				console.log("emailSenders: "+JSON.stringify(vm.emailSenders))
								
			});
		}
		
		function loadRecieverMailList(){
			var msg=""
				 var url =emailUrl+"/getAllEmailReceiver/"+$scope.type;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.emailReceivers = response.data;
				console.log("emailReceivers: "+JSON.stringify(vm.emailReceivers))
								
			});
		}
		
		function saveNewReciever(email){
			
			console.log("email:"+JSON.stringify(email))
			if(email==undefined||email.branch==undefined){
				$scope.branchErr=true
				return;
			}else{
				$scope.branchErr=false
			}
			if(email.name==""||email.name==undefined){
				$scope.nameErr=true
				return;
			}else{
				$scope.nameErr=false
			}
			
		
			if(email.emailId==""||email.emailId==undefined){
				$scope.emailIdErr=true
				return;
			}else{
				$scope.emailIdErr=false
			}
			
			
			
			var msg=""
				 var url =emailUrl+"/addEmailReceiver";
					genericFactory.add(msg,url,email).then(function(response) {
						
						console.log("data :"+JSON.stringify(response.data.code))
						loadRecieverMailList();
						$scope.addNewRecieverTab=false;
						vm.email={}
						if(response.data.code==200){
							toastr.success(response.data.message);
							
							
						}else{
							toastr.error(response.data.message);
							
						}
						
				});
		}
		
		
	}

	
})();
