package com.task5.api.util;

import com.task5.api.dto.UserDTO;
import com.task5.api.entity.Chat;
import com.task5.api.entity.User;
import com.task5.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MappingUtils {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> mapToUserDTOList(List<User> users, Long senderID) {
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());

            User sender = userRepository.findById(senderID).orElse(null);
            List<Chat> chats = user.getChats().stream().filter(chat -> chat.getUsers().contains(sender)).collect(Collectors.toList());

            dto.setChats(chats);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
