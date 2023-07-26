(function() {
	'use strict';

	angular.module('myApp.endOfLifeLicence').controller('EndOfLifeLicenceController', EndOfLifeLicenceController);

	EndOfLifeLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function EndOfLifeLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var sId=parseInt($stateParams.branchId);
		console.log("State Param sId:"+JSON.stringify($stateParams.userId))
		var vm = angular.extend(this, {
			
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			allAssets:[]
			
		});

		(function activate() {
			
			loadLicence();
		
		})();
		
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','category':'Category','licenceType':'Type','licenceName':'Name', 'licenceVersion':'Version','brand':'Brand','supplier':'Supplier','licencePeriod':'Period','cost':'Cost','purDate':'Purchase Date','installDate':'Install Date','licenceKey':'Key','status':'Status'}
		
		$scope.newExcel= function(){
			$rootScope.loader=true;
				 $rootScope.loader=true;

					setTimeout(function(){
						 
						 document.getElementById('btnExport').click();
						 $rootScope.loader=false;
						 
						  $rootScope.$digest();
						},1000)
	    	  //document.getElementById('btnExport').click();
			
			}
		
		
		function loadLicence(){
			var msg=""
				
		
			 var url =licenceUrl+"/getAllEndOfLifeLicence";
				console.log("assetUrl: "+url)

				genericFactory.getAll(msg,url).then(function(response) {
				vm.allLicences = response.data;
				;		
				
				console.log("allAssets: "+JSON.stringify(vm.allAssets))
								
			});
		}
		
		
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadAssets();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAssets();
			
		}
		
		function loadAssets(){
			if(vm.perPage>=1000){
				console.log("MORE THAN 100")
				vm.perPage=100
			}
			var url=""
			var urlCount=""
			var msg=""
		
			var dataReq=""
				console.log("MORE THAN 100")
			if(sId==1){
				dataReq="Pune"
			}else{
				dataReq="Bengaluru"
			}
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=assetUrl+"/getAllAssetsByLimit/"+vm.pageno+"/"+vm.perPage+"/"+dataReq;
				urlCount=assetUrl+"/getAllAssetCount/"+dataReq
			}else{
				url=assetUrl+"/getAllAssetsByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage+"&dataReq="+dataReq;
				urlCount=assetUrl+"/getAllAssetCountAndSearch?searchText="+vm.serachText+"&dataReq="+dataReq
			}
			
			
			console.log("urlCount :: "+urlCount)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.assets = response.data;
				
				console.log("assets: "+JSON.stringify(vm.assets))
								
			});
			
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("assetCount: "+JSON.stringify(vm.assetCount))
								
			});
			
			
			
			
		}
		
		
	
		
	}

	
})();
