package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import finki.ukim.mk.savingapp.model.enumeration.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name= "budget_id")
    private Budget budget;

    private String paymentMethod;

    @ManyToOne
    private Bank bank;

}
