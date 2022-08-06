package com.task5.api.controller;

import com.task5.api.entity.Message;
import com.task5.api.request.CreateChatRequest;
import com.task5.api.service.ChatService;
import com.task5.api.service.MessageService;
import com.task5.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/chats/{id}/messages")
    public ResponseEntity<Response> index(@PathVariable Long id) {
        Response response = new Response();
        List<Message> messages = messageService.findMessagesByChatId(id);
        response.setContent(messages);
        response.setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @MessageMapping("/messages/chat")
    public Message store(@RequestBody CreateChatRequest request) {
        Message message = chatService.store(request);
        String receiverID = String.valueOf(request.getReceiverID());
        String senderID = String.valueOf(request.getSenderID());
        simpMessagingTemplate.convertAndSendToUser(receiverID, "/chatMessage", message); //user/id/private
        simpMessagingTemplate.convertAndSendToUser(senderID, "/chatMessage", message); //user/id/private
        return message;
    }
}
