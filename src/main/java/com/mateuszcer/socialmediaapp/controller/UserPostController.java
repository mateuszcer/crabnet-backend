package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.payload.Mapper;
import com.mateuszcer.socialmediaapp.payload.request.PostCreationRequest;
import com.mateuszcer.socialmediaapp.payload.response.PostCreationResponse;
import com.mateuszcer.socialmediaapp.payload.response.UserPostResponse;
import com.mateuszcer.socialmediaapp.service.UserPostService;
import com.mateuszcer.socialmediaapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://127.0.0.1:5173")
public class UserPostController {
    private final UserPostService userPostService;

    private final UserService userService;

    private final Mapper mapper;
    public UserPostController(UserPostService userPostService, DaoAuthenticationProvider daoAuthenticationProvider, UserService userService, Mapper mapper) {
        this.userPostService = userPostService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping(path="/user_post/create")
    public ResponseEntity<UserPostResponse> createPost(@RequestBody PostCreationRequest postCreationRequest,
                                               Principal principal)
    {

        Optional<User> userOpt = userService.findByUsername(principal.getName());
        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User author = userOpt.get();
        String content = postCreationRequest.getContent();
        UserPost userPost = userPostService.createPost(content, author);
        userPost.setLikedBy(new HashSet<>());
        return ResponseEntity.ok(mapper.toResponse(userPost));
    }

    @PostMapping(path="/user_post/like")
    public ResponseEntity<?> likePost(@RequestParam(name="id") Long userPostId, Principal principal)
    {

        Optional<UserPost> userPostOpt = userPostService.getById(userPostId);
        if(userPostOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserPost userPost = userPostOpt.get();
        Optional<User> userOpt = userService.findByUsername(principal.getName());

        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        if(userPostService.likePost(userPost, user)) {
            return ResponseEntity.ok("Post liked successfully");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path="/user_post/dislike")
    public ResponseEntity<?> dislikePost(@RequestParam(name="id") Long userPostId, Principal principal)
    {

        Optional<UserPost> userPostOpt = userPostService.getById(userPostId);
        if(userPostOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserPost userPost = userPostOpt.get();
        Optional<User> userOpt = userService.findByUsername(principal.getName());

        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        if(userPostService.dislikePost(userPost, user)) {
            return ResponseEntity.ok("Post disliked successfully");
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping(path="/user_post/all/{id}")
    public ResponseEntity<Iterable<UserPostResponse>> getAllByAuthor(@PathVariable Long id)
    {

        Optional<User> userOpt = userService.findById(id);
        if(userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        User user = userOpt.get();




        List<UserPost> posts = userPostService.getByUser(user);
        return ResponseEntity.ok(posts.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }


    @GetMapping("/user_post/newest/{username}")
    public ResponseEntity<List<UserPostResponse>> getNewest(@PathVariable String username) {
        Optional<User> userOpt = userService.findByUsername(username);

        return userOpt.map(user -> ResponseEntity.ok(userPostService.getNewest(user).stream().map(mapper::toResponse)
                .collect(Collectors.toList()))).orElseGet(() -> ResponseEntity.badRequest().build());
    }



}
