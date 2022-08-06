package com.task5.api.service.impl;

import com.task5.api.entity.User;
import com.task5.api.repository.UserRepository;
import com.task5.api.service.UserService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedUserFilter");
        filter.setParameter("isDeleted", false);
        List<User> users = userRepository.findAll();
        session.disableFilter("deletedUserFilter");
        return users;
    }

    @Override
    public User show(Long id, String name) {
            User user = userRepository.findByIdAndName(id, name);
        if (user == null) {
            throw new EntityNotFoundException("User with id: " + id + " not found.");
        }
        return user;
    }

    @Override
    public User login(User user) {
        User findedUser = userRepository.findByName(user.getName());
        if (findedUser == null) {
            return userRepository.save(user);
        }
        findedUser.setIsDeleted(false);
        return userRepository.save(findedUser);
    }

    @Override
    public void destroy(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.setIsDeleted(true);
        userRepository.save(user);
    }

}
