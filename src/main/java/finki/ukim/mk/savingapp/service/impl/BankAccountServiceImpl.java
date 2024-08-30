package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.exception.BankAccountNotFoundException;
import finki.ukim.mk.savingapp.model.exception.UserNotFoundException;
import finki.ukim.mk.savingapp.repository.BankAccountRepository;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }

    @Override
    @Transactional
    public BankAccount createBankAccount(String name, double balance, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
//
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(name);
        bankAccount.setBalance(balance);
        bankAccount.setUser(user);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional
    public BankAccount updateBankAccount(Long id, String name, double balance) {
        BankAccount bankAccount = this.findById(id);

        bankAccount.setName(name);
        bankAccount.setBalance(balance);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    @Transactional
    public void deleteBankAccount(Long id) {
        BankAccount bankAccount = this.findById(id);
        bankAccountRepository.delete(bankAccount);
    }

    @Override
    public void updateAmount(BankAccount bankAccount, Double amount) {
        BankAccount currentBankAccount = this.findById(bankAccount.getId());
        double balance = currentBankAccount.getBalance();
        currentBankAccount.setBalance(balance-amount);
    }
    @Override
    public BankAccount getBankAccount(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }
    @Override
    public BankAccount findByUser(User user) {
        return bankAccountRepository.findByUser(user);
    }
}
