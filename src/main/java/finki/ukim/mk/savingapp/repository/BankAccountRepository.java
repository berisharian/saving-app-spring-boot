package finki.ukim.mk.savingapp.repository;

import finki.ukim.mk.savingapp.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
