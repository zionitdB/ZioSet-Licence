
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
    


    
    $scope.save=function(spindle){
        	console.log("spares "+JSON.stringify(spindle))
       
        
        if(spindle==undefined||spindle.SpindleName==undefined){
				$scope.SpindleNameError=true;
				return;
			}else{
				$scope.SpindleNameError=false;
			}
        
        if(spindle.SpindleType==undefined){
				$scope.SpindleTypeError=true;
				return;
			}else{
				$scope.SpindleTypeError=false;
			}
        
        if(spindle.BeltDetails==undefined){
				$scope.BeltDetailsError=true;
				return;
			}else{
				$scope.BeltDetailsError=false;
			}
        
        if(spindle.SparesInfo==undefined){
				$scope.SparesInfoError=true;
				return;
			}else{
				$scope.SparesInfoError=false;
			}
        if(spindle.status==undefined){
				$scope.statusError=true;
				return;
			}else{
				$scope.statusError=false;
			}
        
        
         $scope.spindle={}
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

    
        
    