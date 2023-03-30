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

    @NotEmpty(message = "Firstname must not be empty")
    private String firstname;

    @NotEmpty(message = "Lastname must not be empty")
    private String lastname;

    @NotEmpty(message = "Gender must not be empty")
    private String gender;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    private String password;

    @NotEmpty(message = "Picture id must be provided")
    private Integer pictureId;

}
