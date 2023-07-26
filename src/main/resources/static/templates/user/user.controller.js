(function() {
	'use strict';

	angular.module('myApp.user').controller('UserController', UserController);

	UserController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope'];
	
	/* @ngInject */
	function UserController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope) {
		var userUrl = ApiEndpoint.url+"user";
		var departmentUrl = ApiEndpoint.url+"department";
		var commonUrl = ApiEndpoint.url+"common";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			user : userDetail,
			addNew:addNew,
			save:save,
			edit:edit,
			cancle:cancle,
			delet:delet,
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadAllUsers:loadAllUsers,
		
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
			$scope.viewPasstab=false
			loadAllUsers();
			loadAllUserCounts();
		
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
			loadAllUsers();
		}
		$scope.totalUser=function (){
			$scope.type="all"
				loadAllUsers();
		}
		$scope.activeUser=function (){
			$scope.type="active"
				loadAllUsers();
		}
		$scope.puneUser=function (){
			$scope.type="pune"
				loadAllUsers();
		}
		$scope.bengaluruUser=function (){
			$scope.type="bengaluru"
				loadAllUsers();
		}
		
		function loadAllUserCounts(){
			var msg=""
				 var url =userUrl+"/getAllAllUserCounts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.userCounts = response.data;
				console.log("userCounts: "+JSON.stringify(vm.userCounts))
								
			});
		}
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAllUsers();
			
		}
		$scope.searchByPagination=function (search){
			loadAllUsers();
			
		}
		
		$scope.viewPass=function (){
			if($scope.viewPasstab){
				$scope.viewPasstab=false
			}else{
				$scope.viewPasstab=true
			}
		}
		function addNew(){
			$scope.addNew=true;
			loadRoles();
			loadBranch();
		}
		function cancle(){
			$scope.addNew=false;
			
		}
		function loadBranch(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
		}
		function edit(user){
			$scope.addNew=true;
			vm.userObj=user;
			loadRoles() ;
			loadBranch();
		}
		
		
		function delet(user){
			var msg=""
				 var url =userUrl+"/deleteUser";
				genericFactory.add(msg,url,user).then(function(response) {
					loadAllUsers();
					if(response.data.code==200){
						toastr.success(response.data.message);
						
						
					}else{
						toastr.error(response.data.message);
						
					}
								
			});
		}
		$scope.checkUsername=function (userName){
			var msg=""
				 var url =userUrl+"/checkUserName?userName="+userName;
					genericFactory.getAll(msg,url).then(function(response) {
					vm.userRes = response.data;
					console.log("userRes: "+JSON.stringify(vm.userRes))
									
				});
		}
		
		//list users by limit 
		function loadAllUsers() {
			var url=""
				var urlCount=""
					var msg=""
					if(vm.serachText==""||vm.serachText==undefined){
						url=userUrl+"/getUserByLimit/"+vm.pageno+"/"+vm.perPage+"/"+$scope.type;
						urlCount=userUrl+"/getUserCount/"+$scope.type
					}else{
						url=userUrl+"/getUserByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&type="+$scope.type;
						urlCount=userUrl+"/getUserCountAndSearch?searchText="+vm.serachText+"&type="+$scope.type;
					}
					genericFactory.getAll(msg,url).then(function(response) {
						vm.users = response.data;
						
						console.log("users: "+JSON.stringify(vm.users))
										
					});
					
					genericFactory.getAll(msg,urlCount).then(function(response) {
						vm.assetCount = response.data;
						vm.total_count= response.data;
						console.log("assetCount: "+JSON.stringify(vm.assetCount))
										
					});
		}
		function loadRoles() {
			var msg = null;
	
			var url = userUrl+"/getAllRoles";
			
			console.log("DAT URL"+url)
			
		
			genericFactory.getAll(msg,url).then(function(response) {
				vm.roles = response.data;
				console.log("roles "+JSON.stringify(vm.roles))
				
			});
		}
		function stringContainsNumber(_input){
			  let string1 = String(_input);
			  for( let i = 0; i < string1.length; i++){
			      if(!isNaN(string1.charAt(i)) && !(string1.charAt(i) === " ") ){
			        return true;
			      }
			  }
			  return false;
			}
		
		
		function save(user){
			
			var regExp = /[a-zA-Z]/g;
			
			console.log("EXIT OL DUSER NAME "+JSON.stringify(user));
			if(user.role==undefined||user.role==""){
				$scope.roleErr=true;
				return;
			}else{
				$scope.roleErr=false;
			}
			if(user.role.roleId==2){
				if(user.branch==undefined){
					$scope.branchErr=true;
					return;
				}else{
					$scope.branchErr=false;
				}
			}
			
			if(user.firstName==undefined||user.firstName==""){
				$scope.fnameErr=true;
				return;
			}else{
				$scope.fnameErr=false;
			}
			
			if(user.lastName==undefined||user.lastName==""){
				$scope.lnameErr=true;
				return;
			}else{
				$scope.lnameErr=false;
			}	
			if(user.email==undefined||user.email==""){
				$scope.emailErr=true;
				return;
			}else{
				$scope.emailErr=false;
			}	
			if(user.username==undefined||user.username==""){
				$scope.usernameErr=true;
				return;
			}else{
				$scope.usernameErr=false;
			}
			if(user.password==undefined||user.password==""){
				$scope.passwordErr=true;
				return;
			}else{
				$scope.passwordErr=false;
				
				console.log("LENGH "+user.password.length)
				if(user.password.length<8){
					$scope.passwordLengthErr=true;
					return;
				}else{
					$scope.passwordLengthErr=false;
					console.log("stringContainsNumber "+stringContainsNumber(user.password))
					if(stringContainsNumber(user.password)){
						$scope.passwordnumberErr=false;
					}else{
						$scope.passwordnumberErr=true;
						return;
					}

					if(regExp.test(user.password)){
						$scope.passwordcharErr=false;
					
					} else {
						$scope.passwordcharErr=true;
						return;
					}
				}
			}
			
			var msg="";
			if(vm.userRes.code==200)
			{
				return;
			}
			
			var url =userUrl+"/updateUser";
			console.log("URL "+url);

			genericFactory.add(msg,url,user).then(function(response) {
				loadAllUsers();
				$scope.addNew=false;
				vm.userObj={};
				if(response.data.code==200){
					toastr.success(response.data.message);
					
					
				}else{
					toastr.error(response.data.message);
					
				}

			});
			
			
			
		
					
			}
				
			
		
			
			
			
			
		
		
	
		
	}

	
})();
