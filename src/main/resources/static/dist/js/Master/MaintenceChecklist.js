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
    
 
    
    $scope.save=function(MavhinCkeckList){
        	console.log("MavhinCkeckList "+JSON.stringify(MavhinCkeckList))
           
      
        
        
 $scope.loderview=true;
     
         
         setTimeout(()=>{                           
         $scope.loderview = false;
         }, 500); 
        
        return;
      
    }
    
   
    
    $scope.cancel=function(){
        $scope.showMe= false;
    }
    
     $scope.MavhinCkeckList = []
         
     
    
    
    $scope.addchecklist=function() {
        
        var checkpoint = {}
        checkpoint.checkpointname="", 
            checkpoint.operation="",
            checkpoint.defalutvalue=""
     
       $scope.MavhinCkeckList.push(checkpoint);
    }
        
        
    $scope.removeNewColumns = function() {
        
         $scope.MavhinCkeckList.pop(checkpoint);
        };
    
    });

    
        
    