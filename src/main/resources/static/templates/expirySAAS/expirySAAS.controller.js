(function() {
	'use strict';

	angular.module('myApp.expirySAAS').controller('ExpirySAASController', ExpirySAASController);

	ExpirySAASController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function ExpirySAASController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var licenceUrl = ApiEndpoint.url+"licence";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
		});

		(function activate() {
			getExpiringLicencceSAAS();
		})();
		
		$scope.searchByPagination=function (search){
			getExpiringLicencceSAAS();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			getExpiringLicencceSAAS();
			
		}
		
		
		
		
		$scope.file="Employees"
			vm.labels={'srNo':'Sr No','associate.associateName': 'Publisher', 'product.productName': 'Product','productVersion': 'Version','computerName': 'Computer Name','asset.serialNo':'Serial No','insDate':'Install Date','expiryDate':'Expiry Date'}
		
		$scope.newExcel= function(){
			 $rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				 $rootScope.loader=false;
				 
				  $rootScope.$digest();
				},1000);					
			
		}
		function getExpiringLicencceSAAS(){
			var msg=""
			var url=""
				var urlCount=""
			
			
			if(vm.serachText==""||vm.serachText==undefined){
				url=licenceUrl+"/getExpiringLicencesSAASPagination/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getAllCountExpiringLicencesSAAS";
			}else{
				url=licenceUrl+"/getExpiringLicencesSAASSearchPagination?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
				urlCount=licenceUrl+"/getSearchCountExpiringLicencesSAAS?searchText="+vm.serachText;
			}
			
			
			
			
			
				genericFactory.getAll(msg,url).then(function(response) {
				vm.expiryLiceecncesSAAS= response.data;
				console.log("expiryLiceecncesSAAS: "+JSON.stringify(vm.expiryLiceecncesSAAS))
				
								
			});
				
				genericFactory.getAll(msg,urlCount).then(function(response) {
					vm.total_count= response.data;
					console.log("total_count: "+JSON.stringify(vm.total_count))
					
									
				});
		}
	
		
	}

	
})();
