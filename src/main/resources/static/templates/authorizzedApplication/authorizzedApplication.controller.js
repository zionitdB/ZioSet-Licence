(function() {
	'use strict';

	angular.module('myApp.authorizzedApplication').controller('AuthorizzedApplicationController', AuthorizzedApplicationController);

	AuthorizzedApplicationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function AuthorizzedApplicationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var bundleUrl = ApiEndpoint.url+"bundle";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			perPage : 10,
			total_count:100,
			pageno:1,
			serachText:"",
			loadAuthorizesApplications:loadAuthorizesApplications
		
		});
		
		(function activate() {
		
			loadAuthorizesApplications();
			
		})();
		$scope.searchByPagination=function (search){
			loadAuthorizesApplications();
			
		}
		// current page
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadAuthorizesApplications();
			
		}
		
		
	
		function save(branch){
			console.log("branch: "+JSON.stringify(branch))
			if(branch==undefined||branch.branchName==""){
				$scope.branchNameErr=true
				return;
			}else{
				$scope.branchNameErr=false
			}
			
			var msg="";
			var 	url=commonUrl+"/saveBranch";
			genericFactory.add(msg,url,branch).then(function(response) {
				vm.types = response.data;
				$scope.branchTab=false;
				loadBranches();
				console.log("types: "+JSON.stringify(vm.types))
								
			});

			
		}
		function loadAuthorizesApplications(){
			var msg="";
			/*var 	url=bundleUrl+"/getAuthorizedApplication";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.autorizedApplications = response.data;
				console.log("autorizedApplications: "+JSON.stringify(vm.autorizedApplications))
								
			});*/
			
			var url=""
				var urlCount=""
					var msg=""
					if(vm.serachText==""||vm.serachText==undefined){
						url=bundleUrl+"/getAuthorizedApplicationPagination/"+vm.pageno+"/"+vm.perPage;
						urlCount=bundleUrl+"/getAuthorizedApplicationCount"
					}else{
						url=bundleUrl+"/getAuthorizedApplicationSearchPagination?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage
						urlCount=bundleUrl+"/getSearchCountProduct?searchText="+vm.serachText;
					}
					genericFactory.getAll(msg,url).then(function(response) {
						vm.autorizedApplications = response.data;
						
						console.log("autorizedApplications: "+JSON.stringify(vm.autorizedApplications))
										
					});
					
					genericFactory.getAll(msg,urlCount).then(function(response) {
						
						vm.total_count= response.data;
						console.log("total_count: "+JSON.stringify(vm.total_count))
										
					});
							
		}
		$scope.selectQR = function (index) {
			vm.autorizedApplications[index].check = !vm.autorizedApplications[index].check;

			
			var msg="";
			var 	url=bundleUrl+"/addAuthorizedApplication";
			genericFactory.add(msg,url,vm.autorizedApplications[index]).then(function(response) {
				loadAuthorizesApplications()
								
			});
			
		}
	
		
		
	
		
	}

	
})();
