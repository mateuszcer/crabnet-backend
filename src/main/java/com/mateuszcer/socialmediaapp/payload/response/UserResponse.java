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
    private String bio;
    private Integer pictureId;

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) {
            return false;
        }

        UserResponse userResponse = (UserResponse) obj;
        return this.username.equals(userResponse.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
