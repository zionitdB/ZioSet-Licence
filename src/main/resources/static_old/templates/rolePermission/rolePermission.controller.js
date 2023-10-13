(function () {
	'use strict';

	angular.module('myApp.rolePermission').controller('RolePermissionController', RolePermissionController);

	RolePermissionController.$inject = ['$state', '$log', '$scope', 'toastr', 'localStorageService', 'ApiEndpoint', 'loginFactory', 'genericFactory', '$document', '$rootScope','$window'];

	/* @ngInject */
	function RolePermissionController($state, $log, $scope, toastr, localStorageService, ApiEndpoint, loginFactory, genericFactory, $document, $rootScope,$window) {
		var accessUrl = ApiEndpoint.url + "access";
	
		var vm = angular.extend(this, {
			save:save,
			addPermission:addPermission,
			savePermission:savePermission,
			addRole:addRole,
			saveRole:saveRole,
			viewActions:viewActions,
			selPermission:[],
		cancle:cancle
		});

		(function activate() {
			loadRolePermission();
			loadPermissionCount();
			$window.scrollTo(0,0);
			
		})();
		function cancle(){
			$scope.actionTab=false
			$scope.addPermissionTab=false;
			$scope.addRoleTab=false;
		}
		
		$scope.upadateAction=function(action){
			console.log("Action" + JSON.stringify(action))
			var msg = "";
			var url = accessUrl + "/updatePermissionAction";
			genericFactory.add(msg, url,action).then(function (response) {
				//vm.serialRole= response.data;
				//console.log("serialRole" + JSON.stringify(vm.serialRole))

			});
			
		}
		function viewActions(permissions){
			vm.selPermission=[]
			$scope.actionTab=true
			vm.selRole=permissions.role
			console.log("permissions" + JSON.stringify(permissions))
			
			angular.forEach(permissions.permissions, function(item) {
				 if(item.permissionAvailable){
					 
					 console.log("Permisssion "+JSON.stringify(item));
					 console.log("ROLE  "+JSON.stringify(vm.selRole))
					 var msg = "";
						var url = accessUrl + "/getPermisssionActionByRoleAndPermission?role="+vm.selRole.roleId+"&permisssion="+item.permissionName;
						genericFactory.getAll(msg, url).then(function (response) {
							vm.permissionAction= response.data;
							 vm.selPermission.push(vm.permissionAction)

						

							console.log("selPermission" + JSON.stringify(vm.selPermission))
			
						});
					/* item.actions=[];
					 var addAct={};
					 addAct.actionName="Add"
					addAct.isAvailable=true;
					 item.actions.push(addAct)
					 
					  var addAct1={};
					 addAct1.actionName="Edit"
						 addAct1.isAvailable=true;
					 item.actions.push(addAct1)
					 
					  var addAct2={};
					 addAct2.actionName="View"
						 addAct2.isAvailable=true;
					 item.actions.push(addAct2)
					 
					  var addAct3={};
					 addAct3.actionName="Delete"
						 addAct3.isAvailable=true;
					 item.actions.push(addAct3)
					 
					 vm.selPermission.push(item)*/
				 }
				});
			
			
		}
		
		
		function saveRole(role){
			if(role.roleName==""||role.roleName==undefined){
				$scope.roleErr=true;
				return;
			}else{
				$scope.roleErr=false;
			}
			if(vm.serialRole.code==500){
				return;
				
			}
			var msg = "";
			var url = accessUrl + "/addRole";
			genericFactory.add(msg, url,role).then(function (response) {
				loadRolePermission();
				$scope.addPermissionTab=false;
				$scope.addRoleTab=false;
				});
		}
		$scope.checkRoleName=function (role){
			console.log("permissionName" + JSON.stringify(role))
			var msg = "";
			var url = accessUrl + "/checkRoleName?roleName="+role;
			genericFactory.getAll(msg, url).then(function (response) {
				vm.serialRole= response.data;
				console.log("serialRole" + JSON.stringify(vm.serialRole))

			});
		}
		function addRole(){
			$scope.addPermissionTab=false;
			$scope.addRoleTab=true;
		}
		
		$scope.checkPermissionName=function (permissionName){
			var msg = "";
			var url = accessUrl + "/checkPermissionName?permissionName="+permissionName;
			genericFactory.getAll(msg, url).then(function (response) {
				vm.serialRes= response.data;
			});
		}
		function addPermission(){
			$scope.addPermissionTab=true;
			$scope.addRoleTab=false;
		}
		function savePermission(permission){
			if(permission.category==""||permission.category=="null"||permission.category==null){
				$scope.catErr=true;
				return;
			}else{
				$scope.catErr=false;
			}
			
			
			if(permission.permissionsName==""||permission.permissionsName==undefined){
				$scope.perNameErr=true;
				return;
			}else{
				$scope.perNameErr=false;
			}
			if(vm.serialRes.code==500){
				return;
				
			}
				console.log("permission" + JSON.stringify(permission))
				var msg = "";
				var url = accessUrl + "/addPermission";
				genericFactory.add(msg, url,permission).then(function (response) {
					loadRolePermission();
					$scope.addPermissionTab=false;
/*					vm.rolePermissions = response.data;
 * 
*/				});
		}
		$scope.checkPermission=function(permis,parindex,index){
			console.log("permis " + JSON.stringify(permis)+" For Ind "+index+" OF parent"+parindex)

		}
		$scope.editPermission=function(index){
			vm.rolePermissions[index].editTabButtom=true
			angular.forEach(vm.rolePermissions[index].permissions,function(permission){
				permission.editTab=true;
				})

		}
		function loadRolePermission(){
			var msg = "";
			var url = accessUrl + "/getRolesPermission";
			genericFactory.getAll(msg, url).then(function (response) {
				vm.rolePermissions = response.data;
			});
		}
		
		function loadPermissionCount(){
			var msg = "";
			var url = accessUrl + "/getPermissionsCount";
			genericFactory.getAll(msg, url).then(function (response) {
				vm.permissionCount = response.data;
			});
		}
		function save(rolepermission,index){
			
			
			var msg = "";
			var url = accessUrl + "/saveRolePermission";
			genericFactory.add(msg, url,rolepermission).then(function (response) {
				vm.rolePermissions[index].editTabButtom=false
				loadRolePermission()
				angular.forEach(vm.rolePermissions[index].permissions,function(permission){
					permission.editTab=false;
					})
					
			});

		//	console.log("rolepermission" + JSON.stringify(rolepermission))
			


		}

			}
})();