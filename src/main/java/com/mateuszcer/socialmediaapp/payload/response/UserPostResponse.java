package com.mateuszcer.socialmediaapp.payload.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserPostResponse {
    private String authorUsername;

    private String content;

    private Long id;

    private Set<String> likedBy;

    private LocalDateTime creationTime;

    private Integer authorPictureId;
}
