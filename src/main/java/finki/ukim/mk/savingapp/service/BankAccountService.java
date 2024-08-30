package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> findAll();

    BankAccount findById(Long id);

    void deleteBankAccount(Long id);

    BankAccount createBankAccount(String name, double balance, String userId);

    BankAccount updateBankAccount(Long id, String name, double balance);

    void updateAmount(BankAccount bankAccount, Double amount);

    BankAccount getBankAccount(Long id);

    BankAccount findByUser(User user);

}
