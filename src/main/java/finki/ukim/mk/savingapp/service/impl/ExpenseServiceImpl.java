package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.Expense;
import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.enumeration.ExpenseCategory;
import finki.ukim.mk.savingapp.repository.ExpenseRepository;
import finki.ukim.mk.savingapp.service.ExpenseService;
import finki.ukim.mk.savingapp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, BankAccountService bankAccountService) {
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

    @Override
    public Expense create(String name, Double amount, ExpenseCategory category, LocalDate date, String paymentMethod, Long bankId) {
        BankAccount bankAccount = bankAccountService.findById(bankId);

        Expense expense = new Expense();
        expense.setName(name);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(date);
        expense.setPaymentMethod(paymentMethod);
        expense.setBank(bankAccount);

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
}
