package ro.itschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.itschool.entity.Role;
import ro.itschool.entity.User;
import ro.itschool.service.UserService;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    HttpServletRequest httpServletRequest;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("user") @RequestBody User user) {
        if (user.getPassword().equals(user.getPasswordConfirm())) {
            user.setRoles(Collections.singleton(new Role("ROLE_USER")));
            user.setEnabled(false);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            userService.saveUser(user);
            return "register-success";
        } else {
            return "register";
        }
    }

}
