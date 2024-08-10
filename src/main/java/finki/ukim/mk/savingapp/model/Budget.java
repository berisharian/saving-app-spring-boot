package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import finki.ukim.mk.savingapp.model.enumeration.BudgetCategory;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private BudgetCategory category;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

    @Enumerated(EnumType.STRING)
    private BudgetStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    @ManyToOne
    private Bank bank;

    public Double calculateTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public void updateBudgetStatus() {
        double totalExpenses = calculateTotalExpenses();
        if (totalExpenses > amount) {
            this.status = BudgetStatus.EXCEEDED;
        } else if (totalExpenses == amount) {
            this.status = BudgetStatus.COMPLETED;
        } else {
            this.status = BudgetStatus.ACTIVE;
        }
    }



}
