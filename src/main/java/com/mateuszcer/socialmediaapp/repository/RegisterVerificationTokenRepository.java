package com.mateuszcer.socialmediaapp.repository;

import com.mateuszcer.socialmediaapp.model.RegisterVerificationToken;
import com.mateuszcer.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterVerificationTokenRepository
        extends JpaRepository<RegisterVerificationToken, Long> {

    RegisterVerificationToken findByToken(String token);

    RegisterVerificationToken findByUser(User user);
}