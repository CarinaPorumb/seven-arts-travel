package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.User;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;
import java.util.Optional;

@Controller
public class ActivationController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/activation/{randomToken}")
    public String registerForm(@PathVariable String randomToken, Model model){
        final Optional<User> userByRandomToken = Optional.ofNullable(userService.findUserByRandomToken(randomToken));
        if (userByRandomToken.isPresent()) {
            model.addAttribute("user", userByRandomToken.get());
            return "activation";
        } else
            return "invalid-token";
    }

    @PostMapping(value = "/activation/{randomToken}")
    public String registerUser(@ModelAttribute("user") @RequestBody User user){
        final User user1 = userService.findUserByRandomToken(user.getRandomToken());
        user1.setEnabled(true);
        userRepository.save(user1);
        return "activation-success";
    }

}
