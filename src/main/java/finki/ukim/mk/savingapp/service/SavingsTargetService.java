package finki.ukim.mk.savingapp.service;

import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SavingsTargetService {

    List<SavingsTarget> findAll();

    Optional<SavingsTarget> findById(Long id);

    void deleteById(Long id);

    SavingsTarget create(String name, Double currentAmount, Double targetAmount, Double savingAmount,
                         SavingsPeriod savingsPeriod, String description, LocalDate dueDate, String userId);

    SavingsTarget update(Long id, String name, Double currentAmount, Double targetAmount, Double savingAmount,
                         SavingsPeriod savingsPeriod, String description, LocalDate dueDate);

    public Map<BudgetStatus, Long> countBudgetStatuses();

    public List<SavingsTarget> findAllByUser(User user);

    public List<SavingsTarget> findByUser(User user);
}
