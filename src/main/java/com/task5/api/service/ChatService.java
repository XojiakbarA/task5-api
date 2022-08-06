package com.task5.api.service;

import com.task5.api.entity.Chat;
import com.task5.api.entity.Message;
import com.task5.api.request.CreateChatRequest;

import java.util.List;

public interface ChatService {

    Message store(CreateChatRequest request);

    List<Chat> findChatsByUserNames(Long senderID, Long receiverID);

}
