package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User owner;

    private Double currentAmount;

    private Double targetAmount;

    private Double savingAmount;

    @Enumerated(EnumType.STRING)
    private SavingsPeriod savingsPeriod;

    private String description;

    private LocalDateTime createdAt;

    private BudgetStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dueDate;

    private LocalDateTime updatedAt;


}
