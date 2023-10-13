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
    
 
    
    
    $scope.save=function(uploaddrawing){
        	console.log("uploaddrawing"+JSON.stringify(uploaddrawing))
           if(uploaddrawing==undefined||uploaddrawing.drawingtype==undefined){
				$scope.drawingtypeError=true;
				return;
			}else{
				$scope.drawingtypeError=false;
			}
        
        if(uploaddrawing.name==undefined){
				$scope.nameError=true;
				return;
			}else{
				$scope.nameError=false;
			}
        
        if(uploaddrawing.file==undefined){
				$scope.fileError=true;
				return;
			}else{
				$scope.fileError=false;
			}
        $scope.uploaddrawing={}
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

    
        
    