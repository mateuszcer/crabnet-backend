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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
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
    public ResponseEntity<PostCreationResponse> createPost(@RequestBody PostCreationRequest postCreationRequest,
                                               Principal principal)
    {
        User author;
        author = userService.findByUsername(principal.getName());
        String content = postCreationRequest.getContent();
        UserPost userPost = userPostService.createPost(content, author);
        return ResponseEntity.ok(new PostCreationResponse(content, author.getUsername(),
                userPost.getCreateDateTime()));
    }

    @PostMapping(path="/user_post/like")
    public ResponseEntity<?> createPost(@RequestParam(name="id") Long userPostId, Principal principal)
    {

        Optional<UserPost> userPostOpt = userPostService.getById(userPostId);
        if(userPostOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserPost userPost = userPostOpt.get();
        User user = userService.findByUsername(principal.getName());
        userPostService.likePost(userPost, user);
        return ResponseEntity.ok("Post liked successfully");
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

}
