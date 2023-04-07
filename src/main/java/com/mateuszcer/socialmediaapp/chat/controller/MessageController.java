package com.mateuszcer.socialmediaapp.chat.controller;


import com.mateuszcer.socialmediaapp.chat.model.ChatMessage;
import com.mateuszcer.socialmediaapp.chat.payload.ChatMessageRequest;
import com.mateuszcer.socialmediaapp.chat.payload.ChatMessageResponse;
import com.mateuszcer.socialmediaapp.chat.service.ChatMessageService;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.payload.Mapper;
import com.mateuszcer.socialmediaapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class MessageController {


    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserService userService;

    private final ChatMessageService chatMessageService;

    private final Mapper mapper;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, UserService userService, ChatMessageService chatMessageService, Mapper mapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
        this.chatMessageService = chatMessageService;
        this.mapper = mapper;
    }

    @MessageMapping("/chat.message")
    public void broadcastNews(@Payload ChatMessageRequest chatMessageRequest, Principal principal) {
        Optional<User> userOpt = userService.findByUsername(chatMessageRequest.getReceiverUsername());

        if(userOpt.isEmpty()) {
            return;
        }

        User receiver = userOpt.get();
        User sender = userService.findByUsername(principal.getName()).get();
        ChatMessage chatMessage = chatMessageService.createMessage(sender, receiver, chatMessageRequest.getMessage());

        this.simpMessagingTemplate.convertAndSendToUser(chatMessageRequest.getReceiverUsername(), "/queue/chat.message",
                mapper.toResponse(chatMessage));
        this.simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/chat.message",
                mapper.toResponse(chatMessage));
    }

    @GetMapping("/chat/messages/{username}")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(@PathVariable String username, Principal principal) {
        Optional<User> userOpt = userService.findByUsername(username);

        if(userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User receiver = userOpt.get();
        User sender = userService.findByUsername(principal.getName()).get();
        List<ChatMessage> chatMessages = Stream.concat(
                chatMessageService.findAllBySenderAndReceiver(sender, receiver).stream(),
                chatMessageService.findAllBySenderAndReceiver(receiver, sender).stream()).toList();

        return ResponseEntity.ok(chatMessages.stream().map(mapper::toResponse).collect(Collectors.toList()));

    }
}
