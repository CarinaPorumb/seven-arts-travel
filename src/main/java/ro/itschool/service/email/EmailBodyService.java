package ro.itschool.service.email;

import ro.itschool.entity.User;

public interface EmailBodyService {

    String emailBody (User user);

    String emailBodyScheduler(User user);

}
