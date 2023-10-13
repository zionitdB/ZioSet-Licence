(function() {
	'use strict';

	angular.module('myApp.workerLicences').controller('WorkerLicencesController', WorkerLicencesController);

	WorkerLicencesController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function WorkerLicencesController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetLifeUrl = ApiEndpoint.url+"assetLife";
		var workerLiceneceUrl = ApiEndpoint.url+"workerLicenece";
	
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:0,
			pageno:1,
			serachText:"",
			add:add,
			cancel:cancel,
			limit:1000,
			create:create
			
		});

		(function activate() {
			$scope.today=new Date();
			$scope.addTab=false;
			getLoadWorkerLicences()
		})();
		
		
		
		function create(){
			var msg="";
			var url=workerLiceneceUrl+"/createLicences?licencceNO="+vm.noOfLicence
			genericFactory.getAll(msg,url).then(function(response) {
				
				vm.responceObj= response.data;
				console.log("responceObj: "+JSON.stringify(vm.responceObj))
							$scope.addTab=false

				getLoadWorkerLicences();
								
			});
		}
		function add(){
			$scope.addTab=true
			vm.noOfLicence=0
		}
		function cancel(){
			$scope.addTab=false
		}
		
		$scope.checkLimitOdlicece=function(){
			var msg="";
			var urlCount=workerLiceneceUrl+"/getWorkerLicencesCount"

			genericFactory.getAll(msg,urlCount).then(function(response) {
				
				vm.total_count= response.data;
				
				console.log("total_count: "+JSON.stringify(vm.total_count))
				var count=vm.total_count+vm.noOfLicence;
				console.log("noOfLicence: "+JSON.stringify(vm.noOfLicence))
				console.log("count: "+count)

					if(count>=vm.limit){
						$scope.limitOverceed=true
						console.log("limitOverceed: ")

					}else{
						console.log("limit in  ")
					}		
			});
		}
		
		$scope.searchByPagination=function (search){
			getLoadWorkerLicences();
			
		}	// current page
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			getLoadWorkerLicences();
			
		}
		
		function getLoadWorkerLicences(){
			//$rootScope.loader=true;
			
			var url=""
				var urlCount=""
					var msg=""
					if(vm.serachText==""||vm.serachText==undefined){
						url=workerLiceneceUrl+"/getWorkerLicencesByLimit/"+vm.pageno+"/"+vm.perPage;
						urlCount=workerLiceneceUrl+"/getWorkerLicencesCount"
					}else{
						url=workerLiceneceUrl+"/getWorkerLicencesByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage
						urlCount=workerLiceneceUrl+"/getWorkerLicencesCountAndSearch?searchText="+vm.serachText;
					}
					genericFactory.getAll(msg,url).then(function(response) {
						vm.workerLiceneces = response.data;
						
						console.log("workerLiceneces: "+JSON.stringify(vm.workerLiceneces))
										
					});
					
					genericFactory.getAll(msg,urlCount).then(function(response) {
						
						vm.total_count= response.data;
						console.log("total_count: "+JSON.stringify(vm.total_count))
										
					});
			
		}
		
	}

	
})();
