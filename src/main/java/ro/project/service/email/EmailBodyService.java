package ro.project.service.email;

import ro.project.entity.User;

public interface EmailBodyService {

    String emailBody (User user);

    String emailBodyScheduler(User user);
}