package com.mateuszcer.socialmediaapp.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreationRequest {

    @NotEmpty
    private String content;
}
