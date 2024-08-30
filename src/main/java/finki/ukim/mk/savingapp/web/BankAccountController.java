package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.User;
import finki.ukim.mk.savingapp.service.BankAccountService;
import finki.ukim.mk.savingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final UserService userService;
    public BankAccountController(BankAccountService bankAccountService, UserService userService) {
        this.bankAccountService = bankAccountService;
        this.userService = userService;
    }


    @GetMapping()
    public String showBankAccounts(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bodyContent", "bank-accounts/list");
        return "master-template";
    }

    @GetMapping("/details/{id}")
    public String showBankAccountDetails(@PathVariable Long id, Model model) {
        BankAccount bankAccount = bankAccountService.findById(id);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("bodyContent", "bank-accounts/details");
        return "master-template";
    }

    @GetMapping("/add")
    public String addBankAccountForm(Model model) {
        model.addAttribute("bodyContent", "bank-accounts/form");
        return "master-template";
    }

    @PostMapping("/add")
    public String addBankAccount(@RequestParam String name,
                                 @RequestParam double balance
                                 ) {
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
                bankAccountService.createBankAccount(name, balance, user.getUsername());
                return "redirect:/bank-accounts";
            }
        }
        return "redirect:/bank-accounts";
    }

    @GetMapping("/edit/{id}")
    public String editBankAccountForm(@PathVariable Long id, Model model) {
        BankAccount bankAccount = bankAccountService.findById(id);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("bodyContent", "bank-accounts/form");
        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editBankAccount(@PathVariable Long id,
                                  @RequestParam String name,
                                  @RequestParam double balance) {
        bankAccountService.updateBankAccount(id, name, balance);
        return "redirect:/bank-accounts";
    }

    @PostMapping("/delete/{id}")
    public String deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return "redirect:/bank-accounts";
    }
}
