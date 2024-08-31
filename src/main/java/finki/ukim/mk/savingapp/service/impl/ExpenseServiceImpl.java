package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.Expense;
import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.ExpenseCategory;
import finki.ukim.mk.savingapp.repository.ExpenseRepository;
import finki.ukim.mk.savingapp.service.ExpenseService;
import finki.ukim.mk.savingapp.service.BankAccountService;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final UserService userService;
    private final ExpenseRepository expenseRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public ExpenseServiceImpl(UserService userService, ExpenseRepository expenseRepository, BankAccountService bankAccountService) {
        this.userService = userService;
        this.expenseRepository = expenseRepository;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

//    @Override
//    public Expense create(String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, Long bankId) {
//        BankAccount bankAccount = bankAccountService.findById(bankId);
//
//        Expense expense = new Expense();
//        expense.setName(name);
//        expense.setAmount(amount);
//        expense.setCategory(category);
//        expense.setDate(date);
//        expense.setPaymentMethod(paymentMethod);
////        expense.setBank(bankAccount);
//
//        bankAccountService.updateAmount(bankAccount, amount);
//
//        return expenseRepository.save(expense);
//    }

    @Override
    public Expense create(String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, BankAccount bankAccount) {
        Expense expense = new Expense();
        expense.setName(name);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(date);
        expense.setPaymentMethod(paymentMethod);
        expense.setBank(bankAccount);

        bankAccountService.updateAmount(bankAccount, amount);

        return expenseRepository.save(expense);
    }


    @Override
    public Expense update(Long id, String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, Long bankId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            BankAccount bankAccount = bankAccountService.findById(bankId);

            existingExpense.setName(name);
            existingExpense.setAmount(amount);
            existingExpense.setCategory(category);
            existingExpense.setDate(date);
            existingExpense.setPaymentMethod(paymentMethod);
            existingExpense.setBank(bankAccount);

            return expenseRepository.save(existingExpense);
        } else {
            throw new IllegalArgumentException("Expense not found");
        }
    }

//    @Override
//    public Map<String, Double> getExpensesAsMap() {
//        List<Expense> expenses = expenseRepository.findAll();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        return expenses.stream()
//                .collect(Collectors.toMap(
//                        expense -> expense.getDate().format(formatter), // Convert date to string for JSON compatibility
//                        Expense::getAmount,
//                        Double::sum // If multiple expenses exist on the same date, sum them
//                ));
//    }

    @Override
    public Map<String, Double> getExpensesAsMap() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null) {
            User user = (User) userService.loadUserByUsername(username);
            List<Expense> expenses = expenseRepository.findByBank_User(user);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return expenses.stream()
                    .collect(Collectors.toMap(
                            expense -> expense.getDate().format(formatter),
                            Expense::getAmount,
                            Double::sum
                    ));
        }

        return Collections.emptyMap();
    }

    @Override
    public List<Expense> findByUser(User user) {
        return expenseRepository.findByBank_User(user);
    }

}
