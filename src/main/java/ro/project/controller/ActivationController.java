//package ro.project.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ro.project.entity.User;
//import ro.project.exception.NotFoundException;
//import ro.project.repository.UserRepository;
//import ro.project.service.entity.UserService;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Controller
//public class ActivationController {
//
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    @GetMapping(value = "/activation/{randomToken}")
//    public String registerForm(@PathVariable String randomToken, Model model) throws NotFoundException {
//        Optional<User> userByRandomToken = Optional.ofNullable(userService.findUserByRandomToken(randomToken));
//        if (userByRandomToken.isPresent()) {
//            model.addAttribute("user", userByRandomToken.get());
//            return "activation";
//        } else
//            return "invalid-token";
//    }
//
//    @PostMapping(value = "/activation/{randomToken}")
//    public String registerUser(@ModelAttribute("user") @RequestBody User user) {
//        final User user1 = userService.findUserByRandomToken(user.getRandomToken());
//        user1.setEnabled(true);
//        userRepository.save(user1);
//        return "activation-success";
//    }
//}