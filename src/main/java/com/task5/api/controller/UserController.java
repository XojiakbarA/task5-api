package com.task5.api.controller;

import com.task5.api.entity.Chat;
import com.task5.api.entity.User;
import com.task5.api.service.ChatService;
import com.task5.api.service.UserService;
import com.task5.api.util.MappingUtils;
import com.task5.api.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MappingUtils mappingUtils;

    @GetMapping("/users/{id}/{name}")
    public ResponseEntity<Response> show(@PathVariable Long id, @PathVariable String name) {
        Response response = new Response();
        try {
            User user = userService.show(id, name);
            response.setContent(user);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/users/{senderID}/chats")
    public ResponseEntity<Response> userChats(@PathVariable Long senderID, @RequestParam("receiverID") Long receiverID) {
        Response response = new Response();
        List<Chat> chats = chatService.findChatsByUserNames(senderID, receiverID);
        response.setContent(chats);
        response.setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Response> index(@RequestParam("senderID") Long senderID) {
        Response response = new Response();
        List<User> users = userService.findAll();
        response.setContent(mappingUtils.mapToUserDTOList(users, senderID));
        response.setMessage("OK");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @MessageMapping("/users/create")
    @SendTo("/chatroom/create")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @MessageMapping("/users/delete")
    @SendTo("/chatroom/delete")
    public Long destroy(@RequestBody Long id) {
        userService.destroy(id);
        return id;
    }
}
