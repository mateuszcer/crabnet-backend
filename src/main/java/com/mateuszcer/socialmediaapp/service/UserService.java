package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.*;
import com.mateuszcer.socialmediaapp.payload.request.SignupRequest;
import com.mateuszcer.socialmediaapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateuszcer.socialmediaapp.exceptions.UserAlreadyExistException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements IUserService{
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final FollowersService followersService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, FollowersService followersService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.followersService = followersService;
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean followUser(String username, String toFollowUsername) {
        User user = userRepository.findByUsername(username);
        User toFollow = userRepository.findByUsername(toFollowUsername);

        Set<Followers> following = user.getFollowing();
        Followers follow = new Followers(user, toFollow);
        following.add(follow);
        followersService.update(follow);
        userRepository.save(user);
        userRepository.save(toFollow);
        return true;
    }

    @Override
    public User registerUser(SignupRequest signupRequest) throws UserAlreadyExistException {

        String username = signupRequest.getUsername();
        String email = signupRequest.getEmail();
        String password = signupRequest.getPassword();
        String firstname = signupRequest.getFirstname();
        String lastname = signupRequest.getLastname();
        String genderString = signupRequest.getGender();
        EGender gender;
        if(genderString.equals("Male")) {
            gender = EGender.GENDER_MALE;
        }
        else if(genderString.equals("Female")) {
            gender = EGender.GENDER_FEMALE;
        }
        else {
            gender = EGender.GENDER_OTHER;
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistException("Email is already in use!");
        }

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistException("Username is already in use!");
        }

        User newUser = User
                .builder()
                .email(email)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .password(passwordEncoder.encode(password))
                .gender(gender)
                .enabled(Boolean.FALSE)
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public Set<User> getFollowers(String username) {
        User user = userRepository.findByUsername(username);
        List<Followers> followers = followersService.getAllByTo(user);
        return followers.stream().map(Followers::getFrom).collect(Collectors.toSet());
    }

    public Set<User> getFollowing(String username) {
        User user = userRepository.findByUsername(username);
        List<Followers> followers = followersService.getAllByFrom(user);
        return followers.stream().map(Followers::getTo).collect(Collectors.toSet());
    }



}
