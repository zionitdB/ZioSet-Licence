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
    
 
    
    $scope.save=function(machineowner){
        	console.log("machineowner "+JSON.stringify(machineowner))
           
        
       if(machineowner==undefined||machineowner.machine==undefined){
				$scope.machineError=true;
				return;
			}else{
				$scope.machineError=false;
			}
        
        if(machineowner.user==undefined){
				$scope.userError=true;
				return;
			}else{
				$scope.userError=false;
			}
        $scope.machineowner={}
        
        $scope.loderview=true;
     
         
         setTimeout(()=>{                           
         $scope.loderview = false;
         }, 500); 
        
         toastr.success('Submitted successfully!!')
        return;
    }
    
    
    $scope.cancel=function(){
        $scope.showMe= false;
    }
    
});

    
        
    