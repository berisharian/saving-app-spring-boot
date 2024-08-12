package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
