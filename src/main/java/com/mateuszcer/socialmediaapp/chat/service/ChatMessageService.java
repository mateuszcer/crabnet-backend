package com.mateuszcer.socialmediaapp.chat.service;

import com.mateuszcer.socialmediaapp.chat.model.ChatMessage;
import com.mateuszcer.socialmediaapp.chat.repository.ChatMessageRepository;
import com.mateuszcer.socialmediaapp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessage> findAllBySenderAndReceiver(User sender, User receiver) {
        return chatMessageRepository.findAllBySenderAndReceiver(sender, receiver);
    }


    public ChatMessage createMessage(User sender, User receiver, String content) {
        return chatMessageRepository.save(new ChatMessage(content, sender, receiver));
    }
}
