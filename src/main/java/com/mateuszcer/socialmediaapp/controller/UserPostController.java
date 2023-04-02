package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.Comment;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.payload.Mapper;
import com.mateuszcer.socialmediaapp.payload.request.CommentCreationRequest;
import com.mateuszcer.socialmediaapp.payload.request.PostCreationRequest;
import com.mateuszcer.socialmediaapp.payload.response.CommentResponse;
import com.mateuszcer.socialmediaapp.payload.response.UserPostResponse;
import com.mateuszcer.socialmediaapp.service.CommentLikeService;
import com.mateuszcer.socialmediaapp.service.CommentsService;
import com.mateuszcer.socialmediaapp.service.UserPostService;
import com.mateuszcer.socialmediaapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://127.0.0.1:5173")
public class UserPostController {
    private final UserPostService userPostService;

    private final UserService userService;

    private final CommentsService commentsService;

    private final CommentLikeService commentLikeService;

    private final Mapper mapper;
    public UserPostController(UserPostService userPostService, UserService userService, CommentsService commentsService, CommentLikeService commentLikeService, Mapper mapper) {
        this.userPostService = userPostService;
        this.userService = userService;
        this.commentsService = commentsService;
        this.commentLikeService = commentLikeService;
        this.mapper = mapper;
    }

    @PostMapping(path="/user_post/create")
    public ResponseEntity<UserPostResponse> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest,
                                               Principal principal, BindingResult result)
    {
        if(result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Optional<User> userOpt = userService.findByUsername(principal.getName());
        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User author = userOpt.get();
        String content = postCreationRequest.getContent();
        UserPost userPost = userPostService.createPost(content, author);

        return ResponseEntity.ok(mapper.toResponse(userPost));
    }

    @PostMapping(path="/user_post/{id}")
    public ResponseEntity<UserPostResponse> getPost(@PathVariable Long id)
    {

        Optional<UserPost> userPostOpt = userPostService.findById(id);
        return userPostOpt.map(userPost -> ResponseEntity.ok(mapper.toResponse(userPost)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping(path="/user_post/like")
    public ResponseEntity<?> likePost(@RequestParam(name="id") Long userPostId, Principal principal)
    {

        Optional<UserPost> userPostOpt = userPostService.findById(userPostId);
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

        Optional<UserPost> userPostOpt = userPostService.findById(userPostId);
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

    @PostMapping("/user_post/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Principal principal) {
        Optional<UserPost> userPostOpt = userPostService.findById(id);

        if(userPostOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserPost userPost = userPostOpt.get();

        if(!principal.getName().equals(userPost.getAuthor().getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        userPostService.delete(userPost);

        return ResponseEntity.ok("Post deleted");
    }

    @GetMapping("/user_post/comments/{id}")
    public ResponseEntity<Iterable<CommentResponse>> getAllComments(@PathVariable Long id) {
        Optional<UserPost> userPostOpt = userPostService.findById(id);

        if(userPostOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        UserPost userPost = userPostOpt.get();

        List<Comment> comments = userPostService.getAllComments(userPost);

        return ResponseEntity.ok(comments.stream().map(mapper::toResponse).collect(Collectors.toSet()));
    }

    @PostMapping("/user_post/comments/add")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentCreationRequest commentCreationRequest,
                                                            Principal principal, BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        Optional<UserPost> userPostOpt = userPostService.findById(commentCreationRequest.getUserPostId());

        if(userPostOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserPost userPost = userPostOpt.get();
        Optional<User> userOpt = userService.findByUsername(principal.getName());

        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        Comment comment = userPostService.createComment(userPost, user, commentCreationRequest.getContent());
        return ResponseEntity.ok(mapper.toResponse(comment));
    }

    @PostMapping("/user_post/comments/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, Principal principal) {
        Optional<Comment> commentOpt = commentsService.findById(id);
        if(commentOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Comment comment = commentOpt.get();
        if(!comment.getAuthor().getUsername().equals(principal.getName())) {
            return ResponseEntity.badRequest().build();
        }

        commentsService.delete(comment);

        return ResponseEntity.ok("Post disliked");
    }

    @PostMapping("/user_post/comments/like/{id}")
    public ResponseEntity<String> likeComment(@PathVariable Long id, Principal principal) {
        Optional<Comment> commentOpt = commentsService.findById(id);
        if(commentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Comment doesn't exist");
        }
        Comment comment = commentOpt.get();

        Optional<User> userOpt = userService.findByUsername(principal.getName());
        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User doesnt exist");
        }

        User user = userOpt.get();
        if(commentLikeService.likeComment(comment, user)) {
            return ResponseEntity.ok("Comment liked successfully");
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/user_post/comments/dislike/{id}")
    public ResponseEntity<String> dislikeComment(@PathVariable Long id, Principal principal) {
        Optional<Comment> commentOpt = commentsService.findById(id);
        if(commentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Comment comment = commentOpt.get();

        Optional<User> userOpt = userService.findByUsername(principal.getName());
        if(userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOpt.get();
        if(commentLikeService.dislikeComment(comment, user)) {
            return ResponseEntity.ok("Comment disliked successfully");
        }
        return ResponseEntity.badRequest().build();
    }






}
