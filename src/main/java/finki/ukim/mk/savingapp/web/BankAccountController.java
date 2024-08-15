package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
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
                                 @RequestParam double balance,
                                 @RequestParam Long userId) {
        bankAccountService.createBankAccount(name, balance, userId);
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
