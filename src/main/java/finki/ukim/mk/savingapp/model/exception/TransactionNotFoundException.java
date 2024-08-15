package finki.ukim.mk.savingapp.model.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super(String.format("Transaction with id %d has not been found.", id));
    }
}
