(function() {
	'use strict';

	angular.module('myApp.licenceRetirement').controller('LicenceRetirementController', LicenceRetirementController);

	LicenceRetirementController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','$http','$filter'];
	
	/* @ngInject */
	function LicenceRetirementController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,$http,$filter) {
		var publisherUrl = ApiEndpoint.url+"portal";
		var productUrl = ApiEndpoint.url+"portal";
		var releaseUrl = ApiEndpoint.url+"portal";
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
			publishers:[],
		
		});

		(function activate() {
		
			loadPublishers();
			loadAvailableLicencExpiry();
		
			console.log("000000000000000000000000000000000000")
		})();
		
		function loadAvailableLicencExpiry(){
			var url="";
			var urlCount=""
				var msg=""
			if(vm.serachText==""||vm.serachText==undefined){
				url=licenceUrl+"/getAvailanleLicenceExpiryPagination/"+vm.pageno+"/"+vm.perPage;
				urlCount=licenceUrl+"/getAllCountAvailanleLicenceExpiry"
			}else{
				url=licenceUrl+"/getAvailanleLicenceExpirySearchPagination?page_no="+vm.pageno+'&perPage='+vm.perPage+"&searchText="+searchText;
				urlCount=licenceUrl+"/getSearchCountAvailanleLicenceExpiry&searchText="+vm.serachText;
			}
			
			
			
			console.log("url: "+url)
						console.log("urlCount: "+urlCount)


			genericFactory.getAll(msg,url).then(function(response) {
				vm.availableReleases = response.data;
//				vm.publishers[index].products=vm.products
				console.log("availableReleases: "+JSON.stringify(vm.availableReleases))
			//	console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.total_count1= response.data.data;
				console.log("Licence  Count: "+JSON.stringify(vm.total_count))
								
			});
		}
		
		
		
		
		
		
		
		
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
				url=releaseUrl+"/getReleasePortalPagination?pageNo="+vm.pageno+"&perPage="+vm.perPage+"&productId="+vm.productId;
				urlCount=releaseUrl+"/getCountReleasePortal?productId="+vm.productId;
			}else{
				url=releaseUrl+"/getReleasePortalPaginationAndSearch?pageNo="+vm.pageno+'&perPage='+vm.perPage+"&productId="+vm.productId+"&searchText="+searchText;
				urlCount=releaseUrl+"/getCountReleasePortalAndSearch?productId="+vm.productId+"&searchText="+vm.serachText
			}
			
			
			
			console.log("url: "+url)

			genericFactory.getAll(msg,url).then(function(response) {
				vm.release = response.data;
				console.log("release: "+JSON.stringify(vm.release))

//				vm.publishers[index].products=vm.products
				console.log("release: "+JSON.stringify(vm.release))
			//	console.log("publishers: "+JSON.stringify(vm.publishers))
								
			});
			genericFactory.getAll(msg,urlCount).then(function(response) {
				vm.assetCount = response.data;
				vm.total_count= response.data;
				console.log("Licence  Count: "+JSON.stringify(vm.total_count))
								
			});
		}
		function checkPublisher(publisher,index){
			console.log("PUSB "+JSON.stringify(publisher))
			if(publisher.selected){
				uncheckedOtherPublisher(index)
				var msg="";
				var 	url=productUrl+"/getProductsByPublisher?publisherId="+publisher.id;
				genericFactory.getAll(msg,url).then(function(response) {
					vm.products = response.data;
					vm.publishers[index].products=vm.products
					console.log("products "+JSON.stringify(vm.products))
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
			var 	url=publisherUrl+"/getPublishers";
			
			genericFactory.getAll(msg,url).then(function(response) {
				vm.publishers = response.data;
								
			});
		}
		
		
		
		
	
		
		
	
		
	}

	
})();
