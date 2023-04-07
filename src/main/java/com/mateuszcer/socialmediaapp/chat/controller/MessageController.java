package com.mateuszcer.socialmediaapp.chat.controller;


import com.mateuszcer.socialmediaapp.chat.payload.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class MessageController {


    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat.message")
    public void broadcastNews(@Payload ChatMessage chatMessage, Principal principal) {
        this.simpMessagingTemplate.convertAndSendToUser(chatMessage.getUsername(), "/queue/chat.message",
                new ChatMessage(chatMessage.getMessage(), principal.getName()));

    }
}
