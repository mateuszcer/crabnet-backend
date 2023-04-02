package com.mateuszcer.socialmediaapp.payload.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "{validation.comment.size.too_short}")
    @Size(max = 100, message = "{validation.comment.size.too_long}")
    private String content;

}
