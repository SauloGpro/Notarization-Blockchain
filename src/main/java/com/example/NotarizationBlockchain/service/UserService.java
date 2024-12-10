package com.example.NotarizationBlockchain.service;

import com.example.NotarizationBlockchain.model.User;

public interface UserService {
    User register(User user);

    User loadUserByUsername(String username);
}

