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
    
 


        
     $scope.save=function(setupchart){
        	console.log("setupchart "+JSON.stringify(setupchart))
           
        
       if(setupchart==undefined||location.setupchartname==undefined){
				$scope.setupchartnameError=true;
				return;
			}else{
				$scope.setupchartnameError=false;
			}
        
        if(setupchart.file==undefined){
				$scope.fileError=true;
				return;
			}else{
				$scope.fileError=false;
			}
        $scope.setupchart={}
        
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

    
        
    