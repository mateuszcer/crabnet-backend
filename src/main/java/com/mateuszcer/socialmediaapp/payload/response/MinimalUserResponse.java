package com.mateuszcer.socialmediaapp.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MinimalUserResponse {
    private String username;
    private Integer pictureId;

    private String firstname;

    private String lastname;
}
