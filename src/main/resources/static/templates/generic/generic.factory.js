(function() {
	'use strict';

	angular
		.module('myApp.generic')
		.factory('genericFactory', genericFactory).directive('toggle', function(){
			  return {
				    restrict: 'A',
				    link: function(scope, element, attrs){
				      if (attrs.toggle=="tooltip"){
				        $(element).tooltip();
				      }
				      if (attrs.toggle=="popover"){
				        $(element).popover();
				      }
				    }
				  };
				}) 
		
		
		.service('fileUpload',
				[ '$http', 'toastr', '$rootScope','$window',function($http, toastr,$rootScope,$window) {
					this.uploadFileToUrl = function(file, uploadUrl) {
						var fd = new FormData();
						fd.append('file', file);
						$http.post(uploadUrl, fd, {
							transformRequest : angular.identity,
							headers : {
								'Content-Type' : undefined
							}
						}).then(function(response) {
							$window.location.reload();
						
							
							
						});
					}
				} ]).service('toaster', ['$rootScope', function ($rootScope) {
				    this.pop = function (type, title, body, timeout, bodyOutputType, clickHandler) {
				        this.toast = {
				            type: type,
				            title: title,
				            body: body,
				            timeout: timeout,
				            bodyOutputType: bodyOutputType,
				            clickHandler: clickHandler
				        };
				        $rootScope.$broadcast('toaster-newToast');
				    };

				    this.clear = function () {
				        $rootScope.$broadcast('toaster-clearToasts');
				    };
				}])
				.constant('toasterConfig', {
				    'limit': 0,                   // limits max number of toasts 
				    'tap-to-dismiss': true,
				    'close-button': false,
				    'newest-on-top': true,
				    //'fade-in': 1000,            // done in css
				    //'on-fade-in': undefined,    // not implemented
				    //'fade-out': 1000,           // done in css
				    // 'on-fade-out': undefined,  // not implemented
				    //'extended-time-out': 1000,    // not implemented
				    'time-out': 5000, // Set timeOut and extendedTimeout to 0 to make it sticky
				    'icon-classes': {
				        error: 'toast-error',
				        info: 'toast-info',
				        wait: 'toast-wait',
				        success: 'toast-success',
				        warning: 'toast-warning'
				    },
				    'body-output-type': '', // Options: '', 'trustedHtml', 'template'
				    'body-template': 'toasterBodyTmpl.html',
				    'icon-class': 'toast-info',
				    'position-class': 'toast-top-right',
				    'title-class': 'toast-title',
				    'message-class': 'toast-message'
				})
				.directive('toasterContainer', ['$compile', '$timeout', '$sce', 'toasterConfig', 'toaster',
				function ($compile, $timeout, $sce, toasterConfig, toaster) {
				    return {
				        replace: true,
				        restrict: 'EA',
				        scope: true, // creates an internal scope for this directive
				        link: function (scope, elm, attrs) {

				            var id = 0,
				                mergedConfig;

				            mergedConfig = angular.extend({}, toasterConfig, scope.$eval(attrs.toasterOptions));

				            scope.config = {
				                position: mergedConfig['position-class'],
				                title: mergedConfig['title-class'],
				                message: mergedConfig['message-class'],
				                tap: mergedConfig['tap-to-dismiss'],
				                closeButton: mergedConfig['close-button']
				            };

				            scope.configureTimer = function configureTimer(toast) {
				                var timeout = typeof (toast.timeout) == "number" ? toast.timeout : mergedConfig['time-out'];
				                if (timeout > 0)
				                    setTimeout(toast, timeout);
				            };

				            function addToast(toast) {
				                toast.type = mergedConfig['icon-classes'][toast.type];
				                if (!toast.type)
				                    toast.type = mergedConfig['icon-class'];

				                id++;
				                angular.extend(toast, { id: id });

				                // Set the toast.bodyOutputType to the default if it isn't set
				                toast.bodyOutputType = toast.bodyOutputType || mergedConfig['body-output-type'];
				                switch (toast.bodyOutputType) {
				                    case 'trustedHtml':
				                        toast.html = $sce.trustAsHtml(toast.body);
				                        break;
				                    case 'template':
				                        toast.bodyTemplate = toast.body || mergedConfig['body-template'];
				                        break;
				                }

				                scope.configureTimer(toast);

				                if (mergedConfig['newest-on-top'] === true) {
				                    scope.toasters.unshift(toast);
				                    if (mergedConfig['limit'] > 0 && scope.toasters.length > mergedConfig['limit']) {
				                        scope.toasters.pop();
				                    }
				                } else {
				                    scope.toasters.push(toast);
				                    if (mergedConfig['limit'] > 0 && scope.toasters.length > mergedConfig['limit']) {
				                        scope.toasters.shift();
				                    }
				                }
				            }

				            function setTimeout(toast, time) {
				                toast.timeout = $timeout(function () {
				                    scope.removeToast(toast.id);
				                }, time);
				            }

				            scope.toasters = [];
				            scope.$on('toaster-newToast', function () {
				                addToast(toaster.toast);
				            });

				            scope.$on('toaster-clearToasts', function () {
				                scope.toasters.splice(0, scope.toasters.length);
				            });
				        },
				        controller: ['$scope', '$element', '$attrs', function ($scope, $element, $attrs) {

				            $scope.stopTimer = function (toast) {
				                if (toast.timeout) {
				                    $timeout.cancel(toast.timeout);
				                    toast.timeout = null;
				                }
				            };

				            $scope.restartTimer = function (toast) {
				                if (!toast.timeout)
				                    $scope.configureTimer(toast);
				            };

				            $scope.removeToast = function (id) {
				                var i = 0;
				                for (i; i < $scope.toasters.length; i++) {
				                    if ($scope.toasters[i].id === id)
				                        break;
				                }
				                $scope.toasters.splice(i, 1);
				            };

				            $scope.click = function (toaster) {
				                if ($scope.config.tap === true) {
				                    if (toaster.clickHandler && angular.isFunction($scope.$parent.$eval(toaster.clickHandler))) {
				                        var result = $scope.$parent.$eval(toaster.clickHandler)(toaster);
				                        if (result === true)
				                            $scope.removeToast(toaster.id);
				                    } else {
				                        if (angular.isString(toaster.clickHandler))
				                            console.log("TOAST-NOTE: Your click handler is not inside a parent scope of toaster-container.");
				                        $scope.removeToast(toaster.id);
				                    }
				                }
				            };
				        }],
				        template:
				        '<div  id="toast-container" ng-class="config.position">' +
				            '<div ng-repeat="toaster in toasters" class="toast" ng-class="toaster.type" ng-click="click(toaster)" ng-mouseover="stopTimer(toaster)"  ng-mouseout="restartTimer(toaster)">' +
				              '<button class="toast-close-button" ng-show="config.closeButton">&times;</button>' +
				              '<div ng-class="config.title">{{toaster.title}}</div>' +
				              '<div ng-class="config.message" ng-switch on="toaster.bodyOutputType">' +
				                '<div ng-switch-when="trustedHtml" ng-bind-html="toaster.html"></div>' +
				                '<div ng-switch-when="template"><div ng-include="toaster.bodyTemplate"></div></div>' +
				                '<div ng-switch-default >{{toaster.body}}</div>' +
				              '</div>' +
				            '</div>' +
				        '</div>'
				    };
				}])
.filter('reverse', function() {
					  return function(items) {
						    return items.slice().reverse();
						  };
						});

	genericFactory.$inject = ['genericHttpFactory', '$q', 'toastr'];
	
	/* @ngInject */
	function genericFactory(genericHttpFactory, $q, toastr) {
		var service = {			
			getAll : getAll,
			delet : delet,
			add : add,
			addList : addList,
			get :get,
			active : active,
			addReturn:addReturn,
			showAlert:showAlert,
		};
		return service;

		// ***************************************************************

		function getAll(msg,url) {
			var deferred = $q.defer();
			genericHttpFactory.getAll(url).then(function(response){
			//	toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				//alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function delet(msg,url,entity){
			var deferred = $q.defer();
			genericHttpFactory.delet(url,entity).then(function(response){
				toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function active(msg,url,entity){
			var deferred = $q.defer();
			genericHttpFactory.active(url,entity).then(function(response){
				toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function add(msg,url,entity){
			var deferred = $q.defer();
			genericHttpFactory.add(url,entity).then(function(response){
				//toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addReturn(msg,url,entity) {
			var deferred = $q.defer();
			genericHttpFactory.addReturn(url,entity).then(function(response){
			//	toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function addList(msg,url,entities){
			var deferred = $q.defer();
			genericHttpFactory.addList(url,entities).then(function(response){
				toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		function get(msg,url,entity_id){
			var deferred = $q.defer();
			genericHttpFactory.get(msg,url,entity_id).then(function(response){
				toastr.success(msg);
				deferred.resolve(response);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		
		function showAlert(containt) {
		      alert = $mdDialog.alert({
		        title: containt.title,
		        textContent:containt.massage,
		        ok: 'Close'
		      });

		      $mdDialog
		        .show( alert )
		        .finally(function() {
		          alert = undefined;
		        });
		    }
	}

})();