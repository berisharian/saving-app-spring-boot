package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import finki.ukim.mk.savingapp.model.enumeration.BudgetStatus;
import finki.ukim.mk.savingapp.model.enumeration.SavingsPeriod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.lang.annotation.Target;
import java.time.Duration;
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

    @Enumerated(EnumType.STRING)
    private BudgetStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate dueDate;

    private LocalDateTime updatedAt;


    public BudgetStatus getStatusBudget() {
        if (currentAmount > targetAmount){
            status = BudgetStatus.EXCEEDED;
        } else if (currentAmount < targetAmount){
            status = BudgetStatus.ACTIVE;
        } else
            status =  BudgetStatus.COMPLETED;
        return status;
    }


//    public double getPeriodicalAmount(){
//        LocalDateTime current = LocalDateTime.now();
//
//        Duration duration = Duration.between(createdAt, current);
//
//        long days = duration.toDays();
//        long hours = duration.toHours() % 24;
//        long minutes = duration.toMinutes() % 60;
//        long seconds = duration.getSeconds() % 60;
//
//        if (SavingsPeriod.MINUTE.equals(savingsPeriod)) {
//            return currentAmount + savingAmount * minutes;
//        }
//        if (SavingsPeriod.DAILY.equals(savingsPeriod)){
//            return currentAmount + (savingAmount * days);
//        }
//        if (SavingsPeriod.MONTHLY.equals(savingsPeriod)){
//            return currentAmount + (savingAmount * days/30);
//        }
//        return currentAmount;
//    }
    public void getStatusBudgetWithParam(double c) {
        if (c > targetAmount){
            status = BudgetStatus.EXCEEDED;
        } else if (c < targetAmount){
            status = BudgetStatus.ACTIVE;
        } else
            status =  BudgetStatus.COMPLETED;
    }

    public double getPeriodicalAmount(){
        LocalDateTime current = LocalDateTime.now();

        Duration duration = Duration.between(createdAt, current);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        if (SavingsPeriod.MINUTE.equals(savingsPeriod)) {
            double result = currentAmount + savingAmount * minutes;
            getStatusBudgetWithParam(result);
            return result;
        } else
        if (SavingsPeriod.DAILY.equals(savingsPeriod)){
            double result = currentAmount + (savingAmount * days);
            getStatusBudgetWithParam(result);
            return result;
        } else {
            double result = currentAmount + (savingAmount * days/30);
            getStatusBudgetWithParam(result);
            return result;
        }
    }





//    public void recalculateAndUpdateStatus() {
//        // Calculate the new amount based on the period
//        double updatedAmount = getPeriodicalAmount();
//
//        // Update currentAmount with the recalculated amount
//        this.currentAmount = updatedAmount;
//
//        // Update the status based on the new amount
//        this.status = getStatusBudget();
//    }




}
