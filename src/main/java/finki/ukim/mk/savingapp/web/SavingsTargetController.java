package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.SavingsTarget;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;
import finki.ukim.mk.savingapp.model.exception.SavingsTargetNotFoundException;
import finki.ukim.mk.savingapp.service.SavingsTargetService;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/savings-targets")
public class SavingsTargetController {
    private final UserService userService;
    private final SavingsTargetService savingsTargetService;

    @Autowired
    public SavingsTargetController(UserService userService, SavingsTargetService savingsTargetService) {
        this.userService = userService;
        this.savingsTargetService = savingsTargetService;
    }

    @GetMapping()
    public String showSavingsTargets(Model model) {
        model.addAttribute("savingsTargets", savingsTargetService.findAll());
        model.addAttribute("bodyContent", "savings-targets/list");
        return "master-template";
    }

    @GetMapping("/details/{id}")
    public String showSavingsTargetDetails(@PathVariable Long id, Model model) {
        SavingsTarget savingsTarget = savingsTargetService.findById(id)
                .orElseThrow(() -> new SavingsTargetNotFoundException(id));
        model.addAttribute("savingsTarget", savingsTarget);
        model.addAttribute("bodyContent", "savings-targets/details");
        return "master-template";
    }

    @GetMapping("/add")
    public String addSavingsTargetForm(Model model) {
        model.addAttribute("savingsPeriods", SavingsPeriod.values());
        model.addAttribute("savingsStatuses", BudgetStatus.values());
        model.addAttribute("bodyContent", "savings-targets/form");
        return "master-template";
    }

    @PostMapping("/add")
    public String addSavingsTarget(@RequestParam String name,
                                   @RequestParam Double currentAmount,
                                   @RequestParam Double targetAmount,
                                   @RequestParam Double savingAmount,
                                   @RequestParam SavingsPeriod savingsPeriod,
                                   @RequestParam String description,
                                   @RequestParam LocalDate dueDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        if (username != null) {
            // Find the user by username (email)
            User user = (User) userService.loadUserByUsername(username);
            if (user != null) {
                savingsTargetService.create(name, currentAmount, targetAmount, savingAmount, savingsPeriod, description, dueDate, user.getUsername());
                return "redirect:/bank-accounts";
            }
        }

        return "redirect:/savings-targets";
    }

    @GetMapping("/edit/{id}")
    public String editSavingsTargetForm(@PathVariable Long id, Model model) {
        SavingsTarget savingsTarget = savingsTargetService.findById(id)
                .orElseThrow(() -> new SavingsTargetNotFoundException(id));
        model.addAttribute("savingsTarget", savingsTarget);
        model.addAttribute("savingsPeriods", SavingsPeriod.values());
        model.addAttribute("savingsStatuses", BudgetStatus.values());
        model.addAttribute("bodyContent", "savings-targets/form");
        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editSavingsTarget(@PathVariable Long id,
                                    @RequestParam String name,
                                    @RequestParam Double currentAmount,
                                    @RequestParam Double targetAmount,
                                    @RequestParam Double savingAmount,
                                    @RequestParam SavingsPeriod savingsPeriod,
                                    @RequestParam String description,
                                    @RequestParam LocalDate dueDate) {
        savingsTargetService.update(id, name, currentAmount, targetAmount, savingAmount, savingsPeriod, description, dueDate);
        return "redirect:/savings-targets";
    }

    @PostMapping("/delete/{id}")
    public String deleteSavingsTarget(@PathVariable Long id) {
        savingsTargetService.deleteById(id);
        return "redirect:/savings-targets";
    }
}
