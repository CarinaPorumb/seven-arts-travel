package ro.itschool.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.User;
import ro.itschool.exception.UserNotFound;
import ro.itschool.repository.UserRepository;
import ro.itschool.service.UserService;

import java.util.Optional;

@Controller
public class LoginController {
    private final UserService userService;
    private final UserRepository userRepository;

    public LoginController(UserService userService,
                           UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = {"/login", "/"})
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "redirect:/index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password.html";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword2(@ModelAttribute("user") @RequestBody User user) throws UserNotFound {
        Optional<User> myUser = Optional.ofNullable(userService.findUserByUserName(user.getUsername()));
        if (myUser.isPresent()) {
            if (myUser.get().getEmail().equals(user.getEmailConfirm()))
                return "register-success";

        }
        return "wrong-email";
    }

}