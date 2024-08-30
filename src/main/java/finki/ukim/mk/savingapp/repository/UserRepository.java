package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String email);

    Optional<User> findByUsernameAndPassword(String email, String password);


}

