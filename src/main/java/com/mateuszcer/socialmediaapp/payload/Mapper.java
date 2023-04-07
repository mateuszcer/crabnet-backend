package com.mateuszcer.socialmediaapp.payload;

import com.mateuszcer.socialmediaapp.chat.model.ChatMessage;
import com.mateuszcer.socialmediaapp.chat.payload.ChatMessageRequest;
import com.mateuszcer.socialmediaapp.chat.payload.ChatMessageResponse;
import com.mateuszcer.socialmediaapp.model.Comment;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
import com.mateuszcer.socialmediaapp.payload.response.CommentResponse;
import com.mateuszcer.socialmediaapp.payload.response.MinimalUserResponse;
import com.mateuszcer.socialmediaapp.payload.response.UserResponse;
import com.mateuszcer.socialmediaapp.payload.response.UserPostResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class Mapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getUsername(), user.getFirstname(),
                user.getLastname(),
                user.getPosts().stream().map(this::toResponse).collect(Collectors.toSet()),
                user.getFollowing().stream().map(followers -> this.toMinimalResponse(followers.getTo()))
                        .collect(Collectors.toSet()),
                user.getFollowers().stream().map(followers -> this.toMinimalResponse(followers.getFrom()))
                        .collect(Collectors.toSet()),
                user.getBio(),
                user.getProfilePicture()
        );

    }

    public MinimalUserResponse toMinimalResponse(User user) {
        return new MinimalUserResponse(
                user.getUsername(),
                user.getProfilePicture(),
                user.getFirstname(),
                user.getLastname()
        );
    }

    public UserPostResponse toResponse(UserPost userPost) {

        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setAuthorUsername(userPost.getAuthor().getUsername());
        userPostResponse.setContent(userPost.getContent());
        userPostResponse.setId(userPost.getId());
        userPostResponse.setLikedBy(userPost.getLikedBy().stream().map(likes -> likes.getFrom().getUsername()).collect(Collectors.toSet()));
        userPostResponse.setCreationTime(userPost.getCreateDateTime());
        userPostResponse.setAuthorPictureId(userPost.getAuthor().getProfilePicture());
        userPostResponse.setComments(userPost.getComments().stream().map(this::toResponse).collect(Collectors.toSet()));
        return userPostResponse;
    }

    public CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getContent(),
                comment.getAuthor().getUsername(),
                comment.getAuthor().getProfilePicture(),
                comment.getSource().getId(),
                comment.getId(),
                comment.getLikedBy().stream().map(like -> like.getFrom().getUsername()).collect(Collectors.toSet()),
                comment.getCreateDateTime()
        );
    }

    public ChatMessageResponse toResponse(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getContent(),
                this.toMinimalResponse(chatMessage.getSender()),
                chatMessage.getCreateDateTime()
        );
    }


}
