package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

