
    
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
    
 

   
    $scope.save=function(spares){
        	console.log("spares "+JSON.stringify(spares))
       
        
        if(spares==undefined||spares.partname==undefined){
				$scope.partnameError=true;
				return;
			}else{
				$scope.partnameError=false;
			}
        
        if(spares.stockcodedeatils==undefined){
				$scope.stockcodedeatilsError=true;
				return;
			}else{
				$scope.stockcodedeatilsError=false;
			}
        
        if(spares.stoackcode==undefined){
				$scope.stoackcodeError=true;
				return;
			}else{
				$scope.stoackcodeError=false;
			}
        
        if(spares.location==undefined){
				$scope.locationError=true;
				return;
			}else{
				$scope.locationError=false;
			}
        if(spares.Cost==undefined){
				$scope.CostError=true;
				return;
			}else{
				$scope.CostError=false;
			}
        
        if(spares.status==undefined){
				$scope.statusError=true;
				return;
			}else{
				$scope.statusError=false;
			}
        
         $scope.spares={}
        
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

    
        
    