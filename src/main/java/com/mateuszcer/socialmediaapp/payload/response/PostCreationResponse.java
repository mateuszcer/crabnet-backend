package com.mateuszcer.socialmediaapp.payload.response;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreationResponse {
    String content;
    String authorUsername;
    LocalDateTime creationTime;
}
