package ro.sevenartstravel.service.email;

public interface EmailSender {
    void sendEmail(String toEmail, String subject, String body);
}