package ro.sevenartstravel.service.email.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.sevenartstravel.entity.User;
import ro.sevenartstravel.service.email.EmailBodyService;

@Slf4j
@Service
public class EmailBodyServiceImpl implements EmailBodyService {
    @Override
    public String emailBody(User user) {
        log.info("Generating activation email body for user: {}", user.getEmail());
        return "Hello," +
                "In order to activate your account please access the following link:\n" +
                "http://localhost:8080/activation/" + user.getRandomToken();
    }

    @Override
    public String emailBodyScheduler(User user) {
        log.info("Generating scheduler email body for user: {}", user.getEmail());
        return "Hello," +
                "Discover the latest art destination and get a sneak peek of what's coming soon on: \n" +
                "http://localhost:8080/index/ !";
    }
}