package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.Transaction;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.TransactionType;
import finki.ukim.mk.savingapp.model.exception.TransactionNotFoundException;
import finki.ukim.mk.savingapp.repository.TransactionRepository;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

//    @Override
//    public Page<Transaction> findAll(Long userId, Pageable pageable) {
//        User user = userRepository.getReferenceById(userId);
//        return transactionRepository.findByUser(user, pageable);
//    }

    @Override
    public Page<Transaction> getAllTranscations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.transactionRepository.findAll(pageable);
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(()-> new TransactionNotFoundException(id));
    }

    @Override
    public Transaction create(String transactionTitle, LocalDateTime dateTime, Double amount, TransactionType transactionType, String description, BankAccount sender, BankAccount receiver) {
        Transaction transaction = new Transaction(transactionTitle, dateTime, amount, transactionType, description, sender, receiver);
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = getById(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction update(Long id, String transactionTitle, LocalDateTime dateTime, Double amount, TransactionType transactionType, String description, BankAccount sender, BankAccount receiver) {
        Transaction transaction = getById(id);
        transaction.setTransactionTitle(transactionTitle);
        transaction.setDateTime(dateTime);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setDescription(description);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        return transactionRepository.save(transaction);
    }
}
