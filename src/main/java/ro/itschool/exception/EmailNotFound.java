package ro.itschool.exception;

public class EmailNotFound extends Exception{
    public EmailNotFound() {
        super("Email not found!");
    }
}