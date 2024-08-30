package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.User;

import java.util.List;

public interface AuthService {
    User login(String username, String password);

    List<User> findAll();
}

