//package ro.project.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import ro.project.entity.Role;
//import ro.project.entity.User;
//import ro.project.service.entity.UserService;
//
//import java.util.Collections;
//@RequiredArgsConstructor
//@Controller
//public class RegisterController {
//
//    private final UserService userService;
//
//    @GetMapping(value = "/register")
//    public String registerForm(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "register";
//    }
//
//    @PostMapping(value = "/register")
//    public String registerUser(@ModelAttribute("user") @RequestBody User user) {
//        if (user.getPassword().equals(user.getPasswordConfirm())) {
//            user.setRoles(Collections.singleton(new Role("ROLE_USER")));
//            user.setEnabled(false);
//            user.setAccountNonExpired(true);
//            user.setAccountNonLocked(true);
//            user.setCredentialsNonExpired(true);
//            userService.saveUser(user);
//            return "register-success";
//        } else {
//            return "register";
//        }
//    }
//}