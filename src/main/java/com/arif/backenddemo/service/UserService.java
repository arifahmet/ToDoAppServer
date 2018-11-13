package com.arif.backenddemo.service;

import com.arif.backenddemo.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserByUsername(String username);
    User createUser(User user);
}
