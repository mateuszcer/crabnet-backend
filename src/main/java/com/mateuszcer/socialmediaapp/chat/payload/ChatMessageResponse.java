package com.mateuszcer.socialmediaapp.chat.payload;

import com.mateuszcer.socialmediaapp.payload.response.MinimalUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageResponse {
    private String message;
    private MinimalUserResponse sender;
    private LocalDateTime creationTime;
}