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
    
 

    
    
    $scope.save=function(MchPerformance){
        	console.log("MchPerformance "+JSON.stringify(location))
           
        if(MchPerformance==undefined||MchPerformance.performance==undefined){
				$scope.performanceError=true;
				return;
			}else{
				$scope.performanceError=false;
			}
        
        if(MchPerformance.Machine==undefined){
				$scope.MachineError=true;
				return;
			}else{
				$scope.MachineError=false;
			}
         if(MchPerformance.file==undefined){
				$scope.fileError=true;
				return;
			}else{
				$scope.fileError=false;
			}
        $scope.MchPerformance={}
        
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

    
        
    