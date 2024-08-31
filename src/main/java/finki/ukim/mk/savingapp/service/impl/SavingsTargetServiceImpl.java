package finki.ukim.mk.savingapp.service.impl;

import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;
import finki.ukim.mk.savingapp.model.exception.UserNotFoundException;
import finki.ukim.mk.savingapp.repository.SavingsTargetRepository;
import finki.ukim.mk.savingapp.repository.UserRepository;
import finki.ukim.mk.savingapp.service.SavingsTargetService;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SavingsTargetServiceImpl implements SavingsTargetService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SavingsTargetRepository savingsTargetRepository;

    public SavingsTargetServiceImpl(UserService userService, UserRepository userRepository, SavingsTargetRepository savingsTargetRepository) {
        this.userService = userService;
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
        savingsTarget.setStatus(savingsTarget.getStatusBudget());

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

//    @Override
//    public Map<BudgetStatus, Long> countBudgetStatuses() {
//        return savingsTargetRepository.findAll().stream()
//                .collect(Collectors.groupingBy(SavingsTarget::getStatus, Collectors.counting()));
//    }

    @Override
    public Map<BudgetStatus, Long> countBudgetStatuses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null) {
            User user = (User) userService.loadUserByUsername(username);
            return savingsTargetRepository.findByOwner(user).stream()
                    .collect(Collectors.groupingBy(SavingsTarget::getStatus, Collectors.counting()));
        }

        return Collections.emptyMap();
    }

    @Override
    public List<SavingsTarget> findAllByUser(User user) {
        return savingsTargetRepository.findByOwner(user);
    }

    @Override
    public List<SavingsTarget> findByUser(User user) {
        return savingsTargetRepository.findByOwner(user);
    }

    public void deleteById(Long id) {
        savingsTargetRepository.deleteById(id);
    }

    public List<SavingsTarget> findAll() {
        return savingsTargetRepository.findAll();
    }
}
