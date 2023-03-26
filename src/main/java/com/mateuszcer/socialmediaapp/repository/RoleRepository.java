package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.ERole;
import com.mateuszcer.socialmediaapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}