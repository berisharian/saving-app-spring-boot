package finki.ukim.mk.savingapp.model.dto;


import lombok.Data;

@Data
public class BalanceDTO {
    private String name;
    private double balance;

    public BalanceDTO(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
}