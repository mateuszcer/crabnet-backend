package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.exceptions.UserAlreadyExistException;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.payload.request.SignupRequest;

import java.util.Optional;

public interface IUserService {
    public Optional<User> findById(Long id);

    public Boolean existsById(Long id);

    public Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);
    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);

    public User registerUser(SignupRequest signupRequest) throws UserAlreadyExistException;

    void saveRegisteredUser(User user);


}
