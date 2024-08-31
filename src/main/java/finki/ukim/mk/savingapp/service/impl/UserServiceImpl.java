package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.exception.UserNotFoundException;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String name, String surname, String password, String phone) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Bad Credentials");
        }

        if(this.userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(
                username,
                name,
                surname,
                passwordEncoder.encode(password),
                phone,
                null
        );


        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public void updateUser(String username, User updatedUser) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setPhone(updatedUser.getPhone());
        user.setBirthDate(updatedUser.getBirthDate());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

}

