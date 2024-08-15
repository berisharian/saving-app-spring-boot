package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.exception.UserNotFoundException;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(String name, String surname, String email, String password, String phone, LocalDate birthDate) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setBirthDate(birthDate);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, String name, String surname, String email, String password, String phone, LocalDate birthDate) {
        User user = findById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setBirthDate(birthDate);

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
}
