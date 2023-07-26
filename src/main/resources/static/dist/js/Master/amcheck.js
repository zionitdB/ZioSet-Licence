
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
    
    
  
    $scope.save=function(amcheck){
        	console.log("amcheck "+JSON.stringify(amcheck))
       
        if(amcheck==undefined||amcheck.Machine==undefined){
				$scope.MachineError=true;
				return;
			}else{
				$scope.MachineError=false;
			}
        
        if(amcheck.Item==undefined){
				$scope.ItemError=true;
				return;
			}else{
				$scope.ItemError=false;
			}
        
        if(amcheck.satandard==undefined){
				$scope.satandardError=true;
				return;
			}else{
				$scope.satandardError=false;
			}
        
        if(amcheck.checkpointcode==undefined){
				$scope.checkpointcodeError=true;
				return;
			}else{
				$scope.checkpointcodeError=false;
			}
        
        if(amcheck.tool==undefined){
				$scope.toolError=true;
				return;
			}else{
				$scope.toolError=false;
			}
        
        
        if(amcheck.frequency==undefined){
				$scope.frequencyError=true;
				return;
			}else{
				$scope.frequencyError=false;
			}
        
         if(amcheck.manTime==undefined){
				$scope.manTimeError=true;
				return;
			}else{
				$scope.manTimeError=false;
			}
        
         if(amcheck.status==undefined){
				$scope.statusError=true;
				return;
			}else{
				$scope.statusError=false;
			}
        
          $scope.amcheck={}
       
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

    
        
    