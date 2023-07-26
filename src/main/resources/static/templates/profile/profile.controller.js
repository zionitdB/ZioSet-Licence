(function() {
	'use strict';

	angular.module('myApp.profile')
	.controller('ProfileController', ProfileController);
	ProfileController.$inject = [ '$state','$compile','$uibModal',
		'$log', '$scope', 'toastr', 'localStorageService', '$timeout','ApiEndpoint','genericFactory','$rootScope','$window','$filter','$http','loginFactory'];

	
	/* @ngInject */
	function ProfileController($state, $compile,$uibModal, $log,$scope, toastr, localStorageService, $timeout, ApiEndpoint , genericFactory,$rootScope,$window,$filter,$http,loginFactory) {

		var userUrl = ApiEndpoint.url+"user";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			user:userDetail,
			edit:edit,
			save:save
		});

		(function activate() {
			$scope.viewPasstab=false
			$scope.ediTable=true
								//toastr.success("hiiiii");

			console.log("User Detials "+JSON.stringify(userDetail))
		})();
		$scope.viewPass=function (){
			if($scope.viewPasstab){
				$scope.viewPasstab=false
			}else{
				$scope.viewPasstab=true
			}
		}
		function edit(){
			$scope.ediTable=false
			
			
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
		var regExp = /[a-zA-Z]/g;
		function save(){


			if(vm.user.firstName==undefined||vm.user.firstName==""){
				$scope.firstNameErr=true
				return;
			}else{
				$scope.firstNameErr=false	
				
			}
			if(vm.user.lastName==""||vm.user.lastName==undefined){
				$scope.lastNameErr=true
				return;
			}else{
				$scope.lastNameErr=false	
			}
			if(vm.user.username==""||vm.user.username==undefined){
				$scope.usernameErr=true
				return;
			}else{
				$scope.usernameErr=false	
			}
			if(vm.user.password==""||vm.user.password==undefined){
				$scope.passwordErr=true
				return;
			}else{
				$scope.passwordErr=false	
			}
			/*if(vm.user.email==""||vm.user.email==undefined){
				$scope.emailErr=true
				return;
			}else{
				$scope.emailErr=false	
			}*/
			if(vm.user.password.length<8){
				$scope.passwordLengthErr=true;
				return;
			}else{
				$scope.passwordLengthErr=false;
				console.log("stringContainsNumber "+stringContainsNumber(vm.user.password))
				if(stringContainsNumber(vm.user.password)){
					$scope.passwordnumberErr=false;
				}else{
					$scope.passwordnumberErr=true;
					return;
				}
				

				if(regExp.test(vm.user.password)){
					$scope.passwordcharErr=false;
					console.log("Valid Cha ")
				
				} else {
					$scope.passwordcharErr=true;
					console.log("InValid Cha ")
					
					return;
				}
				console.log("char "+regExp.test(vm.user.password))
			}
			
			
			var msg=""
				 var url =userUrl+"/updateUser";
				genericFactory.add(msg,url,vm.user).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
					 doLogout ()
					localStorageService.set(ApiEndpoint.userKey, vm.user);

				}else{
					toastr.error(response.data.message);

				}
			});

		}
		function doLogout (){
			loginFactory.ClearCredentials();
			$state.go('login');
			localStorageService.remove(ApiEndpoint.userKey);
		}
		
		
	}
})();
