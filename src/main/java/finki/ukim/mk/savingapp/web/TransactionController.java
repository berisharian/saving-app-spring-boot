package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.model.Transaction;
import finki.ukim.mk.savingapp.model.enumeration.TransactionType;
import finki.ukim.mk.savingapp.service.BankAccountService;
import finki.ukim.mk.savingapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TransactionController(TransactionService transactionService, BankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String showTransactions(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   Model model) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Transaction> transactions = transactionService.getAllTranscations(0, 10);
        model.addAttribute("transactions", transactions);
        model.addAttribute("bodyContent", "transactions/list");
        return "master-template";
    }

    @GetMapping("/details/{id}")
    public String showTransactionDetails(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("bodyContent", "transactions/details");
        return "master-template";
    }

    @GetMapping("/add")
    public String addTransactionForm(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("transactionTypes", TransactionType.values()); // Assuming you have an enumeration for TransactionType
        model.addAttribute("bodyContent", "transactions/form");
        return "master-template";
    }

    @PostMapping("/add")
    public String addTransaction(@RequestParam String transactionTitle,
                                 @RequestParam LocalDateTime dateTime,
                                 @RequestParam double amount,
                                 @RequestParam TransactionType transactionType,
                                 @RequestParam String description,
                                 @RequestParam Long senderId,
                                 @RequestParam Long receiverId) {
        BankAccount sender = bankAccountService.findById(senderId);
        BankAccount receiver = bankAccountService.findById(receiverId);
        transactionService.create(transactionTitle, dateTime, amount, transactionType, description, sender, receiver);
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String editTransactionForm(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getById(id);
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        model.addAttribute("transaction", transaction);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("bodyContent", "transactions/form");
        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editTransaction(@PathVariable Long id,
                                  @RequestParam String transactionTitle,
                                  @RequestParam LocalDateTime dateTime,
                                  @RequestParam double amount,
                                  @RequestParam TransactionType transactionType,
                                  @RequestParam String description,
                                  @RequestParam Long senderId,
                                  @RequestParam Long receiverId) {
        BankAccount sender = bankAccountService.findById(senderId);
        BankAccount receiver = bankAccountService.findById(receiverId);
        transactionService.update(id, transactionTitle, dateTime, amount, transactionType, description, sender, receiver);
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return "redirect:/transactions";
    }
}
