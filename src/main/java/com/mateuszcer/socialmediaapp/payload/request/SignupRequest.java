package com.mateuszcer.socialmediaapp.payload.request;

import com.mateuszcer.socialmediaapp.validators.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignupRequest {

    @ValidEmail
    private String email;

    @NotBlank(message = "Firstname must not be empty")
    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 15, message = "{validation.name.size.too_long}")
    private String firstname;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 15, message = "{validation.name.size.too_long}")
    @NotBlank(message = "Lastname must not be empty")
    private String lastname;

    @NotEmpty(message = "Gender must not be empty")
    private String gender;

    @Size(min = 3, message = "{validation.username.size.too_short}")
    @Size(max = 20, message = "{validation.username.size.too_long}")
    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Picture id must be provided")
    private Integer pictureId;

    public void setUsername(String value){
        this.username = value.replaceAll(" ", "");
    }

    public String getUsername(){
        return this.username;
    }

    public void setFirstname(String value) {
        this.firstname = StringUtils.capitalize(value);
    }
    public String getFirstname() {
        return firstname;
    }


    public void setLastname(String value) {
        this.lastname = StringUtils.capitalize(value);
    }
    public String getLastname() {
        return lastname;
    }


}
