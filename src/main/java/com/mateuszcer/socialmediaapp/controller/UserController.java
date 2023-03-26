package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.payload.Mapper;
import com.mateuszcer.socialmediaapp.payload.response.UserResponse;
import com.mateuszcer.socialmediaapp.repository.UserRepository;
import com.mateuszcer.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://127.0.0.1:5173")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;


    private final Mapper mapper;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, Mapper mapper) {

        this.userRepository = userRepository;
        this.userService = userService;

        this.mapper = mapper;
    }

    @GetMapping(path="/user/all")
    public ResponseEntity<Iterable<UserResponse>>
    getAllUsers() {
        return ResponseEntity.ok(
                userRepository.findAll()
                        .stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList()));
    }

    @GetMapping(path="/user/self")
    public ResponseEntity<UserResponse>
    getSelfInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @GetMapping(path="/user/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PostMapping(path="/user/follow/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username,
                                                   Principal principal) {
        userService.followUser(principal.getName(), username);
        return ResponseEntity.ok("Followed");
    }






}
