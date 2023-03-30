package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameLike(String pattern);

    List<User> findByFirstnameLike(String pattern);

    List<User> findByLastnameLike(String pattern);

    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
