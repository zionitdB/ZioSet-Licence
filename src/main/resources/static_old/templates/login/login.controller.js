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
			forget:forget,
			verify:verify,
			resendOTP:resendOTP
			
		});

		(function activate() {
			vm.passView=true
		})();

		// ******************************************************
		function resendOTP(){
			console.log("user : "+JSON.stringify($scope.user))
			if($scope.user==undefined||$scope.user.username==""){
				toastr.error("Enter Username");
				return;
			}
			var obj={}
			obj.username=$scope.user.username
			obj.otp=$scope.user.otp
			
			var msg=""
				 var url =userUrl+"/resendOTP";
					genericFactory.add(msg,url,obj).then(function(response) {
					vm.optResendRes = response.data;
					if (vm.optResendRes.code==200) {
						$scope.user.otpVerified=true;
						$scope.veficationMessage=vm.optResendRes.message
						vm.optVerificationRes.code=200
						$scope.showResend=false
					}
					console.log("optVerificationRes: "+JSON.stringify(vm.optVerificationRes))

				});
			
		}
		function verify(){
			console.log("User: "+JSON.stringify($scope.user))

			if($scope.user==undefined||$scope.user.username==""){
				toastr.error("Enter Username");
				return;
			}
			
			if($scope.user.otp==""||$scope.user.otp==undefined){
				toastr.error("Enter OTP");
				return;
			}
			
			
			var obj={}
			obj.username=$scope.user.username
			obj.otp=$scope.user.otp
			
			var msg=""
				 var url =userUrl+"/verifyOPT";
					genericFactory.add(msg,url,obj).then(function(response) {
					vm.optVerificationRes = response.data;
					console.log("optVerificationRes: "+JSON.stringify(vm.optVerificationRes))
					if (vm.optVerificationRes.code==200) {
						$scope.user.otpVerified=true;
						$scope.veficationMessage=vm.optVerificationRes.message
					} 
					if (vm.optVerificationRes.code==600) {
						$scope.showResend=true
						//$scope.veficationMessage=vm.optVerificationRes.message
					} 
					$scope.veficationMessage=vm.optVerificationRes.message
					
					/*else {
						
					}*/
					
				});
		}
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
			var captchaResponse = $window.grecaptcha.getResponse();
			if (captchaResponse.length === 0) {
				toastr.error('Please complete the reCAPTCHA.');
				return;
			}
			var msg=""								
			var  url =userUrl+"/login"
			console.log("URL :: "+url)
			console.log("User: "+JSON.stringify(user))
			genericFactory.add(msg,url,user).then(function(response) {
				vm.userRes = response.data;
				console.log("userRes: "+JSON.stringify(vm.userRes))

			//	toastr.success("hiii");
				if (vm.userRes.code==200) {
					//toastr.success(vm.user.massage);
				//	return;
					 
					 loginFactory.SetCredentials(user);
					$location.path('/main/home');
					toastr.success("Login successfully");
					localStorageService.set(ApiEndpoint.userKey, response.data.data);
					$window.location.reload();
					//$state.go('main.home');
				} else {
					toastr.error(vm.userRes.massage);
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
