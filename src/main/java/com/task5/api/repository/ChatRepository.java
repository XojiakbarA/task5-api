package com.task5.api.repository;

import com.task5.api.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT DISTINCT chats.id, chats.subject, chats.created_at FROM (SELECT * from user_chat where user_id in (:senderID)) t1 INNER JOIN (SELECT * from user_chat where user_id in (:receiverID)) t2 USING(chat_id) INNER JOIN chats ON chats.id = t1.chat_id",
            nativeQuery = true)
    List<Chat> findDistChatsByUsername(@Param("senderID") Long senderID, @Param("receiverID") Long receiverID);

}
