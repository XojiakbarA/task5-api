package com.task5.api.service;

import com.task5.api.entity.Message;
import com.task5.api.request.CreateMessageRequest;

import java.util.List;

public interface MessageService {

    Message store(CreateMessageRequest request);
    List<Message> findMessagesByChatId(Long id);

}
