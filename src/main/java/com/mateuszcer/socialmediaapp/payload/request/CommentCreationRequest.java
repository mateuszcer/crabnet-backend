package com.mateuszcer.socialmediaapp.payload.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentCreationRequest {
    @NotNull
    private Long userPostId;

    @NotEmpty
    private String content;

}
