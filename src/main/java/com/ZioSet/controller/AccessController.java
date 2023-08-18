package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.dto.PermissionActionDTO;
import com.ZioSet.dto.PermissionDTO;
import com.ZioSet.dto.ResponceObj;
import com.ZioSet.dto.RolePermissionDto;
import com.ZioSet.model.PermissionAction;
import com.ZioSet.model.Permissions;
import com.ZioSet.model.Role;
import com.ZioSet.model.RolePermission;
import com.ZioSet.Service.AccessService;
import com.ZioSet.Service.UserServices;

@RestController
@CrossOrigin("*")
@RequestMapping("/access")
public class AccessController {

	@Autowired
	private AccessService accessService;
	
	@Autowired
	private UserServices userService; 
	
	
	@GetMapping("/getRolesPermission")
	public @ResponseBody List<RolePermissionDto> getRolesPermission() {
		List<RolePermissionDto> list= new  ArrayList<RolePermissionDto>();
		try {
			List<Role> roles= userService.getAllRoles();
			List<Permissions> permissions= accessService.getAllPermission();
			System.out.println("PER "+permissions.size());
			
			for(Role role:roles){
				RolePermissionDto rolePermissionDto=new RolePermissionDto();
				rolePermissionDto.setRole(role);
				List<PermissionDTO> dtos= new ArrayList<PermissionDTO>();
				for(Permissions permission:permissions){
					PermissionDTO  permissionDTO= new PermissionDTO();
					permissionDTO.setPermissionName(permission.getPermissionsName());
					Optional<RolePermission> optional= accessService.getRolePermissionByRoleAndPermission(role.getRoleId(),permission.getPermissionsId());
					if(optional.isPresent()){
						permissionDTO.setPermissionAvailable(true);
					}else{
						permissionDTO.setPermissionAvailable(false);

					}
					permissionDTO.setEditTab(false);
					dtos.add(permissionDTO);
				}
				rolePermissionDto.setPermissions(dtos);
				list.add(rolePermissionDto);
			
			
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@GetMapping("/getPermissionsByRole")
	public @ResponseBody List<Permissions> getPermissionsByRole(@RequestParam("roleId") int roleId) {
		List<Permissions> list= new  ArrayList<Permissions>();
		try {
			List<RolePermission> rolepermissions= accessService.getPermissionsByRole(roleId);
			for(RolePermission rolePermission:rolepermissions){
				list.add(rolePermission.getPermissions());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@PostMapping("/saveRolePermission")
	public ResponseEntity<ResponceObj> saveRolePermission(@RequestBody RolePermissionDto rolePermissions  ) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			Role role=rolePermissions.getRole();
			for(PermissionDTO permissions:rolePermissions.getPermissions()){
				Optional<Permissions> optional= accessService.getPermissionsByName(permissions.getPermissionName());
				Optional<RolePermission> optional2= accessService.getRolePermissionByRoleAndPermission(role.getRoleId(),optional.get().getPermissionsId());

				if(permissions.isPermissionAvailable()){
					if(!optional2.isPresent()){
						RolePermission rolepermission= new RolePermission();
						rolepermission.setPermissions(optional.get());
						rolepermission.setRole(role);
						accessService.saveRolePermission(rolepermission);
						for(int i=1;i<=5;i++){
							PermissionAction action= new PermissionAction();
							action.setPermissions(rolepermission.getPermissions());
							action.setRole(rolepermission.getRole());
							if(i==1){
								System.out.println("Add Permision");
								action.setActionName("Add");
								action.setAvailable(false);
								accessService.savePermissionAction(action);
							}
							if(i==2){
								System.out.println("Edit Permision");
								System.out.println("Add Permision");
								action.setActionName("Add");
								action.setAvailable(false);
								accessService.savePermissionAction(action);
							}
							if(i==3){
								System.out.println("View Permision");
								action.setActionName("View");
								action.setAvailable(false);
								accessService.savePermissionAction(action);
							}
							if(i==4){
								System.out.println("Delete Permision");
								action.setActionName("Delete");
								action.setAvailable(false);
								accessService.savePermissionAction(action);
							}
							if(i==5){
								System.out.println("Upload Permision");
								action.setActionName("Upload");
								action.setAvailable(false);
								accessService.savePermissionAction(action);
							}
						}
					}
					
				}else{
					if(optional2.isPresent()){
						accessService.deleteRolePermission(optional2.get());
						List<PermissionAction> permissionActions=accessService.getPermissionActionByRoleAndPermission(optional2.get().getRole().getRoleId(),optional2.get().getPermissions().getPermissionsId());
						accessService.deleteAllPermissionAction(permissionActions);
					
					}
				}
				
			}
		
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}
	
	@PostMapping("/addPermission")
	public ResponseEntity<ResponceObj> addPermission(@RequestBody Permissions permissions  ) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			Optional<Permissions> optional= accessService.getPermissionsByNameAndCategory(permissions.getCategory(),permissions.getPermissionsName());
			System.out.println("Saving Permission "+permissions.getPermissionsName());
			
			permissions.setActive(1);
			accessService.addPermission(permissions);
			/*if(optional.isPresent()){
				//accessService.addPermission(permissions);
				responceDTO.setCode(500);
				responceDTO.setMessage("Permissions is already exits");
			}else{
				permissions.setActive(1);
				accessService.addPermission(permissions);
				responceDTO.setCode(200);
				responceDTO.setMessage("Permissions Added Successfully");
			}
				
		*/
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}
	@PostMapping("/deletePermission")
	public ResponseEntity<ResponceObj> deletePermission(@RequestBody Permissions permissions  ) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			Optional<Permissions> optional = null;
			System.out.println("Saving Permission "+permissions.getPermissionsName());
			if(optional.isPresent()){
				//accessService.addPermission(permissions);
				responceDTO.setCode(500);
				responceDTO.setMessage("Permissions Are used someever so can not be deleted");
			}else{
				permissions.setActive(1);
				accessService.deletePermission(permissions);
				responceDTO.setCode(200);
				responceDTO.setMessage("Permissions Deleted Successfully");
			}
				
		
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}
	@PostMapping("/changeStatusPermission")
	public ResponseEntity<ResponceObj> changeStatusPermission(@RequestBody Permissions permissions  ) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			if(permissions.getActive()==1){
				permissions.setActive(0);
				}else{
				permissions.setActive(1);
				
			}
				
			accessService.addPermission(permissions);
			responceDTO.setCode(200);
			responceDTO.setMessage("Status Changed Successfully");
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}
	@GetMapping("/getPermissionsByPagination/{page_no}/{item_per_page}")
	public @ResponseBody List<Permissions> getPermissionsByPagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Permissions> list= new  ArrayList<Permissions>();
		try {	
			list=accessService.getPermissionsByPagination(page_no,item_per_page);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/getPermissionsByPaginationAndSerach")
	public @ResponseBody List<Permissions> getPermissionsByPaginationAndSerach(@RequestParam("page_no") int page_no,@RequestParam("item_per_page") int item_per_page,@RequestParam("search") String search) {
		List<Permissions> list= new  ArrayList<Permissions>();
		try {	
			list=accessService.getPermissionsByPaginationAndSerach(page_no,item_per_page,search);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@GetMapping("/getPermissionsCount")
	public @ResponseBody int  getPermissionsCount() {
		int  count= 0;	
		try {
			count= accessService.getPermissionsCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@GetMapping("/getPermissionsCountBySearch/{search}")
	public @ResponseBody int  getPermissionsCountBySearch(@PathVariable("search") String search) {
		int  count= 0;
		try {
			count= accessService.getPermissionsCountBySearch(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@GetMapping("/checkPermissionName")
	public @ResponseBody ResponceObj  checkPermissionName(@RequestParam("permissionName") String permissionName) {
		ResponceObj  responceObj= new ResponceObj();
		try {
			Optional<Permissions> optional = accessService.getPermissionsByName(permissionName);
			if(optional.isPresent()){
				responceObj.setCode(500);
				responceObj.setMessage("Permission name already Exit");
			}else{
				responceObj.setCode(200);
				responceObj.setMessage("Permission name is valid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObj;
	}

	@GetMapping("/checkRoleName")
	public @ResponseBody ResponceObj  checkRoleName(@RequestParam("roleName") String roleName) {
		ResponceObj  responceObj= new ResponceObj();
		try {
			Optional<Role> optional = accessService.getRoleByName(roleName);
			if(optional.isPresent()){
				responceObj.setCode(500);
				responceObj.setMessage("Role name already Exit");
			}else{
				responceObj.setCode(200);
				responceObj.setMessage("Role name is valid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObj;
	}

	@PostMapping("/addRole")
	public ResponseEntity<ResponceObj> addRole(@RequestBody Role role) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			accessService.addRole(role);
			
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}
	@GetMapping("/getPermisssionActionByRoleAndPermission")
	public @ResponseBody PermissionActionDTO  getPermisssionActionByRoleAndPermission(@RequestParam("role") int role,@RequestParam("permisssion") String permisssion) {
		PermissionActionDTO   permissionActionDTO= new PermissionActionDTO();
		try {
			Optional<Permissions> optional=accessService.getPermissionsByName(permisssion);
			List<PermissionAction>   list = accessService.getPermissionActionByRoleAndPermission(role,optional.get().getPermissionsId());
			permissionActionDTO.setRole(list.get(0).getRole());
			permissionActionDTO.setPermissions(optional.get());
			permissionActionDTO.setActions(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissionActionDTO;
	}
	

	@PostMapping("/updatePermissionAction")
	public ResponseEntity<ResponceObj> updatePermissionAction(@RequestBody PermissionAction permissionAction) {
		ResponceObj responceDTO= new ResponceObj();

		try {
			accessService.savePermissionAction(permissionAction);
			
			
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return new ResponseEntity<ResponceObj>(responceDTO,HttpStatus.ACCEPTED)	;
		}
		
			
	}

}
