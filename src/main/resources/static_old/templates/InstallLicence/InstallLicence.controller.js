(function() {
	'use strict';

	angular.module('myApp.InstallLicence').controller('InstallLicenceController', InstallLicenceController);

	InstallLicenceController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter','$stateParams'];
	
	/* @ngInject */
	function InstallLicenceController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter,$stateParams) {
		
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
			
			loadInstallLicences();
		
		})();
		
		
		$scope.filename="Machines"
			vm.labels={'srNo': 'Sr No','branch.branchName': 'Location','category':'Category','licenceType':'Type','licenceName':'Name', 'licenceVersion':'Version','brand':'Brand','supplier':'Supplier','licencePeriod':'Period','cost':'Cost','purDate':'Purchase Date','licenceKey':'Key','status':'Status'}
		
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
		
		
		function loadInstallLicences(){
			var msg=""
				
		
				var url=""
					var urlCount=""
					var msg=""
			
					
					if(vm.serachText==""||vm.serachText==undefined){
						url=licenceUrl+"/getSystemLincencceByLimit/"+vm.pageno+"/"+vm.perPage;
						urlCount=licenceUrl+"/getSystemLicenceCount"
					}else{
						url=licenceUrl+"/getSystemLicenceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
						urlCount=licenceUrl+"/getSystemLicenceCountAndSearch?searchText="+vm.serachText;
					}
					
					
					console.log("urlCount:: "+urlCount)
						console.log("url :: "+url)
					genericFactory.getAll(msg,url).then(function(response) {
						vm.allLicences = response.data;
						
										
					});
					
					genericFactory.getAll(msg,urlCount).then(function(response) {
						vm.total_count= response.data;
										
							console.log("total_count: "+JSON.stringify(vm.total_count))

					});
		}
		
		
	//***********************Pagination Start*****************************//
		$scope.searchByPagination=function (search){
			loadInstallLicences();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadInstallLicences();
			
		}
		
		function loadLicence1(){
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
				url=licenceUrl+"/getSystemLincencceByLimit/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getSystemLicenceCount"
			}else{
				url=licenceUrl+"/getSystemLicenceByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=licenceUrl+"/getSystemLicenceCountAndSearch?searchText="+vm.serachText
			}
			
			
			console.log("urlCount :: "+urlCount)
				console.log("url :: "+url)
			genericFactory.getAll(msg,url).then(function(response) {
				vm.allLicences = response.data;
				
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
