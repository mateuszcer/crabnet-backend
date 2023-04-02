package com.mateuszcer.socialmediaapp.payload.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    private Long id;

    private Set<String> likedBy;

    private LocalDateTime creationTime;
}
