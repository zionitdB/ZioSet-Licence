(function() {
	'use strict';

	angular.module('myApp.branch').controller('BranchController', BranchController);

	BranchController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function BranchController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var assetRegistationUrl = ApiEndpoint.url+"assetRegistation";
		var commonUrl = ApiEndpoint.url+"common";
		
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addNew:addNew,
			save:save,
			cancle:cancle,
			edit:edit
		});

		(function activate() {
		
			loadBranches();
			
		})();
		function addNew(){
			$scope.branchTab=true;
		}
		function cancle(){
			$scope.branchTab=false;
		}
		function edit(branch){
			$scope.branchTab=true;
			vm.branch=branch
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
		function loadBranches(){
			var msg="";
			var 	url=commonUrl+"/getAllBranches";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
				console.log("branches: "+JSON.stringify(vm.branches))
								
			});
							
		}
		
	
		
		
	
		
	}

	
})();
