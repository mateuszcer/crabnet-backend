package com.mateuszcer.socialmediaapp.chat.payload;

import com.mateuszcer.socialmediaapp.payload.response.MinimalUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessageRequest {
    private String message;
    private String receiverUsername;

}