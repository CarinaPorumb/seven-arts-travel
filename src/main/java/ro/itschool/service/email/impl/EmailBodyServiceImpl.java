package ro.itschool.service.email.impl;

import org.springframework.stereotype.Service;
import ro.itschool.entity.User;
import ro.itschool.service.email.EmailBodyService;

@Service
public class EmailBodyServiceImpl implements EmailBodyService {
    @Override
    public String emailBody(User user) {
        return "Hello," +
                "In order to activate your account please access the following link:\n" +
                "http://localhost:8080/activation/" + user.getRandomToken();
    }

    @Override
    public String emailBodyScheduler(User user) {
        return "Hello," +
                "Discover the latest art destination and get a sneak peek of what's coming soon on: \n" +
                "http://localhost:8080/index/ !";
    }
}