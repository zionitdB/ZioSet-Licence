(function() {
	'use strict';

	angular.module('myApp.bundleAppication').controller('BundleAppicationController', BundleAppicationController);

	BundleAppicationController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope'];
	
	/* @ngInject */
	function BundleAppicationController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope) {
		var bundleUrl = ApiEndpoint.url+"bundle";
		var commonUrl = ApiEndpoint.url+"common";
		var licenceUrl = ApiEndpoint.url+"licence";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add:add,
			save:save,
			cancle:cancle,
			
			edit:edit,
			
			delet:delet,
			perPage : 10,
			total_count:0,
			pageno:1,
			serachText:"",
			addNew:addNew,
			applications:[],
			applicationsNames:[],
			remove:remove
			
		});

		(function activate() {
		
			loadBundles();
		
		})();
		
		
		function getAllApplicationName(){
			var msg=""
				 var url =licenceUrl+"/getProducts";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.licencesNames = response.data;
				console.log("licencesName: "+JSON.stringify(vm.licencesNames))
								
			});
		}
		
	function addNew (){
		getAllApplicationName()
			var application={};
			application.applicationName="";
			application.version="";
			application.exprityDate=""
				//application.nameErrr=false
				vm.applications.push(application);
		}
		function remove(application){
			vm.applications.splice(application,1);
		}
		function edit(bundle){
			vm.bundle=bundle
			vm.bundle.createdDate=new Date(bundle.createdDate)
			console.log("bundles "+JSON.stringify(bundle))
			getApplicationByBundles(bundle);
			getAllApplicationName()
			$scope.addNew=true;
		}
		
		function getApplicationByBundles(bundle){
			var msg=""
				 var url =bundleUrl+"/getApplicationByBundleId?bundleId="+bundle.bundleId;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.applications = response.data;
				angular.forEach(vm.applications, function(application) {
					application.exprityDate=new Date(application.exprityDate)
					});
								
			});
		}
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadBundles();
			
		}
		$scope.searchByPagination=function (search){
			loadBundles();
			
		}
		
		
		function loadBundles() {
		     var msg=""
		    	 var url=""
		    		 var urlCount=""
		    	 if(vm.serachText==""||vm.serachText==undefined){
						url=bundleUrl+"/getBundlePagination/"+vm.pageno+"/"+vm.perPage;
						urlCount=bundleUrl+"/getBundleTotalCount";
					}else{
						url=bundleUrl+"/getBundleByLimitAndSearch?searchText="+vm.serachText+"&pageNo="+vm.pageno+'&perPage='+vm.perPage;
						urlCount=bundleUrl+"/getBundleCountbySearch?searchText="+vm.serachText;
					}
					
					
		/*	 var url =zonedeviceUrl+"/getAllDevice";*/
				genericFactory.getAll(msg,url).then(function(response) {
						vm.bundles = response.data;
						console.log("bundles: "+JSON.stringify(vm.bundles))
				});
			
				genericFactory.getAll(msg,urlCount).then(function(response) {
					vm.total_count= response.data;
									
				});
			
	}
		
		function add(){
			$scope.addNew=true;
			vm.bundle.createdDate=new Date(bundle.createdDate)
			
		}

		function cancle(){
			
			$scope.addNew=false;
		
		}
	
		function delet(device){
			var msg=""
				 var url =bundleUrl+"/deletBundle";
				genericFactory.add(msg,url,device).then(function(response) {
				
				console.log("response: "+JSON.stringify(response))
				if(response.data.code==200){
					toastr.success(response.data.message);
				}else{
					toastr.error(response.data.message);

				}
				loadBundles();
				
			});
		}
		function save(){
			if(vm.bundle==undefined||vm.bundle.bundleName==""){
				$scope.bundleNameErr=true
				return;
			}else{
				$scope.bundleNameErr=false	
				
			}
			if(vm.bundle.createdDate==undefined||vm.bundle.createdDate==""){
				$scope.createdDateErr=true
				return;
			}else{
				$scope.createdDateErr=false	
				
			}
			if(vm.applications.length==0){
				$scope.applicatioErr=true
			}else{
				$scope.applicatioErr=false
				var i=0;
				vm.applicationsNames=[]
				angular.forEach(vm.applications, function(application) {
					  console.log("Name "+ application.applicationName);
					  if(application.applicationName==""){
						  console.log("No Name  Found ");
						  var srNo=i+1;
						var msg="No Application Name Found For Sr No "+srNo
						vm.applicationsNames.push(msg)

					  }
					  i++;
					});
				
				
			}
			console.log("applicationsNames: "+JSON.stringify(vm.applicationsNames.length))

			if(vm.applicationsNames.length!=0){
				return;
			}else{

				vm.bundle.applications=vm.applications;
				
				console.log("bundle: "+JSON.stringify(vm.bundle))

				var msg=""
					 var url =bundleUrl+"/addBundle";
					genericFactory.add(msg,url,vm.bundle).then(function(response) {
						console.log("response: "+JSON.stringify(response))

					vm.device={}
					$scope.addNew=false;
					if(response.data.code==200){
						toastr.success(response.data.message);
						$scope.addNew=false;

					}else{
						toastr.error(response.data.message);

					}
					loadBundles();
				});
			}

		
			
			
		}
			
		
	}

	
})();
