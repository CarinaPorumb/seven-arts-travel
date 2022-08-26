package ro.itschool.exception;

public class UserNotFound extends Exception {

    public UserNotFound() {
        super("User not found!");
    }
}
