package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String name, String surname, String phone);
}

