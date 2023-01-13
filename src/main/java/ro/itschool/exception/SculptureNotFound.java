package ro.itschool.exception;

public class SculptureNotFound extends Exception {
    public SculptureNotFound() {
        super("Sculpture not found!");
    }
}