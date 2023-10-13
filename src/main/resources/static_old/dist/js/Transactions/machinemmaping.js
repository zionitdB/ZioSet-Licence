var app = angular.module('myApp', ['toastr']);
app.controller('myCtrl', function($scope,toastr) {
    
    
    $scope.save=function(location){
        	console.log("location "+JSON.stringify(location))
           
        
       if(location==undefined||location.locationName==undefined){
				$scope.locationNameError=true;
				return;
			}else{
				$scope.locationNameError=false;
			}
        
        if(location.locationCode==undefined){
				$scope.locationCodeError=true;
				return;
			}else{
				$scope.locationCodeError=false;
			}
        $scope.location={}
        
 $scope.loderview=true;
     
         
         setTimeout(()=>{                           
         $scope.loderview = false;
         }, 500); 
        

         toastr.success('Submitted successfully!!')
        return;
    }
    
    
   
});

    
        
    