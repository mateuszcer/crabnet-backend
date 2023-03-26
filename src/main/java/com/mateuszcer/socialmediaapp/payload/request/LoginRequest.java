package com.mateuszcer.socialmediaapp.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequest {
    private String email;

    private String password;
}
