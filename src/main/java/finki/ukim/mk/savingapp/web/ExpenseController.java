package finki.ukim.mk.savingapp.web;

import finki.ukim.mk.savingapp.model.Expense;
import finki.ukim.mk.savingapp.model.BankAccount;
import finki.ukim.mk.savingapp.service.ExpenseService;
import finki.ukim.mk.savingapp.service.BankAccountService;
import finki.ukim.mk.savingapp.model.enumeration.ExpenseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final BankAccountService bankAccountService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, BankAccountService bankAccountService) {
        this.expenseService = expenseService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping()
    public String showExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        model.addAttribute("bodyContent", "expenses/list");
        return "master-template";
    }

    @GetMapping("/details/{id}")
    public String showExpenseDetails(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        model.addAttribute("expense", expense);
        model.addAttribute("bodyContent", "expenses/details");
        return "master-template";
    }

    @GetMapping("/add")
    public String addExpenseForm(Model model) {
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bodyContent", "expenses/form");
        return "master-template";
    }

    @PostMapping("/add")
    public String addExpense(@RequestParam String name,
                             @RequestParam Double amount,
                             @RequestParam ExpenseCategory category,
                             @RequestParam LocalDate date,
                             @RequestParam String paymentMethod,
                             @RequestParam Long bankId) {
        expenseService.create(name, amount, category, date, paymentMethod, bankId);
        return "redirect:/expenses";
    }

    @GetMapping("/edit/{id}")
    public String editExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        List<BankAccount> bankAccounts = bankAccountService.findAll();
        model.addAttribute("categories", ExpenseCategory.values());
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("expense", expense);
        model.addAttribute("bodyContent", "expenses/form");
        return "master-template";
    }

    @PostMapping("/edit/{id}")
    public String editExpense(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam Double amount,
                              @RequestParam ExpenseCategory category,
                              @RequestParam LocalDate date,
                              @RequestParam String paymentMethod,
                              @RequestParam Long bankId) {
        expenseService.update(id, name, amount, category, date, paymentMethod, bankId);
        return "redirect:/expenses";
    }

    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteById(id);
        return "redirect:/expenses";
    }
}
