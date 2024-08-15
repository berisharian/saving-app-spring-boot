package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> findAll();

    BankAccount findById(Long id);

    void deleteBankAccount(Long id);

    BankAccount createBankAccount(String name, double balance, Long userId);

    BankAccount updateBankAccount(Long id, String name, double balance);

}
