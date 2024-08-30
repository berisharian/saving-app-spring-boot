package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;
import finki.ukim.mk.savingapp.model.exception.UserNotFoundException;
import finki.ukim.mk.savingapp.repository.SavingsTargetRepository;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.SavingsTargetService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SavingsTargetServiceImpl implements SavingsTargetService {

    private final UserRepository userRepository;
    private final SavingsTargetRepository savingsTargetRepository;

    public SavingsTargetServiceImpl(UserRepository userRepository, SavingsTargetRepository savingsTargetRepository) {
        this.userRepository = userRepository;
        this.savingsTargetRepository = savingsTargetRepository;
    }

    public SavingsTarget create(String name, Double currentAmount, Double targetAmount, Double savingAmount,
                                SavingsPeriod savingsPeriod, String description, LocalDate dueDate, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        SavingsTarget savingsTarget = new SavingsTarget();
        savingsTarget.setName(name);
        savingsTarget.setCurrentAmount(currentAmount);
        savingsTarget.setTargetAmount(targetAmount);
        savingsTarget.setSavingAmount(savingAmount);
        savingsTarget.setSavingsPeriod(savingsPeriod);
        savingsTarget.setDescription(description);
        savingsTarget.setCreatedAt(LocalDateTime.now());
        savingsTarget.setDueDate(dueDate);
        savingsTarget.setOwner(user);
//        savingsTarget.setStatus(BudgetStatus.ACTIVE);
        return savingsTargetRepository.save(savingsTarget);
    }

    public Optional<SavingsTarget> findById(Long id) {
        return savingsTargetRepository.findById(id);
    }

    public SavingsTarget update(Long id, String name, Double currentAmount, Double targetAmount, Double savingAmount,
                                SavingsPeriod savingsPeriod, String description, LocalDate dueDate) {
        SavingsTarget savingsTarget = savingsTargetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SavingsTarget not found"));
        savingsTarget.setName(name);
        savingsTarget.setCurrentAmount(currentAmount);
        savingsTarget.setTargetAmount(targetAmount);
        savingsTarget.setSavingAmount(savingAmount);
        savingsTarget.setSavingsPeriod(savingsPeriod);
        savingsTarget.setDescription(description);
        savingsTarget.setDueDate(dueDate);
        savingsTarget.setUpdatedAt(LocalDateTime.now());
        return savingsTargetRepository.save(savingsTarget);
    }

    public void deleteById(Long id) {
        savingsTargetRepository.deleteById(id);
    }

    public List<SavingsTarget> findAll() {
        return savingsTargetRepository.findAll();
    }
}
