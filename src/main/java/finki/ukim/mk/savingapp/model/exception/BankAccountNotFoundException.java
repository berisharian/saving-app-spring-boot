package finki.ukim.mk.savingapp.model.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(Long id) {
        super(String.format("Bank account with id %s not found", id));
    }
}
