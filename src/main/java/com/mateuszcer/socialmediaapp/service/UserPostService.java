package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.Comment;
import com.mateuszcer.socialmediaapp.model.Likes;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.repository.UserPostRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserPostService {

    private final UserPostRepository userPostRepository;
    private final LikesService likesService;
    private final CommentsService commentsService;

    private final CommentLikeService commentLikeService;

    public UserPostService(UserPostRepository userPostRepository, LikesService likesService, CommentsService commentsService, CommentLikeService commentLikeService) {
        this.userPostRepository = userPostRepository;
        this.likesService = likesService;
        this.commentsService = commentsService;
        this.commentLikeService = commentLikeService;
    }

    public final Optional<UserPost> findById(Long id) {
        return userPostRepository.findById(id);
    }


    public final Boolean likePost(UserPost userPost, User user) {
        if(likesService.existsByFromAndTo(user, userPost)) {
            return Boolean.FALSE;
        }

        Likes likes = new Likes(user, userPost);
        likesService.update(likes);
        return Boolean.TRUE;
    }

    public UserPost createPost(String content, User author) {

        UserPost userPost = UserPost.builder()
                .content(content)
                .author(author)
                .likedBy(new HashSet<>())
                .comments(new HashSet<>())
                .build();

        return this.userPostRepository.save(
                userPost);
    }

    public Boolean dislikePost(UserPost userPost, User user) {
        Optional<Likes> likesOpt = likesService.findByFromAndTo(user, userPost);

        if(likesOpt.isEmpty()) {
            return Boolean.FALSE;
        }

        Likes likes = likesOpt.get();
        likesService.delete(likes);
        return Boolean.TRUE;
    }

    public List<UserPost> getByUser(User user) {
        return userPostRepository.findAllByAuthor(user);
    }

    public List<UserPost> getNewest(User author) {
        return userPostRepository.findFirst25ByAuthor(author);
    }

    public void delete(UserPost userPost) {
        commentsService.deleteAll(userPost.getComments());
        likesService.deleteAllByPost(userPost);
        userPostRepository.delete(userPost);
    }

    public List<Comment> getAllComments(UserPost userPost) {
        return commentsService.findAllBySource(userPost);
    }

    public Comment createComment(UserPost userPost, User author, String content) {
        return commentsService.createComment(userPost, author, content);
    }








}
