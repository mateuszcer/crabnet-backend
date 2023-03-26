package com.mateuszcer.socialmediaapp.payload.response;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserPostResponse {
    private String authorUsername;

    private String content;

    private Long id;

    private Set<UserResponse> likedBy;
}
