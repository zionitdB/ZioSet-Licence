(function() {
	'use strict';

	angular.module('myApp.licenceExpiry').controller('LicenceExpiryController', LicenceExpiryController);

	LicenceExpiryController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceExpiryController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var publisherUrl = ApiEndpoint.serverUrl+"publisher";
		var productUrl = ApiEndpoint.serverUrl+"product";
		var releaseUrl = ApiEndpoint.serverUrl+"release";
		var licenceUrl = ApiEndpoint.url+"licence";
	
		var userDetails = localStorageService.get(ApiEndpoint.userKey);
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			checkPublisher:checkPublisher,
			checkProduct:checkProduct,
			addRelease:addRelease,
			perPage : 10,
			total_count:100,
			pageno:1,
		
		});

		(function activate() {
		
			loadPublishers();
		
			
		})();
		
		$scope.searchByPagination=function (search){
			loadRelease();
			
		}
		
		$scope.pagination = {
		        current: 1
		    };
		
		// page changed 
		$scope.pageChanged = function(pageNo){
			vm.pageno=pageNo;
			loadRelease();
			
		}
		function addRelease(release){
			console.log("release  : "+JSON.stringify(release))

			var releaseObj={}
			releaseObj.productName=release.product.productName
			releaseObj.publisherName=release.product.publisher.publisherName
			releaseObj.releaseName=release.releaseName
			releaseObj.releaseDate=release.releaseDate
			releaseObj.retirementDate=release.retirementDate
			releaseObj.premiumSupportEndDate=release.premiumSupportEndDate
			releaseObj.extendedSupportEndDate=release.extendedSupportEndDate
			releaseObj.edition=release.edition
			console.log("releaseObj  : "+JSON.stringify(releaseObj))

			var msg="";
			//var 	url=licenceUrl+"/addLicenceExpriry";
			 var url =licenceUrl+"/addLicenceExpriry";
			 console.log("url: "+JSON.stringify(url))
				genericFactory.add(msg,url,releaseObj).then(function(response) {
				vm.releaseRes = response.data;
//				vm.publishers[index].products=vm.products
				if(vm.releaseRes.code==200){
					toastr.success(vm.releaseRes.message);
				}else{
					toastr.error(vm.releaseRes.message);
				}
				console.log("releaseRes: "+JSON.stringify(vm.releaseRes))
			//	console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
		}
		function checkProduct(product,parent,index){
			console.log("products: "+JSON.stringify(product))
		
			if(product.selected){
				uncheckedOtherProduct(parent,index)
				vm.productId=product.id
				loadRelease();
				/*var msg="";
				var 	url=releaseUrl+"/getReleaseListByProductId?productId="+product.id;
				
				genericFactory.getAll(msg,url).then(function(response) {
					vm.release = response.data.data;
//					vm.publishers[index].products=vm.products
					console.log("release: "+JSON.stringify(vm.release))
				//	console.log("publishers: "+JSON.stringify(vm.publishers))
									
				});*/
				
			}
		}
		function loadRelease(){
			var msg="";
			//var 	url=releaseUrl+"/getReleaseListByProductId?productId="+vm.productId;
			var url="";
			var urlCount=""
			if(vm.serachText==""||vm.serachText==undefined){
				url=releaseUrl+"/getReleaseListByProductIdPagination?pageNo="+vm.pageno+"&pageSize="+vm.perPage+"&productId="+vm.productId;
				urlCount=releaseUrl+"/getReleaseCountByProductId?productId="+vm.productId;
			}else{
				url=releaseUrl+"/getReleaseListByProductIdAndSearchPagination?pageNo="+vm.pageno+'&pageSize='+vm.perPage+"&productId="+vm.productId+"&searchText="+searchText;
				urlCount=releaseUrl+"/getReleaseCountByProductIdAndSearch?productId="+vm.productId+"&searchText="+vm.serachText
			}
			
			
			
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.release = response.data.data;
//				vm.publishers[index].products=vm.products
				console.log("release: "+JSON.stringify(vm.release))
			//	console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data.data;
				console.log("Licence  Count: "+JSON.stringify(vm.total_count))
								
			});
		}
		function checkPublisher(publisher,index){
			
			if(publisher.selected){
				uncheckedOtherPublisher(index)
				var msg="";
				var 	url=productUrl+"/getProductListByPublisher?publisherId="+publisher.id;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.products = response.data.data;
					vm.publishers[index].products=vm.products
				/*	console.log("products: "+JSON.stringify(vm.products))
					console.log("publishers: "+JSON.stringify(vm.publishers))*/
									
				});
			}
			

		}
		function uncheckedOtherPublisher(index){
			
			var i=0;
			angular.forEach(vm.publishers, function (publisher) {
				//console.log("i  : "+i)
				
				if(i!=index){
					publisher.selected=false;
					publisher.products=[]
				}
				i++;
            });
			
		}
function uncheckedOtherProduct(parent,index){
	console.log("parent: "+parent)
	console.log("index: "+index)
		//console.log("PUb Sel: "+JSON.stringify(vm.publishers[parent]))
			var i=0;
			angular.forEach(vm.publishers[parent].products, function (product) {
				//console.log("i  : "+i)
				//console.log("product Sel: "+JSON.stringify(vm.publishers[parent]))
				if(i!=index){
					product.selected=false;
					
				}
				i++;
            });
			
		}
		function loadPublishers(){
			var msg="";
			var 	url=publisherUrl+"/getAllPublisherList";
			genericFactory.getAll(msg,url).then(function(response) {
				vm.publishers = response.data.data;
				console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
		}
		
		
		
		
	
		
		
	
		
	}

	
})();
