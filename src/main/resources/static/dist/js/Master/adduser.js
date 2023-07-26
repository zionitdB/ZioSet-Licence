 var app = angular.module('myApp', ['toastr']);
app.controller('myCtrl', function($scope,toastr) {
    $scope.showMe = false;
      $scope.loderview=false;
    $scope.add = function() {
        $scope.showMe = true;
        
        
    }
    
     $scope.clicknav=function(){
        $scope.loderview=true; 
    }
   
    $scope.save=function(adduser){
        	console.log("adduser "+JSON.stringify(adduser))
       
        
        if(adduser==undefined||adduser.firstname==undefined){
				$scope.firstnameError=true;
				return;
			}else{
				$scope.firstnameError=false;
			}
        
        if(adduser.lastname==undefined){
				$scope.lastnameError=true;
				return;
			}else{
				$scope.lastnameError=false;
			}
        
        if(adduser.department==undefined){
				$scope.departmentError=true;
				return;
			}else{
				$scope.departmentError=false;
			}
        
        if(adduser.designation==undefined){
				$scope.designationError=true;
				return;
			}else{
				$scope.designationError=false;
			}
        if(adduser.username==undefined){
				$scope.usernameError=true;
				return;
			}else{
				$scope.usernameError=false;
			}
        
        if(adduser.passowrd==undefined){
				$scope.passowrdError=true;
				return;
			}else{
				$scope.passowrdError=false;
			}
       
        
        if(adduser.addrole==undefined){
				$scope.addroleError=true;
				return;
			}else{
				$scope. addroleError=false;
			}
        
         $scope.adduser={}
        
         $scope.loderview=true;
     
         
         setTimeout(()=>{                           
         $scope.loderview = false;
         }, 500); 
        
         toastr.success('Submitted successfully!!')
        return;
         
    }
/*
    $scope.passwordEnter=function (password){
		
			if(password.length >= 8){
				$scope.adduser=false
			}else{
				$scope.adduser=true
			}
			
		}  */
    
    $scope.cancel=function(){
        $scope.showMe= false;
    }
    
    
});

    
        
    