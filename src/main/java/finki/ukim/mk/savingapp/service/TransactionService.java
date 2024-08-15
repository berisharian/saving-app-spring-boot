package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.Transaction;
import finki.ukim.mk.savingapp.model.enumeration.TransactionType;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


public interface TransactionService {
//    Page<Transaction> findAll(Long userId, Pageable pageable);

    Page<Transaction> getAllTranscations(int page, int size);

    Transaction getById(Long id);

    Transaction create(String transactionTitle, LocalDateTime dateTime, Double amount, TransactionType transactionType, String description, BankAccount sender, BankAccount receiver);

    void delete(Long id);

    Transaction update(Long id, String transactionTitle, LocalDateTime dateTime, Double amount, TransactionType transactionType, String description, BankAccount sender, BankAccount receiver);
}
