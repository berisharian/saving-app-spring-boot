package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.Expense;
import finki.ukim.mk.savingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByBank_User(User user);
}
