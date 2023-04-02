package com.mateuszcer.socialmediaapp.payload.response;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentResponse {
    private String content;

    private String authorUsername;

    private Integer authorPictureId;

    private Long sourceId;

    private LocalDateTime creationTime;
}
