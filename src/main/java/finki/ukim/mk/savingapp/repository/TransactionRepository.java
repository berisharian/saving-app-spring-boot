package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.Transaction;
import finki.ukim.mk.savingapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    Page<Transaction> findByUser(User user, Pageable pageable);
}
