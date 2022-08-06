package com.task5.api.service.impl;

import com.task5.api.entity.Chat;
import com.task5.api.entity.Message;
import com.task5.api.entity.User;
import com.task5.api.repository.ChatRepository;
import com.task5.api.repository.MessageRepository;
import com.task5.api.repository.UserRepository;
import com.task5.api.request.CreateMessageRequest;
import com.task5.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message store(CreateMessageRequest request) {
        User receiver = userRepository.findById(request.getReceiverID()).orElse(null);
        User sender = userRepository.findById(request.getSenderID()).orElse(null);
        Chat chat = chatRepository.findById(request.getChatID()).orElse(null);

        assert receiver != null;
        assert sender != null;

        Message message = new Message();
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setChat(chat);
        message.setContent(request.getContent());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findMessagesByChatId(Long id) {
        return messageRepository.findMessagesByChatId(id);
    }
}
