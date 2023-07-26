var app = angular.module('myApp', ['toastr']);
app.controller('myCtrl', function($scope,toastr) {
   
    $scope.loderview=false;
   
    $scope.clicknav=function(){
        $scope.loderview=true; 
    }
    
    
    
   
        
         setTimeout(()=>{                           
         $scope.loderview = false;
         }, 500); 
        
    
        
    
    
   
});

    
        
    