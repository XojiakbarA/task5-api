package com.task5.api.request;

import lombok.Data;

@Data
public class CreateChatRequest {
    private Long senderID;
    private Long receiverID;
    private String subject;
    private String content;
}
