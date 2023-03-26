package com.mateuszcer.socialmediaapp.payload.request;

import com.mateuszcer.socialmediaapp.validators.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignupRequest {

    @ValidEmail
    private String email;

    private String firstname;

    private String lastname;

    private String gender;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    private String password;

}
