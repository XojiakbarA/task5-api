package com.task5.api.service;

import com.task5.api.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User show(Long id, String name);

    User login(User user);

    void destroy(Long id);

}
