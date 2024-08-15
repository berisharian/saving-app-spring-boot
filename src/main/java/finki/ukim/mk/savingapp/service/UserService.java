package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<User> listAll();

    User findById(Long id);

    User findByEmail(String email);

    User createUser(String name, String surname, String email, String password, String phone, LocalDate birthDate);

    User updateUser(Long id, String name, String surname, String email, String password, String phone, LocalDate birthDate);

    void delete(Long id);
}
