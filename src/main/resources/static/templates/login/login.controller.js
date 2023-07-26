(function() {
	'use strict';

	angular
		.module('myApp.login')
		.controller('loginController', loginController);

		loginController.$inject = ['$rootScope','$stateParams','$scope','$state','localStorageService','toastr', 'ApiEndpoint','loginFactory','$location','$window','genericFactory','toaster'];

	/* @ngInject */
	function loginController($rootScope, $stateParams, $scope, $state, localStorageService,toastr, ApiEndpoint, loginFactory,$location,$window,genericFactory,toaster) {
		var userUrl = ApiEndpoint.url+"user";
		var vm = angular.extend(this, {
			doLogin : doLogin,
			showPass:showPass,
			goToforgetPassword:goToforgetPassword,
			goTologin:goTologin,
			checkUserName:checkUserName,
			forget:forget
			
		});

		(function activate() {
			vm.passView=true
		})();

		// ******************************************************
		function goToforgetPassword(){
			$scope.forgetPasswordTab=true
		}
		function goTologin(){
			$scope.forgetPasswordTab=false
		}
		function showPass(){
			if(vm.passView){
				vm.passView=false
			}else{
				vm.passView=true
			}
		}
		function doLogin(user){

			var msg=""								
			var  url =userUrl+"/login"
			console.log("URL :: "+url)
			console.log("User: "+JSON.stringify(user))
			genericFactory.add(msg,url,user).then(function(response) {
				vm.user = response.data;
			//	toastr.success("hiii");
				if (vm.user.code==200) {
					//toastr.success(vm.user.massage);
				//	return;
					 
					 loginFactory.SetCredentials(user);
					$location.path('/main/home');
					toastr.success("Login successfully");
					localStorageService.set(ApiEndpoint.userKey, response.data.data);
					$window.location.reload();
					//$state.go('main.home');
				} else {
					toastr.error(vm.user.massage);
				}
				
			});
		}
		
		function checkUserName(userName){
			var msg=""
			 var url =userUrl+"/checkUserName?userName="+userName;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.userRes = response.data;
				console.log("userRes: "+JSON.stringify(vm.userRes))
								
			});
		}
		function forget(user){
			if(vm.userRes.code==500){
				return;
			}
			var msg=""
			 var url =userUrl+"/forgotPassword";
				genericFactory.add(msg,url,vm.userRes.data).then(function(response) {
				vm.forgetPassRes = response.data;
				console.log("forgetPassRes: "+JSON.stringify(vm.forgetPassRes))
						if (vm.forgetPassRes.code==200) {
					
					toastr.success(vm.forgetPassRes.message);
					$scope.forgetPasswordTab=false
					//$state.go('main.home');
				} else {
					toastr.error(vm.forgetPassRes.message);
				}
				
			});
			
		}
		/*function doLogin(login) {
			console.log(JSON.stringify(login));
			loginFactory.doLogin(login).then(function(response){
				console.log("Responce"+JSON.stringify(response))
				if (response.status=='200') {
					
					console.log("Success")
					loginFactory.SetCredentials(login);
					$location.path('/main/home');
					toastr.success('Login....', 'Succesful !!');
					localStorageService.set(ApiEndpoint.userKey, response.data);
					$window.location.reload();
				} else {
					console.log("EOOR")
					toastr.error('Username and Password Doesnt match...!!');
				}
				if(response.data.length != 0){
					$state.go('main.home');
					toastr.success('Login....', 'Succesful !!');
					localStorageService.set(ApiEndpoint.userKey, response.data);
				}else{
					toastr.error('Username and Password Doesnt match...!!');
				}
			});
		}*/
	}
})();
