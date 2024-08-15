package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import finki.ukim.mk.savingapp.model.enumeration.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateTime;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String description;

    @ManyToOne
    private BankAccount sender;

    @ManyToOne
    private BankAccount receiver;

    public Transaction(String transactionTitle, LocalDateTime dateTime, Double amount, TransactionType transactionType, String description, BankAccount sender, BankAccount receiver) {
        this.transactionTitle = transactionTitle;
        this.dateTime = dateTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.sender = sender;
        this.receiver = receiver;
    }
}
