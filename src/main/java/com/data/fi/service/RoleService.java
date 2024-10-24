package com.data.fi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.fi.model.Role;
import com.data.fi.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id); // Find role by ID
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name); // Find role by name
    }

    public Role save(Role role) {
        return roleRepository.save(role); // Save role
    }

    public void deleteById(Long id) {
        roleRepository.deleteById(id); // Delete role by ID
    }

    // Add any additional methods as necessary
}
