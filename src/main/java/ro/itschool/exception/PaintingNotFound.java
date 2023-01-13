package ro.itschool.exception;

public class PaintingNotFound extends Exception{
    public PaintingNotFound() {
        super("Painting not found!");
    }
}