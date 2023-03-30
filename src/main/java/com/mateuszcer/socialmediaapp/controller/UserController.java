package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.payload.Mapper;
import com.mateuszcer.socialmediaapp.payload.request.BioRequest;
import com.mateuszcer.socialmediaapp.payload.response.UserResponse;
import com.mateuszcer.socialmediaapp.repository.UserRepository;
import com.mateuszcer.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    @GetMapping(path="/user/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt.map(user -> ResponseEntity.ok(
                mapper.toResponse(user))).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(path="/user/all/{pattern}")
    public ResponseEntity<Set<UserResponse>> getAllByPattern(@PathVariable String pattern) {
        List<User> matchers = userService.findAllByPattern(pattern+"%");
        HashSet<UserResponse> res = new HashSet(matchers.stream().map(mapper::toResponse).collect(Collectors.toSet()));
        return ResponseEntity.ok(res);
    }

    @PostMapping(path="/user/follow/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username,
                                                   Principal principal) {
        if(userService.followUser(principal.getName(), username)) {
            return ResponseEntity.ok("Followed");
        }
        return ResponseEntity.badRequest().build();

    }

    @PostMapping(path="/user/unfollow/{username}")
    public ResponseEntity<String> unFollowUser(@PathVariable String username,
                                             Principal principal) {
        if(userService.unFollowUser(principal.getName(), username))
            return ResponseEntity.ok("Unfollowed");

        return ResponseEntity.badRequest().build();
    }


    @PostMapping(path="/user/bio")
    public ResponseEntity<String> updateBio(@RequestBody BioRequest bio, Principal principal) {

        if(bio.getBio().length() > 100) {
            return ResponseEntity.badRequest().body("Bio too long");
        }
        if(userService.updateBio(principal.getName(), bio.getBio())) {
            return ResponseEntity.ok("Bio updated");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path="/user/picture/{id}")
    public ResponseEntity<String> updateBio(@PathVariable Integer id, Principal principal) {

        if(id >= 6) {
            return ResponseEntity.badRequest().body("Id out of range");
        }
        if(userService.updatePicture(principal.getName(), id)) {
            return ResponseEntity.ok("Picture updated");
        }
        return ResponseEntity.badRequest().build();
    }





}
