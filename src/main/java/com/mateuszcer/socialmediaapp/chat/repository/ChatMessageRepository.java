package com.mateuszcer.socialmediaapp.chat.repository;


import com.mateuszcer.socialmediaapp.chat.model.ChatMessage;
import com.mateuszcer.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllBySenderAndReceiver(User sender, User receiver);


}
