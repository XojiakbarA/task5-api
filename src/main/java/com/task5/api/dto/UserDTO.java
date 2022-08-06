package com.task5.api.dto;

import com.task5.api.entity.Chat;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private List<Chat> chats;
}
