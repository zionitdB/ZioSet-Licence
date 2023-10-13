var app = angular.module('myApp',['toastr']);
app.controller('myCtrl', function($scope,toastr) {
    $scope.showMe = false;
     $scope.loderview=false;
    $scope.add = function() {
        $scope.showMe = true;
     }
    
    $scope.clicknav=function(){
        $scope.loderview=true; 
    }
    
   
    
    $scope.save=function (machine){
          
        	console.log("machine "+JSON.stringify(machine))
          
        if(machine==undefined||machine.machineName==undefined){
				$scope.machinError=true;
				return;
			}else{
				$scope.machinError=false;
			}
        
        if(machine.assettype==undefined){
				$scope.assettypeError=true;
				return;
			}else{
				$scope.assettypeError=false;
			}
        
        if(machine.application==undefined){
				$scope.applicationErr=true;
				return;
			}else{
				$scope.applicationErr=false;
			}
        
        if(machine.purchesDate==undefined){
				$scope.dateError=true;
				return;
			}else{
				$scope.dateError=false;
			}
        if(machine.currentCeneter==undefined){
				$scope.currentErr=true;
				return;
			}else{
				$scope.currentErr=false;
			}
         if(machine.status==undefined){
				$scope.statusError=true;
				return;
			}else{
				$scope.statusError=false;
			}
       
           $scope.machine={}
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
    
   
   
   $scope.uploadmachine = function() {
    var file = $scope.myFile;
     console.log('file is ' );
       console.dir(file);
    toastr.success('Submitted successfully!!')
       return;
   }
   
     
        
      
		
});