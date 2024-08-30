package finki.ukim.mk.savingapp.model.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super(String.format("User with id '%d' not found", id));
    }
}
