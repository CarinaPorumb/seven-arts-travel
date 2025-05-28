package ro.sevenartstravel.service.email;

import ro.sevenartstravel.entity.User;

public interface EmailBodyService {

    String emailBody (User user);

    String emailBodyScheduler(User user);
}