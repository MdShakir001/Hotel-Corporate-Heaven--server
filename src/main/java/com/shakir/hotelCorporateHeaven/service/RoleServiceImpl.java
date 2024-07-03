package com.shakir.hotelCorporateHeaven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shakir.hotelCorporateHeaven.exception.RoleAlreadyExistException;
import com.shakir.hotelCorporateHeaven.exception.UserAlreadyExistException;
import com.shakir.hotelCorporateHeaven.model.Role;
import com.shakir.hotelCorporateHeaven.model.User;
import com.shakir.hotelCorporateHeaven.repository.RoleRepository;
import com.shakir.hotelCorporateHeaven.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Override
	public List<Role> getRoles() {
		
		return roleRepository.findAll();
	}

	@Override
	public Role createRole(Role theRole) {
		String roleName="ROLE_"+theRole.getName().toUpperCase();
		Role role=new Role(roleName);
		if(roleRepository.existsByName(roleName)) {
			throw new RoleAlreadyExistException(theRole.getName()+" role already Exist");
		}
		return roleRepository.save(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		this.removeAllUsersFromRole(roleId);
		roleRepository.deleteById(roleId);
		
		
	}

	@Override
	public Role findByName(String name) {
		
		return roleRepository.findByName(name).get();
	}

	@Override
	public User removeUserFromRole(Long userId, Long roleId) {
		Optional<User> user=userRepository.findById(userId);
		Optional<Role> role =roleRepository.findById(roleId);
		if(role.isPresent() && role.get().getUsers().contains(user.get())) {
			role.get().removeUserFromRole(user.get());
			roleRepository.save(role.get());
			return user.get();
			
		}
		throw new UsernameNotFoundException("User not found");
	}

	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		Optional<User> user=userRepository.findById(userId);
		Optional<Role> role =roleRepository.findById(roleId);
		if(user.isPresent() && user.get().getRoles().contains(role.get())) {
			throw new UserAlreadyExistException(user.get().getFirstName()+
					" already Exist for the role "+role.get().getName());
		}
		if(role.isPresent()) {
			role.get().assignRoleToUser(user.get());
			roleRepository.save(role.get());
		}
		return user.get();
	}

	@Override
	public Role removeAllUsersFromRole(Long roleId) {
		Optional<Role> role=roleRepository.findById(roleId);
		role.get().removeAllUsersFromRole();
		
		return roleRepository.save(role.get());
	}

}
