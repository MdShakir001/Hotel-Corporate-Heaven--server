package com.shakir.hotelCorporateHeaven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shakir.hotelCorporateHeaven.exception.RoleAlreadyExistException;
import com.shakir.hotelCorporateHeaven.model.Role;
import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
	private final RoleService roleService;
	@GetMapping("/all")
	public ResponseEntity<List<Role>> getAllRoles(){
		return new ResponseEntity<List<Role>>(roleService.getRoles(),HttpStatus.FOUND);
	}
	@PostMapping("/addRole")
	public ResponseEntity<String> createNewRole(@RequestBody Role theRole){
		try {
			roleService.createRole(theRole);
			return ResponseEntity.ok("New Role created succesfully");
		}catch(RoleAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in creating Role "+e.getMessage());
		}
		
	}
	@DeleteMapping("/delete/{roleId}")
	public  void deleteRole(@PathVariable("roleId") Long roleId) {
		roleService.deleteRole(roleId);
	}
	@PostMapping("/removeAllUserFromRole/{roleId}")
	public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
		return roleService.removeAllUsersFromRole(roleId);
	}
	@PostMapping("/removeUserFromRole")
	public User removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId")Long roleId) {
		return roleService.removeUserFromRole(userId, roleId);
	}
	@PostMapping("/assignRoleToUser")
	public User assignRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId")Long roleId) {
		return roleService.assignRoleToUser(userId, roleId);
	}
	
	
}
