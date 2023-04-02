package com.mateuszcer.socialmediaapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreationRequest {

    @NotBlank
    @Size(max = 255, message = "{validation.post.size.too_long}")
    private String content;
}
