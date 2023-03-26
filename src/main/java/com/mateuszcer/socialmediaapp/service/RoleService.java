package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.ERole;
import com.mateuszcer.socialmediaapp.model.Role;
import com.mateuszcer.socialmediaapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
