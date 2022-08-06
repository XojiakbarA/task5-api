package com.task5.api.request;

import lombok.Data;

@Data
public class CreateMessageRequest {
    private Long senderID;
    private Long receiverID;
    private Long chatID;
    private String content;
}
