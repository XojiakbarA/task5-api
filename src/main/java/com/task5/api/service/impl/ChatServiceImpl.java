package com.task5.api.service.impl;

import com.task5.api.entity.Chat;
import com.task5.api.entity.Message;
import com.task5.api.entity.User;
import com.task5.api.repository.ChatRepository;
import com.task5.api.repository.MessageRepository;
import com.task5.api.repository.UserRepository;
import com.task5.api.request.CreateChatRequest;
import com.task5.api.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message store(CreateChatRequest request) {
        User receiver = userRepository.findById(request.getReceiverID()).orElse(null);
        User sender = userRepository.findById(request.getSenderID()).orElse(null);

        Chat chat = new Chat();
        chat.setSubject(request.getSubject());
        assert receiver != null;
        assert sender != null;
        List<User> users = new ArrayList<>();
        users.add(receiver);
        users.add(sender);
        chat.setUsers(users);
        Chat createdChat = chatRepository.save(chat);

        Message message = new Message();
        message.setChat(createdChat);
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setContent(request.getContent());
        messageRepository.save(message);

        return message;
    }

    @Override
        public List<Chat> findChatsByUserNames(Long senderID, Long receiverID) {
        return chatRepository.findDistChatsByUsername(senderID, receiverID);
    }
}
