package com.task5.api.controller;

import com.task5.api.entity.Message;
import com.task5.api.request.CreateMessageRequest;
import com.task5.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/messages/private")
    public Message store(@RequestBody CreateMessageRequest request) {
        Message message = messageService.store(request);
        String receiverID = String.valueOf(request.getReceiverID());
        String senderID = String.valueOf(request.getSenderID());
        simpMessagingTemplate.convertAndSendToUser(receiverID, "/message", message); //user/id/private
        simpMessagingTemplate.convertAndSendToUser(senderID, "/message", message); //user/id/private
        return message;
    }
}
