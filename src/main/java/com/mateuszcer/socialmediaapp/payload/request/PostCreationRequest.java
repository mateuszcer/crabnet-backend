package com.mateuszcer.socialmediaapp.payload.request;

import lombok.*;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreationRequest {
    private String content;
}
