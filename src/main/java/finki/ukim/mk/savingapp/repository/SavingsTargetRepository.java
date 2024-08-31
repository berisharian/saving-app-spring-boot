package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsTargetRepository extends JpaRepository<SavingsTarget, Long> {
    List<SavingsTarget> findByOwner(User owner);
}
