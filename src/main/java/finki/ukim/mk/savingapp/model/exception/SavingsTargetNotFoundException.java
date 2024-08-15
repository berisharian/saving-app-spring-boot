package finki.ukim.mk.savingapp.model.exception;

public class SavingsTargetNotFoundException extends RuntimeException {
    public SavingsTargetNotFoundException(Long id) {
        super(String.format("Savings target with id '%s' not found", id));
    }
}
