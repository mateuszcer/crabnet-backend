package com.mateuszcer.socialmediaapp.payload;

import com.mateuszcer.socialmediaapp.model.Followers;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.model.UserPost;
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
                user.getFollowing().stream().map(followers -> followers.getTo().getUsername()).collect(Collectors.toSet()),
                user.getFollowers().stream().map(followers -> followers.getFrom().getUsername()).collect(Collectors.toSet())
        );

    }

    public UserPostResponse toResponse(UserPost userPost) {
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setAuthorUsername(userPost.getAuthor().getUsername());
        userPostResponse.setContent(userPost.getContent());
        userPostResponse.setId(userPost.getId());
        userPostResponse.setLikedBy(userPost.getLikedBy().stream().map(this::toResponse).collect(Collectors.toSet()));
        return userPostResponse;
    }


}
