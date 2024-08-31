package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.Expense;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.ExpenseCategory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> findAll();
    Optional<Expense> findById(Long id);
    Expense save(Expense expense);
    void deleteById(Long id);
//    Expense create(String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, Long bankId);
     Expense create(String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, BankAccount bankAccount);


    Expense update(Long id, String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, Long bankId);
    public Map<String, Double> getExpensesAsMap();
    List<Expense> findByUser(User user);
}
