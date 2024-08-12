package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
