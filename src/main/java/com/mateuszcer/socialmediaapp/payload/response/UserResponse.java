package com.mateuszcer.socialmediaapp.payload.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private String username;
    private String firstname;
    private String lastname;
    private Set<UserPostResponse> posts;

    private Set<String> following;
    private Set<String> followers;
}
